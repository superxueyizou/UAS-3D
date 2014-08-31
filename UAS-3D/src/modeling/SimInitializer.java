package modeling;

import modeling.encountergenerator.HeadOnGenerator;
import modeling.encountergenerator.SelfGenerator;
import modeling.uas.UAS;
import sim.util.Double3D;
import tools.CONFIGURATION;
/**
 *
 * @author Xueyi Zou
 * This class is used to build/initialize the simulation.
 * Called for by SAAModelWithUI class
 */
public class SimInitializer
{
	public SAAModel state;

	public SimInitializer(SAAModel simState)
	{
		state = simState;
	}
	
	public  void generateSimulation()
	{	
		double selfLocX;
		double selfLocY;
		double selfLocZ;
		
		double alertTime=21;

		selfLocX= - CONFIGURATION.worldX/2;
    	selfLocY=0;
    	selfLocZ=0;
    	if(state.runningWithUI)
    	{
    		double vx = CONFIGURATION.selfVx;//Math.abs(CONFIGURATION.selfVx*(1+state.random.nextGaussian()));
    		double vy = CONFIGURATION.selfVy;//Math.abs(CONFIGURATION.selfVy*(1+state.random.nextGaussian()));
    		double vz = CONFIGURATION.selfVz;
        	new SelfGenerator(state,selfLocX, selfLocY,selfLocZ, vx, vy, vz).execute();
    	}
    	else
    	{
        	new SelfGenerator(state,selfLocX, selfLocY,selfLocZ, CONFIGURATION.selfVx, CONFIGURATION.selfVy, CONFIGURATION.selfVz).execute();
    	}
	
	    if(CONFIGURATION.headOnSelected==1)
	    {  		    	
	    	if(state.runningWithUI)
	    	{
		    	for(int i=0; i<CONFIGURATION.headOnIntruders; i++)
		    	{		    		
		    		double vx =-CONFIGURATION.headOnVx;
		    		double vy =CONFIGURATION.headOnVy;
		    		double vz =CONFIGURATION.headOnVz;
		    		double offsetY = CONFIGURATION.headOnOffsetY;		
		    		double offsetZ = CONFIGURATION.headOnOffsetZ;	
		    		
		    		Double3D location = new Double3D(selfLocX+alertTime*(CONFIGURATION.selfVx+CONFIGURATION.headOnVx), selfLocY+offsetY, selfLocZ+offsetZ);
		    		new HeadOnGenerator(state, location,vx, vy, vz).execute();	  			    	
		    	}
	    	}
	    	else
	    	{
	    		double vx =-CONFIGURATION.headOnVx;
	    		double vy =CONFIGURATION.headOnVy;
	    		double vz =CONFIGURATION.headOnVz;
	    		double offsetY = CONFIGURATION.headOnOffsetY;
	    		double offsetZ = CONFIGURATION.headOnOffsetZ;
	    			    		
	    		Double3D location = new Double3D(selfLocX+alertTime*(CONFIGURATION.selfVx+CONFIGURATION.headOnVx), selfLocY+offsetY, selfLocZ+offsetZ);
	    		new HeadOnGenerator(state,location,vx, vy, vz).execute();		    		
	    	}	    
	    }	
	
	    for(Object o : state.uasBag)
	    {
	    	UAS uas = (UAS)o;
	    	uas.getCaa().init();	
	    }
				
//		System.out.println("Simulation stepping begins!");
//		System.out.println("====================================================================================================");		
			
	}
	
}
