package modeling;

import modeling.encountergenerator.IntruderFactory;
import modeling.encountergenerator.OwnshipGenerator;
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
	private SAAModel state;	
	private Configuration config;

	public SimInitializer(SAAModel simState)
	{
		state = simState;
		config= Configuration.getInstance();
	}
	
	public void generateSimulation()
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
	 	
	    for(Object o : state.uasBag)
	    {
	    	UAS uas = (UAS)o;
	    	uas.getCaa().init();	
	    }			
	}
	
}
