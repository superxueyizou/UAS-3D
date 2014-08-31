/**
 * 
 */
package modeling.encountergenerator;

import saa.AutoPilot;
import saa.collsionavoidance.ACASX;
import saa.collsionavoidance.ACASX3D;
import saa.collsionavoidance.CollisionAvoidanceAlgorithm;
import saa.collsionavoidance.CollisionAvoidanceAlgorithmAdapter;
import saa.selfseparation.NASAChorus;
import saa.selfseparation.SelfSeparationAlgorithm;
import saa.selfseparation.SelfSeparationAlgorithmAdapter;
import saa.sense.ADS_B;
import saa.sense.EOIR;
import saa.sense.PerfectSensor;
import saa.sense.Radar;
import saa.sense.SensorSet;
import saa.sense.TCAS;
import sim.util.Double3D;
import tools.CONFIGURATION;
import modeling.SAAModel;
import modeling.uas.SenseParas;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;

/**
 * @author Xueyi
 *
 */
public class SelfGenerator
{

	/**
	 * 
	 */
	private SAAModel state;
	private double uasX;
	private double uasY;
	private double uasZ;
	private double Vx;
	private double Vy;
	private double Vz;
		
	
	public SelfGenerator(SAAModel state,double uasX, double uasY,double uasZ, double Vx, double Vy, double Vz) 
	{		
		this.state=state;
		this.uasX=uasX;
		this.uasY=uasY;
		this.uasZ=uasZ;
		this.Vx=Vx;
		this.Vy = Vy;	
		this.Vz = Vz;
		
	}
	
	public UAS execute()
	{
		Double3D location = new Double3D(uasX,uasY,uasZ);
		UASVelocity uasVelocity = new UASVelocity(new Double3D(Vx,Vy,Vz));
		UASPerformance uasPerformance = new UASPerformance(CONFIGURATION.selfStdDevX, CONFIGURATION.selfStdDevY,CONFIGURATION.selfStdDevZ,
				CONFIGURATION.selfMaxSpeed, CONFIGURATION.selfMinSpeed, CONFIGURATION.selfPrefSpeed,CONFIGURATION.selfMaxClimb, 
				CONFIGURATION.selfMaxDescent,CONFIGURATION.selfMaxTurning, CONFIGURATION.selfMaxAcceleration, CONFIGURATION.selfMaxDeceleration);
		SenseParas senseParas = new SenseParas(CONFIGURATION.selfViewingRange,CONFIGURATION.selfViewingAngle);
		
		UAS self = new UAS(state.getNewID(),location, uasVelocity,uasPerformance, senseParas);
		
		SensorSet sensorSet = new SensorSet();
		if((CONFIGURATION.selfSensorSelection&0B10000) == 0B10000)
		{
			sensorSet.perfectSensor=new PerfectSensor();
		}
		if((CONFIGURATION.selfSensorSelection&0B01000) == 0B01000)
		{
			sensorSet.ads_b=new ADS_B();
		}
		if((CONFIGURATION.selfSensorSelection&0B00100) == 0B00100)
		{
			sensorSet.tcas=new TCAS();
		}
		if((CONFIGURATION.selfSensorSelection&0B00010) == 0B00010)
		{
			sensorSet.radar=new Radar();
		}
		if((CONFIGURATION.selfSensorSelection&0B00001) == 0B00001)
		{
			sensorSet.eoir=new EOIR();
		}
		sensorSet.synthesize();
		
		AutoPilot ap= new AutoPilot(state, self,uasPerformance,CONFIGURATION.selfAutoPilotAlgorithmSelection, -999);
		
		CollisionAvoidanceAlgorithm caa;
		switch(CONFIGURATION.selfCollisionAvoidanceAlgorithmSelection)
		{
			case "ACASXAvoidanceAlgorithm":
				caa= new ACASX(state, self);
				break;
			case "ACASX3DAvoidanceAlgorithm":
				caa= new ACASX3D(state, self);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, self);
				break;
			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, self);
		}	
		
		SelfSeparationAlgorithm ssa;
		switch(CONFIGURATION.selfSelfSeparationAlgorithmSelection)
		{
			case "NASAChorusAlgorithm":
				ssa= new NASAChorus(state, self);
				break;
			case "None":
				ssa= new SelfSeparationAlgorithmAdapter(state, self);
				break;
			default:
				ssa= new SelfSeparationAlgorithmAdapter(state, self);
		}
		
		self.init(sensorSet,ap,caa,ssa);
				
		state.uasBag.add(self);
		state.allEntities.add(self);
		self.setSchedulable(true);
		
		return self;
		
	}
}
