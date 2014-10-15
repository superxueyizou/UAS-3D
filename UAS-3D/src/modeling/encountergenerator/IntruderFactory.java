package modeling.encountergenerator;

import saa.AutoPilot;
import saa.collsionavoidance.ACASX2D;
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
import configuration.IntruderConfig;
import modeling.SAAModel;
import modeling.uas.SenseParas;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;

public class IntruderFactory {
	
	public static UAS generateIntruder(SAAModel state, UAS ownship, String intruderAlias,IntruderConfig intruderConfig)
	{	
		Double3D location=ownship.getLocation().add(new Double3D(intruderConfig.intruderR*Math.cos(Math.toRadians(intruderConfig.intruderTheta)), intruderConfig.intruderOffsetY, intruderConfig.intruderR*Math.sin(Math.toRadians(intruderConfig.intruderTheta))));			
		UASVelocity intruderVelocity = new UASVelocity(new Double3D(intruderConfig.intruderGs*Math.cos(Math.toRadians(intruderConfig.intruderBearing)), intruderConfig.intruderVy, intruderConfig.intruderGs*Math.sin(Math.toRadians(intruderConfig.intruderBearing))));
		UASPerformance intruderPerformance = new UASPerformance(intruderConfig.intruderStdDevX, intruderConfig.intruderStdDevY,intruderConfig.intruderStdDevZ,
				intruderConfig.intruderMaxSpeed, intruderConfig.intruderMinSpeed, intruderConfig.intruderMaxClimb, intruderConfig.intruderMaxDescent,intruderConfig.intruderMaxTurning, intruderConfig.intruderMaxAcceleration, intruderConfig.intruderMaxDeceleration);
		SenseParas intruderSenseParas = null;
		
		UAS intruder = new UAS(state.getNewID(),location, intruderVelocity,intruderPerformance, intruderSenseParas);
		intruder.setAlias(intruderAlias);
		
		SensorSet sensorSet = new SensorSet();
		if((intruderConfig.intruderSensorSelection&0B10000) == 0B10000)
		{
			sensorSet.perfectSensor=new PerfectSensor();
		}
		if((intruderConfig.intruderSensorSelection&0B01000) == 0B01000)
		{
			sensorSet.ads_b=new ADS_B();
		}
		if((intruderConfig.intruderSensorSelection&0B00100) == 0B00100)
		{
			sensorSet.tcas=new TCAS();
		}
		if((intruderConfig.intruderSensorSelection&0B00010) == 0B00010)
		{
			sensorSet.radar=new Radar();
		}
		if((intruderConfig.intruderSensorSelection&0B00001) == 0B00001)
		{
			sensorSet.eoir=new EOIR();
		}
		sensorSet.synthesize();
		
		AutoPilot ap= new AutoPilot(state, intruder,intruderPerformance, intruderConfig.intruderAutoPilotAlgorithmSelection,-999);
		
		CollisionAvoidanceAlgorithm caa;
		switch(intruderConfig.intruderCollisionAvoidanceAlgorithmSelection)
		{
			case "ACASX2DAvoidanceAlgorithm":
				caa= new ACASX2D(state, intruder);
				break;
			case "ACASX3DAvoidanceAlgorithm":
				caa= new ACASX3D(state, intruder);
				break;
			case "None":
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
				break;
			default:
				caa= new CollisionAvoidanceAlgorithmAdapter(state, intruder);
		}

		SelfSeparationAlgorithm ssa;
		switch(intruderConfig.intruderSelfSeparationAlgorithmSelection)
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
		intruder.setSchedulable(true);
		
		return intruder;	
	}

}
