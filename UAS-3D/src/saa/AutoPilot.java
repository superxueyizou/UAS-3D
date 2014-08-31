package saa;

import saa.selfseparation.ChorusResData;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double2D;
import sim.util.Double3D;
import modeling.SAAModel;
import modeling.env.Waypoint;
import modeling.uas.UAS;
import modeling.uas.UASPerformance;

public class AutoPilot implements Steppable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double SDX;
	private final double SDY;
	private final double SDZ;
	private SAAModel state; 
	private UAS hostUAS;
	private String type;//normative manoeuvre type
	private int caActionCode;//action code from collision avoidance algorithm
	private ChorusResData ssData=null; // data from self separation algorithm
	private UASPerformance uasPerformance;

	public AutoPilot(SimState simstate, UAS uas, UASPerformance uasPerformance, String type, int actionCode) 
	{
		state = (SAAModel)simstate;
		hostUAS = uas;	
		this.type=type;
		this.caActionCode=actionCode;
		this.uasPerformance=uasPerformance;
		this.SDX=this.uasPerformance.getStdDevX();
		this.SDY=this.uasPerformance.getStdDevY();
		this.SDZ=this.uasPerformance.getStdDevZ();
	}


	public void step(SimState simState) 
	{
		if(hostUAS.isActive == true)
		{	
			if(caActionCode>=0)
			{
				hostUAS.setApWp(executeActionCode(caActionCode));				
			}
			else if (ssData!=null)
			{
				if(Double.isNaN(ssData.getTargetAngle()))
				{
					hostUAS.setApWp( executeTrkManeuver(ssData.getTargetAngle()));
				}
				else if(Double.isNaN(ssData.getTargetGs()))
				{
					hostUAS.setApWp( executeGsManeuver(ssData.getTargetGs()));
				}
				else if(Double.isNaN(ssData.getTargetVs()) && Double.isNaN(ssData.getTargetAltitude()))
				{
					hostUAS.setApWp( executeVsManeuver(ssData.getTargetVs(),ssData.getTargetAltitude()));
				}
				else
				{
					System.err.println("Something wrong in AutoPilot.step(SimState simState) ");
				}
				
			}
			else if(type=="WhiteNoise")
			{
				hostUAS.setApWp(executeWhiteNoise());
			}
			else if(type=="Specific")
			{
				double ay=SDY * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
				hostUAS.setApWp(executeSpecific(ay));
			}
//			else if(type=="Dubins")
//			{
//				hostUAS.setApWp(executeDubins());
//			}
//			else if (type=="ToTarget")
//			{
//				hostUAS.setApWp(executeToTarget());
//			}
			
		}		
	}

	private Waypoint executeTrkManeuver(double targetAngle)
	{
		Waypoint wp = new Waypoint(state.getNewID(), null);
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;
		double vz=hostUAS.getVelocity().z;
		double ay = SDY * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
		if(vy+ay>uasPerformance.getMaxClimb())
		{
			ay=uasPerformance.getMaxClimb()-vy;
		}
		else if(vy+ay<-uasPerformance.getMaxDescent())
		{
			ay=-uasPerformance.getMaxDescent()-vy;
		}
		
		double x = hostUAS.getLocation().x + vx;				
		double y= hostUAS.getLocation().y + vy + 0.5*ay;
		double z= hostUAS.getLocation().z;
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(vx, vy+ay,vz));
		wp.setLocation(new Double3D(x , y,z));		
		return wp;
		
	}

	private Waypoint executeGsManeuver(double targetGs)
	{
		Waypoint wp = new Waypoint(state.getNewID(), null);
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;
		double vz=hostUAS.getVelocity().z;
		double ay = SDY * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
		if(vy+ay>uasPerformance.getMaxClimb())
		{
			ay=uasPerformance.getMaxClimb()-vy;
		}
		else if(vy+ay<-uasPerformance.getMaxDescent())
		{
			ay=-uasPerformance.getMaxDescent()-vy;
		}
		
		double x = hostUAS.getLocation().x + vx;				
		double y= hostUAS.getLocation().y + vy + 0.5*ay;
		double z= hostUAS.getLocation().z;
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(vx, vy+ay,vz));
		wp.setLocation(new Double3D(x , y,z));		
		return wp;
		
	}

	private Waypoint executeVsManeuver(double targetVs, double targetAltitude)
	{
		Waypoint wp = new Waypoint(state.getNewID(), null);
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;
		double vz=hostUAS.getVelocity().z;
		double ay = SDY * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
		if(vy+ay>uasPerformance.getMaxClimb())
		{
			ay=uasPerformance.getMaxClimb()-vy;
		}
		else if(vy+ay<-uasPerformance.getMaxDescent())
		{
			ay=-uasPerformance.getMaxDescent()-vy;
		}
		
		double x = hostUAS.getLocation().x + vx;				
		double y= hostUAS.getLocation().y + vy + 0.5*ay;
		double z= hostUAS.getLocation().z;
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(vx, vy+ay,vz));
		wp.setLocation(new Double3D(x , y,z));		
		return wp;
	}
	
	
	private Waypoint executeActionCode(int actionCode)
	{
		Waypoint wp = new Waypoint(state.getNewID(), null);
		double ay=hostUAS.getCaa().getActionA(actionCode);
		double targetV= hostUAS.getCaa().getActionV(actionCode);
		double x;
		double y;
		double z;
		
		double currentV=hostUAS.getVelocity().y;
		if(Double.isInfinite(ay)||Double.isNaN(ay))
		{		
			ay = SDY* state.random.nextGaussian();
		}
		else if(ay>0 && targetV<currentV)
		{
			ay = -Math.abs(SDY* state.random.nextGaussian());
		}
		else if(ay<0 && targetV>currentV)
		{
			ay = Math.abs(SDY* state.random.nextGaussian());
		}
		
		x = hostUAS.getLocation().x+hostUAS.getVelocity().x;
		y = hostUAS.getLocation().y+hostUAS.getVelocity().y+0.5*ay;
		z = hostUAS.getLocation().z+hostUAS.getVelocity().z;
		
		hostUAS.setOldVelocity(new Double3D(hostUAS.getVelocity().x,hostUAS.getVelocity().y,hostUAS.getVelocity().z));
		hostUAS.setVelocity(new Double3D(hostUAS.getVelocity().x,hostUAS.getVelocity().y+ay,hostUAS.getVelocity().z));

		wp.setLocation(new Double3D(x,y,z));
		wp.setAction(actionCode+30);//30 for ACASX
		return wp;
	}
	
	public Waypoint executeWhiteNoise()
	{		
		Waypoint wp = new Waypoint(state.getNewID(), null);
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;
		double vz=hostUAS.getVelocity().z;
		double ax = SDX * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
		double ay = SDY * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
		double az = SDZ * state.random.nextGaussian()*(state.random.nextBoolean()?1:-1);
		Double2D groundVelocity = new Double2D(vx+ax,vz+az);
		if(groundVelocity.length()>uasPerformance.getMaxSpeed())
		{
			Double2D newGroundVelocity= groundVelocity.resize(uasPerformance.getMaxSpeed());
			ax=newGroundVelocity.x-vx;
			az=newGroundVelocity.y-vz;
		}
		else if(groundVelocity.length()<uasPerformance.getMinSpeed())
		{
			Double2D newGroundVelocity= groundVelocity.resize(uasPerformance.getMinSpeed());
			ax=newGroundVelocity.x-vx;
			az=newGroundVelocity.y-vz;
		}
		
		if(vy+ay>uasPerformance.getMaxClimb())
		{
			ay=uasPerformance.getMaxClimb()-vy;
		}
		else if(vy+ay<-uasPerformance.getMaxDescent())
		{
			ay=-uasPerformance.getMaxDescent()-vy;
		}
		
		double x = hostUAS.getLocation().x + vx+0.5*ax;				
		double y= hostUAS.getLocation().y + vy + 0.5*ay;
		double z= hostUAS.getLocation().z+ vz + 0.5*az;;
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(vx+ax, vy+ay,vz+az));
		wp.setLocation(new Double3D(x , y,z));		
		return wp;
	}

	public Waypoint executeSpecific(double ay)
	{
		Waypoint wp = new Waypoint(state.getNewID(), null);
		double vx=hostUAS.getVelocity().x;
		double vy=hostUAS.getVelocity().y;	
		double vz=hostUAS.getVelocity().z;	
		if(vy+ay>uasPerformance.getMaxClimb())
		{
			ay=uasPerformance.getMaxClimb()-vy;
		}
		else if(vy+ay<-uasPerformance.getMaxDescent())
		{
			ay=-uasPerformance.getMaxDescent()-vy;
		}
		
		double x = hostUAS.getLocation().x + vx;				
		double y= hostUAS.getLocation().y + vy + 0.5*ay;
		double z= hostUAS.getLocation().z;
		hostUAS.setOldVelocity(new Double3D(vx,vy,vz));
		hostUAS.setVelocity(new Double3D(vx, vy+ay, vz));
		wp.setLocation(new Double3D(x , y, z));		
		return wp;
	}
	
	public int getActionCode() {
		return caActionCode;
	}
	public void setActionCode(int actionCode) {
		this.caActionCode = actionCode;
	}


	public ChorusResData getSsData() {
		return ssData;
	}


	public void setSsData(ChorusResData ssData) {
		this.ssData = ssData;
	}
	
/*****************************************************************************************************************/
//	private Waypoint executeToTarget()
//	{
//		LinkedList<Waypoint> wpQueue = hostUAS.getWpQueue();
//		
//		if(wpQueue.size() != 0)
//		{
//			Waypoint nextWp=(Waypoint)wpQueue.peekFirst();
//			
//			Destination nextDest= (Destination)nextWp;	
//			
//			Double2D hostUASLocation = hostUAS.getLocation();		
//			final Double2D nextWpLocation =nextDest.getLocation();
//	 		final double distSqToNextWp = nextWpLocation.distanceSq(hostUASLocation);
//
//	 		Double2D prefVelocity;
//	 		double prefSpeed = hostUAS.getUasPerformance().getPrefSpeed();
//	 		if (Math.pow(prefSpeed,2) > distSqToNextWp)
//	 		{
//	 			prefVelocity = nextWpLocation.subtract(hostUASLocation);
//	 			
//	 		}
//	 		else
//	 		{
//	 			prefVelocity = nextWpLocation.subtract(hostUASLocation).normalize().multiply(prefSpeed); 
//	 			
//	 		}
//	 		
//	 		Double2D newVelocity;
//			double angle = hostUAS.getVelocity().masonRotateAngleToDouble2D(prefVelocity);	
//			if(Math.abs(angle) > hostUAS.getUasPerformance().getMaxTurning())
//			{
//				newVelocity = hostUAS.getVelocity().masonRotate(hostUAS.getUasPerformance().getMaxTurning()*angle/Math.abs(angle));
//			}
//			else
//			{
//				newVelocity = hostUAS.getVelocity().masonRotate(angle);
//			}
//			hostUAS.setOldVelocity(new Double2D(hostUAS.getVelocity().x,hostUAS.getVelocity().y));
//			hostUAS.setVelocity(newVelocity);					
//			Waypoint wp= new Waypoint(state.getNewID(), hostUAS.getDestination());
//			wp.setLocation(hostUASLocation.add(newVelocity));
//			wp.setAction(4);
//			return wp;
//		}
//		else
//		{
//			return null;
//		}
//	}
//	
//	private Waypoint executeDubins() 
//	{
//		LinkedList<Waypoint> wpQueue = hostUAS.getWpQueue();
//		if(wpQueue.size() != 0)
//		{
//			Waypoint nextWp=(Waypoint)wpQueue.peekFirst();			
//			Destination nextDest= (Destination)nextWp;	
//			return takeDubinsPath(hostUAS, nextDest);
//		}
//		else
//		{
//			return null;
//		}
//		
//		return null;
//	}
	
	
	
//    /* 
//     * This function is calculates any maneuvers that are necessary for the 
//     * current plane to avoid looping. Returns a waypoint based on calculations. 
//    */
//   public Waypoint takeDubinsPath(UAS uas, Waypoint nextDest) 
//   {
//       	if (uas.getVelocity().masonAngleWithDouble2D(nextDest.getLocation().subtract(uas.getLocation())) < 2*uas.getUasPerformance().getMaxTurning())
//       	{
//       		return calculateWaypoint(uas, 0);
//       	}
//    	
//    	boolean isDestOnRight = CALCULATION.rightOf(uas.getLocation(), uas.getLocation().add(uas.getVelocity()), nextDest.getLocation());
//    	double angleCoef = isDestOnRight? -1.0 : 1.0;
//      	/* Calculate the center of the circle of minimum turning radius on the side that the waypoint is on*/	
//    	double minTurningRadius = uas.getSpeed()/uas.getUasPerformance().getMaxTurning();
//    	Double2D cPlusCenter = uas.getLocation().add(uas.getVelocity().masonRotate(angleCoef*0.5*Math.PI).normalize().multiply(minTurningRadius));
//
//    	/* If destination is inside circle, must fly opposite direction before we can reach destination*/
//    	if ( minTurningRadius > cPlusCenter.distance(nextDest.getLocation())+4*CONFIGURATION.selfSafetyRadius) //-3*CONFIGURATION.selfSafetyRadius
//    	{
//    		return calculateWaypoint(uas, -1*angleCoef*uas.getUasPerformance().getMaxTurning());
//    	}
//    	else
//    	{
//    		return calculateWaypoint(uas, angleCoef*uas.getUasPerformance().getMaxTurning());
//    	}    	   		
//    	
//    }
//
//	/* Find the new collision avoidance waypoint for the UAS to go to */
//	public Waypoint calculateWaypoint(UAS uas, double turningAngle)
//	{			
//		Waypoint wp = new Waypoint(state.getNewID(), null);
//		wp.setLocation(uas.getLocation().add(uas.getVelocity().masonRotate(turningAngle)));
//		uas.setOldVelocity(new Double2D(uas.getVelocity().x,uas.getVelocity().y));
//		uas.setVelocity(uas.getVelocity().masonRotate(turningAngle));
//		return wp;
//	}   
}
