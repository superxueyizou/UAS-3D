package modeling;

import configuration.Configuration;
import modeling.env.Entity;
import modeling.observer.AccidentDetector;
import modeling.observer.OscillationCalculator;
import modeling.observer.OscillationCounter;
import modeling.observer.ProximityMeasurer;
import modeling.uas.UAS;
import sim.util.*;
import sim.field.continuous.*;
import sim.engine.*;

public class SAAModel extends SimState
{
	private static final long serialVersionUID = 1L;
	
	public static Configuration config= Configuration.getInstance();
	private static SAAModel state=null;
	
	public boolean runningWithUI = false; 
	
	public Bag allEntities = new Bag(); // entities to load into the environment2D, important
	public Bag uasBag = new Bag();

    private int newID = 0;	
    public String information="no information now";

    public Continuous2D xzView;
    public Continuous3D environment3D;
	
    public AccidentDetector aDetector= new AccidentDetector();
    public ProximityMeasurer pMeasurer= new ProximityMeasurer();
    public OscillationCalculator oCalculator= new OscillationCalculator();
    public OscillationCounter oCounter= new OscillationCounter();
    	
	/**
	 * @param seed for random number generator
	 * @param widthX the width of the simulation environment3D
	 * @param heightY the height of the simulation environment3D
	 * @param lengthZ the length of the simulation environment3D
	 * @param UI pass true if the simulation is being run with a UI false if it is not.
	 */
    public static SAAModel getInstance(long seed, double widthX, double heightY, double lengthZ, boolean UI)
    {
    	if(state==null)
    	{
    		state=new SAAModel(seed, widthX, heightY, lengthZ, UI);
    	}
    	
    	return state;    	
    }
    
    public static SAAModel getInstance()
    {
    	if(state==null)
    	{
    		throw new NullPointerException("A singleton of SAAModel has not yet existed!");
    	}
    	
    	return state;    	
    }

	private SAAModel(long seed, double widthX, double heightY, double lengthZ, boolean UI)
    {
		super(seed);
		environment3D = new Continuous3D(1.0, widthX, heightY, lengthZ);
		xzView = new Continuous2D(1.0,widthX,lengthZ);
		runningWithUI = UI;				
	}    
		
	
	public void start()
	{
		super.start();	
		environment3D.clear();
        xzView.clear();
	
		loadEntities();
		scheduleEntities();			
	}
		

	/**
	 * A method which resets the variables for the COModel and also clears
	 * the schedule and environment2D of any entities, to be called between simulations.	 * 
	 * This method resets the newID counter so should NOT be called during a run.
	 */
	public void reset()
	{
		newID = 0;
		uasBag.clear();
		allEntities.clear();

		environment3D.clear(); //clear the environment3D
		xzView.clear();
	}
	
	public void finish()
	{
		super.finish();		
		OscillationCounter oCounter = new OscillationCounter();
		oCounter.step(this);
		
	}
	
	
	/**
	 * A method which provides a different number each time it is called, this is
	 * used to ensure that different entities are given different IDs
	 * 
	 * @return a unique ID number
	 */
	public int getNewID()
	{
		int t = newID;
		newID++;
		return t;
	}
	
	
	/**
	 * A method which adds all of the entities to the simulations environment3D.
	 */
	public void loadEntities()
	{
		for(int i = 0; i < allEntities.size(); i++)
		{
			Entity e =(Entity) allEntities.get(i);
			xzView.setObjectLocation(e, new Double2D(e.getLocation().x,e.getLocation().z).add(new Double2D(0.5*xzView.width,0.5*xzView.height)));
			environment3D.setObjectLocation(e, e.getLocation());		
		}
//		Bag a=xzView.getAllObjects();
//		for(int i=0;i<a.size();i++)
//		{
//			System.out.println(((UAS)a.get(i)).getLocation());
//		}
	}
	
	
	/**
	 * A method which adds all the entities marked as requiring scheduling to the
	 * schedule for the simulation
	 */
	public void scheduleEntities()
	{
		//loop across all items in toSchedule and add them all to the schedule
		int counter = 0;	
		if (config.globalConfig.collisionAvoidanceEnabler)
		{
			for(int i = 0; i < uasBag.size(); i++, counter++)
			{
				schedule.scheduleRepeating(((UAS)uasBag.get(i)).getCaa(), counter, 1.0);
			}	
			
		}
		
		if (config.globalConfig.selfSeparationEnabler)
		{
			for(int i = 0; i < uasBag.size(); i++, counter++)
			{
				schedule.scheduleRepeating(((UAS)uasBag.get(i)).getSsa(), counter, 1.0);
			}	
			
		}
		
		for(int i=0; i < uasBag.size(); i++,counter++)
		{
			schedule.scheduleRepeating(((UAS)uasBag.get(i)).getAp(), counter, 1.0);			
		}
		
		for(int i=0; i < uasBag.size(); i++,counter++)
		{
			schedule.scheduleRepeating((Entity) uasBag.get(i), counter, 1.0);
		}	
		schedule.scheduleRepeating(pMeasurer,counter++, 1.0);
		schedule.scheduleRepeating(oCalculator,counter++, 1.0);	
		schedule.scheduleRepeating(aDetector,counter++, 1.0);	
			
	}


	public String getInformation() {
		return information;
	}
   

}
