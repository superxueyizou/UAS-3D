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

		UAS uas1= (UAS) state.uasBag.get(0);
		UAS uas2= (UAS) state.uasBag.get(1);
		double d= Math.abs(uas1.getLocation().x-uas2.getLocation().x)+Math.abs(uas1.getLocation().y-uas2.getLocation().y); //Math.max(uas1.getLocation().distance(uas2.getLocation()),0);
    	uas1.setTempDistanceToDanger(d);
    	uas2.setTempDistanceToDanger(d);
    	if (d < uas1.getMinDistanceToDanger())
		{
			uas1.setMinDistanceToDanger(d);	
//			System.out.println("   dd "+d);
		}	
//    	else
//    	{
//    		System.out.println(d+"   "+uas1.getMinDistanceToDanger());
//    	}
    	if (d < uas2.getMinDistanceToDanger())
		{
			uas2.setMinDistanceToDanger(d);	
//			System.out.println("   dd "+d);
		}

//
//	    for(int i=0; i<state.uasBag.size(); i++)
//		{	
//	    	
//			uas1= (UAS) state.uasBag.get(i);
//			if(!uas1.isActive)
//			{
//				continue;
//			}
//			
//			double tempProximityToDanger=Double.MAX_VALUE;	
//	    	for (int k = 0; k<state.uasBag.size(); k++)
//			{
//				UAS uas2=(UAS)state.uasBag.get(k);
//				if(!uas2.isActive || uas1==uas2)
//				{
//					continue;
//				}						    
//			
//				double d= Math.abs(uas1.getLocation().x-uas2.getLocation().x)+Math.abs(uas1.getLocation().y-uas2.getLocation().y); //Math.max(uas1.getLocation().distance(uas2.getLocation()),0);
//				if(d<tempProximityToDanger)
//				{
//					tempProximityToDanger=d;
//				}				
//				
//			}
//	    	uas1.setTempDistanceToDanger(tempProximityToDanger);
//			if (tempProximityToDanger < uas1.getDistanceToDanger())
//			{
//				uas1.setDistanceToDanger(tempProximityToDanger);	
//				System.out.println("   dd "+tempProximityToDanger);
//			}		
//							
//		}	    
		
	}



}
