package modeling.observer;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.uas.Proximity;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.util.Double3D;


public class ProximityMeasurer extends Entity
{
	private static final long serialVersionUID = 1L;
	
	public ProximityMeasurer(int idNo, Double3D location)
	{
		super(idNo, Constants.EntityType.TObserver);
		this.location=location;	
	}
	
	@Override
	public void step(SimState simState) 
	{
		SAAModel state = (SAAModel)simState;	
		UAS ownship=(UAS) state.uasBag.get(0);
		Double3D ownshipLoc=ownship.getLocation();
		
		Proximity tempP=new Proximity(Double.MAX_VALUE,Double.MAX_VALUE);
		for(int i=1; i<state.uasBag.size(); i++)//loop all the intruders
		{		    	
			UAS intruder= (UAS)state.uasBag.get(i);
			if(!intruder.isActive)
			{
				continue;
			}
			Double3D intruderLoc=intruder.getLocation();			
			Proximity p= new Proximity(ownshipLoc,intruderLoc);
	    	if(p.lessThan(tempP))
	    	{
	    		tempP=p;	    		
	    	}	    
		}
	    ownship.setTempProximity(tempP);
		if (tempP.lessThan( ownship.getMinProximity()))
		{
			ownship.setMinProximity(tempP);	
		}
		
//		UAS uas1;
//	    for(int i=0; i<state.uasBag.size(); i++)
//		{		    	
//			uas1= (UAS)state.uasBag.get(i);
//			if(!uas1.isActive)
//			{
//				continue;
//			}
//			
//			double tempDistanceToDanger=Double.MAX_VALUE;
//			for (int k = i+1; k<state.uasBag.size(); k++)
//			{
//				UAS uas2=(UAS)state.uasBag.get(k);
//				if(!uas2.isActive)
//				{
//					continue;
//				}
//				double d= uas1.getLocation().distance(uas2.getLocation());
//		    	if(d<tempDistanceToDanger)
//		    	{
//		    		tempDistanceToDanger=d;
//		    		
//		    	}
//			}
//			uas1.setTempDistanceToDanger(tempDistanceToDanger);	
//
//	    	if (tempDistanceToDanger < uas1.getMinDistanceToDanger())
//			{
//				uas1.setMinDistanceToDanger(tempDistanceToDanger);	
//			}
//		}
		
	}	

}
