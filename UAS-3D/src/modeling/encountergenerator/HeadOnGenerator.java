/**
 * 
 */
package modeling.encountergenerator;

import saa.AutoPilot;
import saa.collsionavoidance.ACASX;
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
import sim.util.Double2D;
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
public class HeadOnGenerator extends EncounterGenerator 
{

	/**
	 * 
	 */
	private SAAModel state;
	
	private Double3D location;
	private double Vx;
	private double Vy;
	private double Vz;
		
	
	public HeadOnGenerator(SAAModel state, Double3D location, double Vx, double Vy, double Vz) 
	{		
		this.state=state;
		this.location = location;
		this.Vx = Vx;
		this.Vy = Vy;
		this.Vz = Vz;
		
	}
	
	public void execute()
	{	
		UASVelocity intruderVelocity = new UASVelocity(new Double3D(Vx,Vy,Vz));
		UASPerformance intruderPerformance = new UASPerformance(CONFIGURATION.headOnStdDevX, CONFIGURATION.headOnStdDevY,CONFIGURATION.headOnStdDevZ,
				CONFIGURATION.headOnMaxSpeed, CONFIGURATION.headOnMinSpeed, Math.sqrt(Vx*Vx+Vy*Vy), CONFIGURATION.headOnMaxClimb, CONFIGURATION.headOnMaxDescent,CONFIGURATION.headOnMaxTurning, CONFIGURATION.headOnMaxAcceleration, CONFIGURATION.headOnMaxDeceleration);
		SenseParas intruderSenseParas = new SenseParas(CONFIGURATION.headOnViewingRange,CONFIGURATION.headOnViewingAngle);
		
		UAS intruder = new UAS(state.getNewID(),location, intruderVelocity,intruderPerformance, intruderSenseParas);
		
		SensorSet sensorSet = new SensorSet();
		if((CONFIGURATION.headOnSensorSelection&0B10000) == 0B10000)
		{
			sensorSet.perfectSensor=new PerfectSensor();
		}
		if((CONFIGURATION.headOnSensorSelection&0B01000) == 0B01000)
		{
			sensorSet.ads_b=new ADS_B();
		}
		if((CONFIGURATION.headOnSensorSelection&0B00100) == 0B00100)
		{
			sensorSet.tcas=new TCAS();
		}
		if((CONFIGURATION.headOnSensorSelection&0B00010) == 0B00010)
		{
			sensorSet.radar=new Radar();
		}
		if((CONFIGURATION.headOnSensorSelection&0B00001) == 0B00001)
		{
			sensorSet.eoir=new EOIR();
		}
		sensorSet.synthesize();
		
		AutoPilot ap= new AutoPilot(state, intruder,intruderPerformance, CONFIGURATION.headOnAutoPilotAlgorithmSelection,-999);
		
		CollisionAvoidanceAlgorithm caa;
		switch(CONFIGURATION.headOnCollisionAvoidanceAlgorithmSelection)
		{
			case "ACASXAvoidanceAlgorithm":
				caa= new ACASX(state, intruder);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
				break;
			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
		}

		SelfSeparationAlgorithm ssa;
		switch(CONFIGURATION.headOnSelfSeparationAlgorithmSelection)
		{
			case "NASAChorusAlgorithm":
				ssa= new NASAChorus(state, intruder);
				break;
			case "None":
				ssa= new SelfSeparationAlgorithmAdapter(state, intruder);
				break;
			default:
				ssa= new SelfSeparationAlgorithmAdapter(state, intruder);
		}

		intruder.init(sensorSet,ap,caa,ssa);
		
		state.uasBag.add(intruder);
		state.allEntities.add(intruder);
		intruder.setSchedulable(true);
	
	}

}
