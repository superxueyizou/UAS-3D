/**
 * 
 */
package dominant;

import java.util.Arrays;

import modeling.SAAModel;
import modeling.SimInitializer;
import modeling.observer.AccidentDetector;
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
		
		SAAModel simState= new SAAModel(785945568, config, true); 	
		simState.reset();//reset the simulation. Very important!
    	SimInitializer.generateSimulation(simState, config);   		
		simState.start();	
		do
		{
			if (!simState.schedule.step(simState))
			{
				break;
			}
		} while(simState.schedule.getSteps()< 50);			
		
		UAS ownship = (UAS)simState.uasBag.get(0);

		int numAccident =((AccidentDetector)simState.observerBag.get(2)).getNoAccidents();// index 2 is AccidentDetector, see SimInitializer.java
		float fitness=0;
		if(numAccident>0)
		{
			fitness=1.0f;
			System.out.println(Arrays.toString(ind2.genome));
			System.out.println(config+"  "+simState.seed()+" --> "+ownship.getMinProximity()+numAccident);
		}
		else
		{
			fitness= (float) (1.0/(1+ownship.getMinProximity().toValue()));	
		}
        
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        ((SimpleFitness)ind2.fitness).setFitness(   state,            
										            fitness,/// ...the fitness...
										            false);///... is the individual ideal?  Indicate here...
        
        StringBuilder dataItem = new StringBuilder();
    	dataItem.append(state.generation+",");
    	for (int i=0; i< ind2.genome.length; i++)
    	{
    		dataItem.append(ind2.genome[i]+",");
    		
    	}
    	dataItem.append(fitness+",");
    	dataItem.append(numAccident);
    	Simulation.simDataSet.add(dataItem.toString());        
        MyStatistics.accidents[state.generation]+= numAccident;

        ind2.evaluated = true;
        simState.finish();
	}

}


//final double ft2NMRate=0.000164578833693305;
//final double fps2KnotRate=0.592484;
//TCAS3D tcas3d= new TCAS3D();
//Vect3 so= Vect3.makeXYZ(hostUAS.getLocation().x*ft2NMRate,hostUAS.getLocation().z*ft2NMRate,hostUAS.getLocation().y);
//Vect3 si= Vect3.makeXYZ(intruder.getLocation().x*ft2NMRate,intruder.getLocation().z*ft2NMRate,intruder.getLocation().y);
//Velocity vo= Velocity.makeVxyz(hostUAS.getVelocity().x*fps2KnotRate,hostUAS.getVelocity().z*fps2KnotRate,hostUAS.getVelocity().y);
//Velocity vi= Velocity.makeVxyz(intruder.getVelocity().x*fps2KnotRate,intruder.getVelocity().z*fps2KnotRate,intruder.getVelocity().y);
//double D=500*ft2NMRate;
//double H=100;
//double B=0;
//double T=1000;
//boolean result= tcas3d.conflict(so, vo, si, vi, D, H, B, T);
//System.out.println(result);