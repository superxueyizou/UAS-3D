/**
 * 
 */
package dominant;

import tools.Configuration;
import modeling.SAAModel;
import modeling.SimInitializer;
import modeling.uas.UAS;
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.DoubleVectorIndividual;

/**
 * @author Xueyi Zou
 *
 */
public class MaxNMAC extends Problem implements SimpleProblemForm 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Configuration config = Configuration.getInstance(); 

	/* (non-Javadoc)
	 * @see ec.simple.SimpleProblemForm#evaluate(ec.EvolutionState, ec.Individual, int, int)
	 */
	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) 
	{
		// TODO Auto-generated method stub
		if (ind.evaluated) return;

        if (!(ind instanceof DoubleVectorIndividual))
            state.output.fatal("Whoa!  It's not a DoubleVectorIndividual!!!",null);        
      
        DoubleVectorIndividual ind2 = (DoubleVectorIndividual)ind;
      
        double selfStdDev= ind2.genome[0];
        double selfVx= ind2.genome[1];
        double selfVy= ind2.genome[2];        
        double headOnOffset = ind2.genome[3];
        double headOnStdDev = ind2.genome[4];		
		double headOnVx = ind2.genome[5];
		double headOnVy = ind2.genome[6];				
		
		config.ownshipConfig.ownshipStdDevY = selfStdDev;
		config.ownshipConfig.ownshipVx = selfVx;
		config.ownshipConfig.ownshipVy= selfVy;    		
//		CONFIGURATION.headOnOffsetY= headOnOffset;
//		CONFIGURATION.headOnStdDevY=headOnStdDev;    			
//		CONFIGURATION.headOnVx =headOnVx;
//		CONFIGURATION.headOnVy =headOnVy;
		
		SAAModel simState= new SAAModel(785945568, CONFIGURATION.worldX, CONFIGURATION.worldY, CONFIGURATION.worldZ,false); 	
   		SimInitializer sBuilder = new SimInitializer(simState);
		sBuilder.generateSimulation();
		    		
//****************************************************************************************
		
		simState.start();	
		
		do
		{
			if (!simState.schedule.step(simState))
			{
				break;
			}
		} while(simState.schedule.getSteps()<= 21);	
		
		simState.finish();
//****************************************************************************************
		UAS uas1 = (UAS)simState.uasBag.get(0);   
		UAS uas2 = (UAS)simState.uasBag.get(1); 
		float rawFitness= (float)uas1.getMinDistanceToDanger();  	
//		System.out.println(rawFitness);
		float fitness = 1/(1+Math.max(0,rawFitness));
         
//		System.out.println("uas1 location: "+uas1.getLocation());
//		System.out.println("uas2 location: "+uas2.getLocation());

        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										            false);///... is the individual ideal?  Indicate here...
        
        ind2.evaluated = true;
        System.out.println("individual result: fitness[[ " + fitness +" ]]\n" );
        
        //if(fitness >0.9)
        {
        	StringBuilder dataItem = new StringBuilder();
        	dataItem.append(state.generation+",");
        	for (int i=0; i< ind2.genome.length; i++)
        	{
        		dataItem.append(ind2.genome[i]+",");
        		
        	}
        	dataItem.append(fitness+",");
        	dataItem.append(simState.aDetector.getNoAccidents());
        	Simulation.simDataSet.add(dataItem.toString());
        
        }
        MyStatistics.accidents[state.generation]+= simState.aDetector.getNoAccidents();

	}

}
