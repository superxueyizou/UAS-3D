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

//		UAS uas1= (UAS) state.uasBag.get(0);
//		UAS uas2= (UAS) state.uasBag.get(1);
//		double d= uas1.getLocation().distance(uas2.getLocation());//Math.abs(uas1.getLocation().x-uas2.getLocation().x)+Math.abs(uas1.getLocation().y-uas2.getLocation().y); 
//    	uas1.setTempDistanceToDanger(d);
//    	uas2.setTempDistanceToDanger(d);
//    	if (d < uas1.getMinDistanceToDanger())
//		{
//			uas1.setMinDistanceToDanger(d);	
//		}	
//
//    	if (d < uas2.getMinDistanceToDanger())
//		{
//			uas2.setMinDistanceToDanger(d);	
//		}		
	}



}
