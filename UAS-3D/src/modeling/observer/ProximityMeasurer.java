package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.engine.Steppable;


public class ProximityMeasurer implements Constants,Steppable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SAAModel state;


	public ProximityMeasurer()
	{
		
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
			
			double tempDistanceToDanger=Double.MAX_VALUE;
			for (int k = i+1; k<state.uasBag.size(); k++)
			{
				UAS uas2=(UAS)state.uasBag.get(k);
				if(!uas2.isActive)
				{
					continue;
				}
				double d= uas1.getLocation().distance(uas2.getLocation());
		    	if(d<tempDistanceToDanger)
		    	{
		    		tempDistanceToDanger=d;
		    		
		    	}
			}
			uas1.setTempDistanceToDanger(tempDistanceToDanger);	

	    	if (tempDistanceToDanger < uas1.getMinDistanceToDanger())
			{
				uas1.setMinDistanceToDanger(tempDistanceToDanger);	
			}
		}
	}
}
