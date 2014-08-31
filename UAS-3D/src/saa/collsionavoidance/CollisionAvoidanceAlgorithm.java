/**
 * 
 */
package saa.collsionavoidance;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * @author Xueyi
 *
 */
public abstract class CollisionAvoidanceAlgorithm implements Steppable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	
	public CollisionAvoidanceAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public abstract void init();
	
	public abstract void execute();
	
	@Override
	public abstract void step(SimState simState);

	public abstract double getActionA(int actionCode);

	public abstract double getActionV(int actionCode);

}
