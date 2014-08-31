/**
 * 
 */
package saa.collsionavoidance;

import modeling.uas.UAS;
import sim.engine.SimState;


/**
 * @author Xueyi
 *
 */
public class CollisionAvoidanceAlgorithmAdapter extends CollisionAvoidanceAlgorithm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	public CollisionAvoidanceAlgorithmAdapter(SimState simstate, UAS uas) 
	{
	}

	public void init()
	{
		
	}
	
	@Override
	public void step(SimState simState)
	{
	}
	
	public void execute()
	{
		
	}

	@Override
	public double getActionA(int actionCode) {
		return Double.NaN;
	}

	@Override
	public double getActionV(int actionCode) {
			return Double.NaN;
	}
	
	

}
