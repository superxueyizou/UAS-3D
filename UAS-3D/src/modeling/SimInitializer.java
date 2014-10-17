package modeling;

import sim.util.Double3D;
import modeling.encountergenerator.IntruderFactory;
import modeling.encountergenerator.OwnshipGenerator;
import modeling.observer.AccidentDetector;
import modeling.observer.OscillationCalculator;
import modeling.observer.ProximityMeasurer;
import modeling.uas.UAS;
import configuration.Configuration;
/**
 *
 * @author Xueyi Zou
 * This class is used to build/initialize the simulation.
 * Called for by SAAModelWithUI class
 */
public class SimInitializer
{	
	public static void generateSimulation(SAAModel state, Configuration config)
	{	
		UAS ownship = new OwnshipGenerator(state,"ownship",-0.4*config.globalConfig.worldX, 0,0, config.ownshipConfig).execute();
		state.uasBag.add(ownship);
		state.allEntities.add(ownship);
		
		for(String intruderAlias: config.intrudersConfig.keySet())
		{
			UAS intruder=IntruderFactory.generateIntruder(state, ownship, intruderAlias,config.intrudersConfig.get(intruderAlias));
			state.uasBag.add(intruder);
			state.allEntities.add(intruder);
		}
		
	    AccidentDetector aDetector= new AccidentDetector(state.getNewID(), new Double3D());
	    ProximityMeasurer pMeasurer= new ProximityMeasurer(state.getNewID(), new Double3D());
	    OscillationCalculator oCalculator= new OscillationCalculator(state.getNewID(), new Double3D());
	    state.observerBag.add(pMeasurer);
	    state.observerBag.add(oCalculator);
	    state.observerBag.add(aDetector);// index is 2
	    
	    state.allEntities.add(pMeasurer);
	    state.allEntities.add(oCalculator);
	    state.allEntities.add(aDetector);
	 	
	    for(Object o : state.uasBag)
	    {
	    	UAS uas = (UAS)o;
	    	uas.getCaa().init();	
	    }			
	}
	
}
