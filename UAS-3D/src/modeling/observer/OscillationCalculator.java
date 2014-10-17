package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double3D;


public class OscillationCalculator extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SAAModel state;

	public OscillationCalculator(int idNo, Double3D location)
	{
		super(idNo, Constants.EntityType.TObserver);
		this.location=location;		
	}
	
	@Override
	public void step(SimState simState) 
	{
		this.state = (SAAModel)simState;
		UAS uas1;		

	    for(int i=0; i<state.uasBag.size(); i++)
		{	
	    	
			uas1= (UAS)state.uasBag.get(i);
			if(!uas1.isActive)
			{
				continue;
			}

			Double3D oldVelocity = uas1.getOldVelocity();
			Double3D newVelocity = uas1.getVelocity();
			double area =0;//Math.abs(0.5*oldVelocity.negate().perpDot(newVelocity));
			uas1.setTempOscillation(area);
			uas1.setOscillation(uas1.getOscillation()+area);
										
		}
		
	}

}
