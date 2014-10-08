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
             
        double ownshipVx= ind2.genome[0];
        double ownshipVy= ind2.genome[1];
        double ownshipVz= ind2.genome[2];
        
        double intruder1OffsetX= ind2.genome[3];
        double intruder1OffsetY= ind2.genome[4];
        double intruder1OffsetZ= ind2.genome[5];
        double intruder1Vx= ind2.genome[6];
        double intruder1Vy= ind2.genome[7];
        double intruder1Vz= ind2.genome[8];
        
        double intruder2OffsetX= ind2.genome[9];
        double intruder2OffsetY= ind2.genome[10];
        double intruder2OffsetZ= ind2.genome[11];
        double intruder2Vx= ind2.genome[12];
        double intruder2Vy= ind2.genome[13];
        double intruder2Vz= ind2.genome[14];
				
		Configuration config = Configuration.getInstance();
		
		config.ownshipConfig.ownshipVx=ownshipVx;
		config.ownshipConfig.ownshipVy=ownshipVy;
		config.ownshipConfig.ownshipVz=ownshipVz;
		
		IntruderConfig intruderConfig1=new IntruderConfig();
		intruderConfig1.intruderOffsetX=intruder1OffsetX;
		intruderConfig1.intruderOffsetY=intruder1OffsetY;
		intruderConfig1.intruderOffsetZ=intruder1OffsetZ;
		intruderConfig1.intruderVx=intruder1Vx;
		intruderConfig1.intruderVy=intruder1Vy;
		intruderConfig1.intruderVz=intruder1Vz;
		config.intrudersConfig.put("intruder1", intruderConfig1);
		
		IntruderConfig intruderConfig2=new IntruderConfig();
		intruderConfig2.intruderOffsetX=intruder2OffsetX;
		intruderConfig2.intruderOffsetY=intruder2OffsetY;
		intruderConfig2.intruderOffsetZ=intruder2OffsetZ;
		intruderConfig2.intruderVx=intruder2Vx;
		intruderConfig2.intruderVy=intruder2Vy;
		intruderConfig2.intruderVz=intruder2Vz;
		config.intrudersConfig.put("intruder2", intruderConfig2);
		
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
		
		double globalMinDistanceToDanger=Double.MAX_VALUE;
		for(int j=0; j<simState.uasBag.size(); j++)
		{
			UAS uas = (UAS)simState.uasBag.get(j);
			double minDistanceToDanger=uas.getMinDistanceToDanger();
			if(minDistanceToDanger<globalMinDistanceToDanger)
			{
				globalMinDistanceToDanger = minDistanceToDanger;
			}
			
		}	
//		System.out.println(globalMinDistanceToDanger);
		simState.reset();

		float fitness = (float) (10000-globalMinDistanceToDanger);		
        
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										            (fitness==10000));///... is the individual ideal?  Indicate here...
        
        ind2.evaluated = true;
//        System.out.println();
        
//        if(fitness >0.9)
//        {
//        	StringBuilder dataItem = new StringBuilder();
//        	dataItem.append(state.generation+",");
//        	for (int i=0; i< ind2.genome.length-1; i++)
//        	{
//        		dataItem.append(ind2.genome[i]+",");
//        		
//        	}
//        	dataItem.append(fitness+",");
//        	dataItem.append(simState.getaDetector().getNoAccidents()+",");
//        	dataItem.append(ind2.genome[ind2.genome.length-1]);
//        	Simulation.simDataSet.add(dataItem.toString());
//        
//        }
//        MyStatistics.accidents[state.generation]+= simState.getaDetector().getNoAccidents();

	}

}
