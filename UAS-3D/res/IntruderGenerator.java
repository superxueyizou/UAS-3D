/**
 * 
 */
package modeling.encountergenerator;

import configuration.Configuration;
import configuration.IntruderConfig;
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
import modeling.SAAModel;
import modeling.uas.SenseParas;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;
import modeling.uas.UASVelocity;

/**
 * @author Xueyi
 *
 */
public class IntruderGenerator
{
	private int numHeadOn=0;
	private int numCrossing=0;
	private int numOvertaking=0;
	private int numOvertaken=0;
	private double alertTime=28;
	private Configuration config = Configuration.getInstance();
	/*Used with GUI*/
	public UAS generateHeadOn(SAAModel state, UAS self, String intruderAlias, IntruderConfig intruderConfig)
	{
		int sign=(numHeadOn%2 ==0)?-1:+1;
		int scale=numHeadOn/2;
			    		
		double vx =-intruderConfig.intruderVx;
		double vy =intruderConfig.intruderVy;
		double vz =intruderConfig.intruderVz;
		double offsetY = intruderConfig.intruderOffsetY;		
		double offsetZ = intruderConfig.intruderOffsetZ;	
		
		double selfLocX=self.getLocation().x;
		double selfLocY=self.getLocation().y;
		double selfLocZ=self.getLocation().z;
		
		Double3D location = new Double3D(selfLocX+alertTime*(config.ownshipConfig.ownshipVx+intruderConfig.intruderVx), selfLocY+sign*scale*offsetY, selfLocZ+ 5*state.random.nextGaussian()*offsetZ);
		UAS headOn=generateIntruder(state,location,vx,vy,vz,intruderConfig);	  
		headOn.setAlias(intruderAlias);
		numHeadOn++;
		return headOn;
    	
	}
	
	/*Used with GUI*/
	public UAS generateCrossing(SAAModel state, UAS self, IntruderConfig intruderConfig)
	{
		int sign=(numCrossing%2 ==0)?-1:+1;
		int scale=numCrossing/2;
			    		
		double vx =-intruderConfig.intruderVx;
		double vy =intruderConfig.intruderVy;
		double vz =intruderConfig.intruderVz;
		double offsetY = intruderConfig.intruderOffsetY;		
		double offsetZ = intruderConfig.intruderOffsetZ;	
		
		double selfLocX=self.getLocation().x;
		double selfLocY=self.getLocation().y;
		double selfLocZ=self.getLocation().z;
		
//		Double2D selfMiddle = self.getLocation().add(self.getDestination().getLocation()).multiply(0.5);
//		Double2D selfVector = self.getDestination().getLocation().subtract(self.getLocation());
//		Double2D intruderVector = selfVector.rotate(-sideFactor*encounterAngle).multiply(intruderSpeed/self.getSpeed());
//		Double2D intruderMiddle = selfMiddle;
//				
//		Double2D intruderLocation = intruderMiddle.subtract(intruderVector.multiply(0.5));
//		Double2D intruderDestinationLoc = intruderMiddle.add(intruderVector.multiply(0.5));
//		Destination intruderDestination = new Destination(state.getNewID(), null);
//		intruderDestination.setLocation(intruderDestinationLoc);
//		UASVelocity intruderVelocity = new UASVelocity(intruderDestination.getLocation().subtract(intruderLocation).normalize().multiply(intruderSpeed));
		
		Double3D location = new Double3D(selfLocX+alertTime*(config.ownshipConfig.ownshipVx+intruderConfig.intruderVx), selfLocY+sign*scale*offsetY, selfLocZ+ 5*state.random.nextGaussian()*offsetZ);
		UAS headOn=generateIntruder(state,location,vx,vy,vz,intruderConfig);	  	
		numHeadOn++;
		return headOn;
    	
	}
	
	/*Used with GUI*/
	public UAS generateOvertaking(SAAModel state, UAS self, IntruderConfig intruderConfig)
	{
		int sign=(numHeadOn%2 ==0)?-1:+1;
		int scale=numHeadOn/2;
			    		
		double vx =-intruderConfig.intruderVx;
		double vy =intruderConfig.intruderVy;
		double vz =intruderConfig.intruderVz;
		double offsetY = intruderConfig.intruderOffsetY;		
		double offsetZ = intruderConfig.intruderOffsetZ;	
		
		double selfLocX=self.getLocation().x;
		double selfLocY=self.getLocation().y;
		double selfLocZ=self.getLocation().z;
		
//		Double2D selfMiddle = self.getLocation().add(self.getDestination().getLocation()).multiply(0.5);
//		Double2D selfVector = self.getDestination().getLocation().subtract(self.getLocation());
//		Double2D intruderVector = selfVector.multiply(intruderSpeed/self.getSpeed());
//		Double2D offsetVector = selfVector.rotate(0.5*sideFactor*Math.PI).resize(offset);
//		Double2D intruderMiddle = selfMiddle.add(offsetVector);
//				
//		Double2D intruderLocation = intruderMiddle.subtract(intruderVector.multiply(0.5));
//		Double2D intruderDestinationLoc;
//		if(intruderSpeed>self.getUasPerformance().getPrefSpeed())
//		{
//			intruderDestinationLoc = intruderMiddle.add(intruderVector.multiply(1));
//		}
//		else
//		{
//			intruderDestinationLoc = intruderMiddle.add(intruderVector.multiply(0.4));
//		}
//		
//		Destination intruderDestination = new Destination(state.getNewID(), null);
//		intruderDestination.setLocation(intruderDestinationLoc);
//	
//		UASVelocity intruderVelocity = new UASVelocity(intruderDestination.getLocation().subtract(intruderLocation).normalize().multiply(intruderSpeed));
		
		Double3D location = new Double3D(selfLocX+alertTime*(config.ownshipConfig.ownshipVx+intruderConfig.intruderVx), selfLocY+sign*scale*offsetY, selfLocZ+ 5*state.random.nextGaussian()*offsetZ);
		UAS headOn=generateIntruder(state,location,vx,vy,vz,intruderConfig);	  	
		numHeadOn++;
		return headOn;
    	
	}
	
	/*Used with GUI*/
	public UAS generateOvertaken(SAAModel state, UAS self, IntruderConfig intruderConfig)
	{
		int sign=(numHeadOn%2 ==0)?-1:+1;
		int scale=numHeadOn/2;
			    		
		double vx =-intruderConfig.intruderVx;
		double vy =intruderConfig.intruderVy;
		double vz =intruderConfig.intruderVz;
		double offsetY = intruderConfig.intruderOffsetY;		
		double offsetZ = intruderConfig.intruderOffsetZ;	
		
		double selfLocX=self.getLocation().x;
		double selfLocY=self.getLocation().y;
		double selfLocZ=self.getLocation().z;
		
		Double3D location = new Double3D(selfLocX+alertTime*(config.ownshipConfig.ownshipVx+intruderConfig.intruderVx), selfLocY+sign*scale*offsetY, selfLocZ+ 5*state.random.nextGaussian()*offsetZ);
		UAS headOn=generateIntruder(state,location,vx,vy,vz,intruderConfig);	  	
		numHeadOn++;
		return headOn;
    	
	}
	

}
