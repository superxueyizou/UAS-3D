/**
 * 
 */
package dominant;

import modeling.SAAModel;
import modeling.SimInitializer;
import modeling.uas.UAS;
import configuration.Configuration;
import configuration.IntruderConfig;
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
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see ec.simple.SimpleProblemForm#evaluate(ec.EvolutionState, ec.Individual, int, int)
	 */
	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) 
	{
		if (ind.evaluated) return;

        if (!(ind instanceof DoubleVectorIndividual))
            state.output.fatal("Whoa!  It's not a DoubleVectorIndividual!!!",null);        
      
        DoubleVectorIndividual ind2 = (DoubleVectorIndividual)ind;
             
        double ownshipVy= ind2.genome[0];
        double ownshipGs= ind2.genome[1];
        double ownshipBearing= ind2.genome[2];
        
        double intruder1OffsetY= ind2.genome[3];
        double intruder1R= ind2.genome[4];
        double intruder1Theta= ind2.genome[5];
        double intruder1Vy= ind2.genome[6];
        double intruder1Gs= ind2.genome[7];
        double intruder1Bearing= ind2.genome[8];
 				
		Configuration config = Configuration.getInstance();
		
		config.ownshipConfig.ownshipVy=ownshipVy;
		config.ownshipConfig.ownshipGs=ownshipGs;		
		config.ownshipConfig.ownshipBearing=ownshipBearing;
		
		IntruderConfig intruderConfig1=new IntruderConfig();		
		intruderConfig1.intruderOffsetY=intruder1OffsetY;
		intruderConfig1.intruderR=intruder1R;
		intruderConfig1.intruderTheta=intruder1Theta;
		intruderConfig1.intruderVy=intruder1Vy;
		intruderConfig1.intruderGs=intruder1Gs;
		intruderConfig1.intruderBearing=intruder1Bearing;
		config.intrudersConfig.put("intruder1", intruderConfig1);
				
		SAAModel simState= SAAModel.getInstance(785945568, config, false); 	
    	SimInitializer.generateSimulation(simState, config);   		
		simState.start();	
		do
		{
			if (!simState.schedule.step(simState))
			{
				break;
			}
		} while(simState.schedule.getSteps()<= 50);	

		simState.finish();
		
		UAS ownship = (UAS)simState.uasBag.get(0);
		
//		double globalMinDistanceToDanger=Double.MAX_VALUE;
//		for(int j=0; j<simState.uasBag.size(); j++)
//		{
//			UAS uas = (UAS)simState.uasBag.get(j);
//			double minDistanceToDanger=uas.getMinDistanceToDanger();
//			if(minDistanceToDanger<globalMinDistanceToDanger)
//			{
//				globalMinDistanceToDanger = minDistanceToDanger;
//			}
//			
//		}	
//		System.out.println(globalMinDistanceToDanger);
		

		float fitness = (float) (1.0/(1+ownship.getMinDistanceToDanger()));		
        
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										            (fitness==1));///... is the individual ideal?  Indicate here...
        
        StringBuilder dataItem = new StringBuilder();
    	dataItem.append(state.generation+",");
    	for (int i=0; i< ind2.genome.length-1; i++)
    	{
    		dataItem.append(ind2.genome[i]+",");
    		
    	}
    	dataItem.append(fitness+",");
    	dataItem.append(simState.getaDetector().getNoAccidents());
    	Simulation.simDataSet.add(dataItem.toString());        
        MyStatistics.accidents[state.generation]+= simState.getaDetector().getNoAccidents();

        ind2.evaluated = true;
        simState.reset();//reset the simulation. Very important!
	}

}
