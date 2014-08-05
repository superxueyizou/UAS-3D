/**
 * 
 */
package saa.selfseparation;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * @author Xueyi
 *
 */
public abstract class SelfSeparationAlgorithm implements Steppable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SelfSeparationAlgorithm() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public abstract void init();
	
	public abstract void execute();
	
	@Override
	public abstract void step(SimState simState);

}
