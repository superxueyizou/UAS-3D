package saa.collsionavoidance.mdpLite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class ValueIterationMemoization
{
	private ACASXMDP mdp;

	private final int numStates=(2*ACASXMDP.nh+1)*(2*ACASXMDP.noV+1)*(2*ACASXMDP.niV+1)*(ACASXMDP.nt+1)*(ACASXMDP.nra);
	
	private double[] U= new double[numStates];

	/**
	 * Constructor.
	 * 
	 * @param gamma
	 *            discount &gamma; to be used.
	 */
	public ValueIterationMemoization(ACASXMDP mdp, double gamma,double epsilon) 
	{
		this.mdp=mdp;
		if (gamma > 1.0 || gamma <= 0.0) 
		{
			throw new IllegalArgumentException("Gamma must be > 0 and <= 1.0");
		}
		
		if (epsilon < 0.0) 
		{
			throw new IllegalArgumentException("epsilon must be >= 0");
		}

		double[] Udelta= new double[numStates];
		double delta;

		ACASXState[]states=mdp.states();
		// repeat
		int iteration=0;
		HashMap<Integer, HashMap<Integer, Map<Integer, Double >> > nextStates_Probs = new HashMap<>();

		do 
		{
			boolean usingMemoisation =!(iteration==0);
			System.out.println(iteration++);			
			U=Udelta.clone();
			delta = 0;			
			for (int i=0; i<numStates;i++)
			{
				ACASXState s=states[i];
				if(s.getOrder()!=i)
				{
					System.err.println("error happens in valueIteration() + s.getOrder()!=i");
				}
				ArrayList<Integer> actions= mdp.actions(s);

				double aMax = Double.NEGATIVE_INFINITY;

				HashMap<Integer, Map<Integer, Double >> action_nextstate_prob = new HashMap<>();
				for (Integer a : actions) 
				{
					double aSum=mdp.reward(s, a);
					Map<Integer,Double> TransitionStatesAndProbs;
								
					if(!usingMemoisation)
					{
						TransitionStatesAndProbs= mdp.getTransitionStatesAndProbs(i, a);
						action_nextstate_prob.put(a, TransitionStatesAndProbs);
					}
					else
					{
						TransitionStatesAndProbs=nextStates_Probs.get(i).get(a);
					}				
					
					Set<Entry<Integer,Double>> entrySet = TransitionStatesAndProbs.entrySet();							
					for (Entry<Integer,Double> entry : entrySet) 
					{		
						int nextStateOrder = entry.getKey();
						aSum += entry.getValue() * gamma *  U[nextStateOrder];
					}
						
					if (aSum > aMax) 
					{
						aMax = aSum;
					}
				}
				if(!usingMemoisation)
				{
					nextStates_Probs.put(i, action_nextstate_prob);
				}

				Udelta[i]=aMax;
				
				double aDiff = Math.abs(aMax - U[i]);
				if (aDiff > delta) 
				{
					delta = aDiff;
				}
				
			}
			
		} while (delta > epsilon);		
		U=Udelta.clone();
	}
	
	public double getQValue(int stateOrder, int action) 
	{	
		double QValue = 0;
		Map<Integer, Double> TransitionStatesAndProbs= mdp.getTransitionStatesAndProbs(stateOrder, action);
		Set<Entry<Integer, Double>> entrySet = TransitionStatesAndProbs.entrySet();

		for (Entry<Integer, Double> entry : entrySet) 
		{
			if(entry.getValue()>1)
			{
				System.err.println(entry.getValue()+"greater than 1");
			}
			int nextStateOrder=entry.getKey();
			QValue += entry.getValue()*  U[nextStateOrder];
		
		}
		return QValue;		
	}
	
	public void storeQValues() 
	{	
		FileWriter indexFileWriter = null;
		FileWriter costFileWriter = null;
		FileWriter actionFileWriter = null;
	
		try 
        {
            indexFileWriter = new FileWriter("src/saa/collsionavoidance/mdpLite/generatedFiles/indexFile",false);
            costFileWriter = new FileWriter("src/saa/collsionavoidance/mdpLite/generatedFiles/costFile",false);
            actionFileWriter = new FileWriter("src/saa/collsionavoidance/mdpLite/generatedFiles/actionFile",false);
      
            int index=0;
			for (int i=0; i<numStates;i++)
			{
				ArrayList<Integer> actions =mdp.actions(i);
    			indexFileWriter.write(index+"\n");
    			index+=actions.size();
    			for (int a :actions ) 
    			{
    				actionFileWriter.write(a+"\n");    				
    				double QValue = getQValue(i, a);
    				costFileWriter.write(QValue+"\n");
    			}			

			}
			indexFileWriter.write(index+"\n");
    		
        }
        catch(Exception e) 
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally 
        {
            try 
            {
	                if(indexFileWriter!=null)
	                {
	                	indexFileWriter.close();
	                }
	                if(costFileWriter!=null)
	                {
	                	costFileWriter.close();
	                }
	                if(actionFileWriter!=null)
	                {
	                	actionFileWriter.close();
	                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
				
	}	
	
}

