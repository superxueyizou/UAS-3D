/**
 * 
 */
package dominant;

import tools.CONFIGURATION;
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
public class MaxOscillation extends Problem implements SimpleProblemForm 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

		CONFIGURATION.selfStdDevY = selfStdDev;
		CONFIGURATION.selfVx = selfVx;
		CONFIGURATION.selfVy= selfVy;    		
		CONFIGURATION.headOnOffsetY= headOnOffset;
		CONFIGURATION.headOnStdDevY=headOnStdDev;    			
		CONFIGURATION.headOnVx =headOnVx;
		CONFIGURATION.headOnVy =headOnVy;
		
       	long time = System.nanoTime();
		SAAModel simState= new SAAModel(785945568, CONFIGURATION.worldX, CONFIGURATION.worldY, CONFIGURATION.worldZ, false); 	
		
   		SimInitializer sBuilder = new SimInitializer(simState);
		sBuilder.generateSimulation();
		
		
   		for(int m=0; m<simState.uasBag.size(); m++)
		{
			UAS uas1 = (UAS)simState.uasBag.get(m);
			for(int n=m+1; n<simState.uasBag.size(); n++)
			{
				UAS uas2 = (UAS)simState.uasBag.get(n);
				if(uas1.getLocation().distance(uas2.getLocation())<=100)
				{
	    			
					 ((SimpleFitness)ind2.fitness).setFitness(   state,            
					            0,/// ...the fitness...
					            false);///... is the individual ideal?  Indicate here...

					 ind2.evaluated = true;
					return;
				}
				
			}
			
		}
		
//****************************************************************************************
		
   		simState.start();	
		
		do
		{
			if (!simState.schedule.step(simState))
			{
				break;
			}
		} while(simState.schedule.getSteps()< 41);	
		
		simState.finish();
//****************************************************************************************
		
		double totalOscillaitonNo = 0;				
		for(int j=0; j<simState.uasBag.size(); j++)
		{
			UAS uas = (UAS)simState.uasBag.get(j);
			System.out.println(uas.getNumOscillation());
 
			totalOscillaitonNo += uas.getNumOscillation();
		}
		float rawFitness= (float)totalOscillaitonNo/(simState.uasBag.size()*simState.schedule.getSteps());  

		float fitness = rawFitness;
        
        
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										           false);///... is the individual ideal?  Indicate here...
        
        ind2.evaluated = true;
        System.out.println("individual result: selfStdDev("+selfStdDev+ "), selfVx("+selfVx+ "), selfVy("+selfVy+"), headOnOffsetY("+ headOnOffset+"), headOnStdDev("+ headOnStdDev + "); fitness[[ " + fitness +" ]]" );
        System.out.println();
        
        
        
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
