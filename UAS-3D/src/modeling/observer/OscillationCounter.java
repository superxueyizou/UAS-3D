package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.env.Waypoint;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double3D;


public class OscillationCounter extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SAAModel state;

	public OscillationCounter(int idNo, Double3D location)
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
			int oscillationNo=0;
			for(int j=0; j<uas1.getAchievedWaypoints().size()-1; j++)
			{
				int wp1Action = ((Waypoint)uas1.getAchievedWaypoints().get(j)).getAction();
				int wp2Action = ((Waypoint)uas1.getAchievedWaypoints().get(j+1)).getAction();
				if(wp1Action!=wp2Action)
				{
					oscillationNo++;
				}
			}
			uas1.setNumOscillation(oscillationNo);	
//			System.out.println(uas1+"  "+ oscillationNo);
		}
		
	}

}
