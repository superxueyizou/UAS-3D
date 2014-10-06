/**
 * 
 */
package saa.selfseparation;

import gov.nasa.larcfm.Chorus.Chorus;
import modeling.SAAModel;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.util.Double2D;
import sim.util.Double3D;


/**
 * @author Xueyi
 *
 */
public class NASAChorus extends SelfSeparationAlgorithm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double ft2NMRate=0.000164578833693305;
	private static final double fps2KnotRate=0.592484;
	private static final double ft2MRate=0.3048;
	private SAAModel state; 
	private UAS hostUAS;
	private Chorus chorus;
	
	public NASAChorus(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;
		chorus = new Chorus();
		chorus.setOwnship(""+uas.getID());
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		chorus.setMaxBankAngle(25); // bank angle = 25 degrees
		chorus.setGsAccel(4.5); // ground speed acceleration = 4.5 m/s^2
		chorus.setVsAccel(2.2); // vertical speed acceleration = 2.2 m/s^2
		chorus.setSeparation(5000*ft2NMRate,500); // set protection zone: 5.3 [nm], 1300 [ft]	

	}	
		
	
	@Override
	public void step(SimState simState)
	{
		if(hostUAS.isActive == true)
		{	
			execute();			
		}		 
	}
	
	
	public void execute()
	{		
		//update locations
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas.isActive == true)
			{	
				Double3D loc = uas.getLocation();
				Double3D vel=uas.getVelocity();			
				chorus.updateXYZ(""+uas.getID(), loc.x*ft2NMRate, loc.y*ft2NMRate, loc.z, vel.x*fps2KnotRate, vel.y*fps2KnotRate, vel.z, state.schedule.getTime());			
			}	
		
		}
		
		//Using Kinematic Resolution
		int res = chorus.resolutionKinematic();
		ChorusResData resData=new ChorusResData();
		if (res > 0)
		{
//			chorus.printResolutions();
			if (chorus.hasTrkOnly())
			{					
				if (chorus.trkFeasible() >= 0) 
				{
					Double2D gs=new Double2D(hostUAS.getVelocity().x, hostUAS.getVelocity().z);
					System.out.print("Heading is "+gs.angle()+" [rad]    ");
					System.out.println("Track solution is "+chorus.trkOnly()+" [deg]");
					resData.setTargetGs(Math.toRadians(chorus.trkOnly()));
					hostUAS.getAp().setSsData(resData);	
					return;
				}
				else
				{
					System.out.print("Turn will not complete before entering protection ");
					if (chorus.trkFeasible() == -2)
					System.out.print("zone of most urgent aircraft.");
					if (chorus.trkFeasible() == -3)
					System.out.print("zone of secondary aircraft.");
					System.out.println("");
				}
				if (chorus.hasSecondaryConflictsTrk())
				System.out.println(" Track solution has secondary conflicts.");
				
				System.out.println("The estimated turn time is "+chorus.getManeuverTimeTrk()+" [secs]");
				System.out.println("The estimated time to loss of separation is "+chorus.getDet().getTimeIn(0)+" [secs]");
			}
			if (chorus.hasVsOnly())
			System.out.print("Vertical speed solution is "+chorus.vsOnly()+" [fpm]");
			if (chorus.vsFeasible() < 0)
			System.out.print(" [vs Acceleration infeasible]");
			if (chorus.hasTargetAlt())
			System.out.print("Target Altitude is "+chorus.targetAltitude()+" [fpm]");
			
			hostUAS.getAp().setActionCode(0);	
		}


		//Using Resolution
//		int numDetections = chorus.detection();
//		if (numDetections > 0) 
//		{
//			ChorusDetector chorDet = chorus.getDet();
//			if (chorDet.lossOfSeparation() > 0) 
//			{
//				System.out.println("Duration of Loss of Separation = "
//						+chorDet.conflictDuration(0)+" [sec]");
//			}
//			else
//			{
//				System.out.println(" Aircraft in Conflict, time into LoS is "
//						+chorDet.getTimeIn(0)+" [sec]");
//						chorus.resolution();
//			}
//	
//			if (chorus.hasTrkOnly()) 
//			System.out.print("Track solution is "+chorus.trkOnly()+" [deg]");
//			if (chorus.trkFeasible() < 0) System.out.print(" [Turn infeasible]");
//			System.out.println();
//			if (chorus.hasGsOnly())
//			System.out.print("Ground speed solution is "+chorus.gsOnly()+" [kn]");
//			if (chorus.gsFeasible() < 0)
//			System.out.print(" <gs Acceleration infeasible>");
//			System.out.println();
//			if (chorus.hasVsOnly())
//			System.out.print("Vertical speed solution is "
//			+chorus.vsOnly()+" [fpm]");
//			if (chorus.vsFeasible() < 0)
//			System.out.print(" [vs Acceleration infeasible]");
//			System.out.println();
//		} 
//		else
//		{
//			System.out.println("! Aircraft are not in conflict.");
//		}
//		if (chorus.hasError() || chorus.hasMessage())
//		System.out.println(chorus.getMessage());
//		
	}
	
}
