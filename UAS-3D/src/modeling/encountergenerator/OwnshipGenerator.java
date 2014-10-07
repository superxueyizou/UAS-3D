/**
 * 
 */
package modeling.encountergenerator;

import modeling.SAAModel;
import modeling.uas.SenseParas;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;
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
import configuration.OwnshipConfig;

/**
 * @author Xueyi
 *
 */
public class OwnshipGenerator
{
	private SAAModel state;
	private String ownshipAlias;
	private double uasX;
	private double uasY;
	private double uasZ;
		
	private OwnshipConfig ownshipConfig;
	
	public OwnshipGenerator(SAAModel state, String ownshipAlias, double uasX, double uasY,double uasZ, OwnshipConfig ownshipConfig) 
	{		
		this.state=state;
		this.ownshipAlias=ownshipAlias;
		this.uasX=uasX;
		this.uasY=uasY;
		this.uasZ=uasZ;
		this.ownshipConfig=ownshipConfig;		
	}
	
	public UAS execute()
	{
		Double3D location = new Double3D(uasX,uasY,uasZ);
		UASVelocity uasVelocity = new UASVelocity(new Double3D(ownshipConfig.ownshipVx,ownshipConfig.ownshipVy,ownshipConfig.ownshipVz));
		UASPerformance uasPerformance = new UASPerformance(ownshipConfig.ownshipStdDevX, ownshipConfig.ownshipStdDevY,ownshipConfig.ownshipStdDevZ,
				ownshipConfig.ownshipMaxSpeed, ownshipConfig.ownshipMinSpeed, ownshipConfig.ownshipMaxClimb, 
				ownshipConfig.ownshipMaxDescent,ownshipConfig.ownshipMaxTurning, ownshipConfig.ownshipMaxAcceleration, ownshipConfig.ownshipMaxDeceleration);
		SenseParas senseParas = null;
		
		UAS ownship = new UAS(state.getNewID(),location, uasVelocity,uasPerformance, senseParas);
		ownship.setAlias(ownshipAlias);
		
		SensorSet sensorSet = new SensorSet();
		if((ownshipConfig.ownshipSensorSelection&0B10000) == 0B10000)
		{
			sensorSet.perfectSensor=new PerfectSensor();
		}
		if((ownshipConfig.ownshipSensorSelection&0B01000) == 0B01000)
		{
			sensorSet.ads_b=new ADS_B();
		}
		if((ownshipConfig.ownshipSensorSelection&0B00100) == 0B00100)
		{
			sensorSet.tcas=new TCAS();
		}
		if((ownshipConfig.ownshipSensorSelection&0B00010) == 0B00010)
		{
			sensorSet.radar=new Radar();
		}
		if((ownshipConfig.ownshipSensorSelection&0B00001) == 0B00001)
		{
			sensorSet.eoir=new EOIR();
		}
		sensorSet.synthesize();
		
		AutoPilot ap= new AutoPilot(state, ownship,uasPerformance,ownshipConfig.ownshipAutoPilotAlgorithmSelection, -999);
		
		CollisionAvoidanceAlgorithm caa;
		switch(ownshipConfig.ownshipCollisionAvoidanceAlgorithmSelection)
		{
			case "ACASXAvoidanceAlgorithm":
				caa= new ACASX(state, ownship);
				break;
			case "ACASX3DAvoidanceAlgorithm":
				caa= new ACASX3D(state, ownship);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, ownship);
				break;
			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, ownship);
		}	
		
		SelfSeparationAlgorithm ssa;
		switch(ownshipConfig.ownshipSelfSeparationAlgorithmSelection)
		{
			case "NASAChorusAlgorithm":
				ssa= new NASAChorus(state, ownship);
				break;
			case "None":
				ssa= new SelfSeparationAlgorithmAdapter(state, ownship);
				break;
			default:
				ssa= new SelfSeparationAlgorithmAdapter(state, ownship);
		}
		
		ownship.init(sensorSet,ap,caa,ssa);	
		ownship.setSchedulable(true);
		
		return ownship;
		
	}
}
