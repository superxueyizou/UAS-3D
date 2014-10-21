package dominant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import tools.UTILS;
import configuration.Configuration;
import ec.EvolutionState;
import ec.Evolve;
import ec.Individual;
import ec.simple.SimpleStatistics;
import ec.util.Output;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import ec.vector.DoubleVectorIndividual;

/**
 * @author xueyi
 * simulation with GA as harness
 *
 */
public class Simulation
{
	protected static List<String> simDataSet = new ArrayList<>(200);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		final int TRIALS=10;
		final boolean needsRecurrence=false;//(TRIALS==1);
		String problemName="MaxNMAC";//MaxNMAC, MaxNMACRand

		String title = "generation,ownshipVy,ownshipGs,ownshipBearing,"+
				   "intruder1OffsetY,intruder1R,intruder1Theta,intruder1Vy,intruder1Gs,intruder1Bearing,"+  
				   "fitness," +"accident";
		File parameterFile= new File("src/dominant/"+problemName+".params");
		ParameterDatabase dBase= new ParameterDatabase(parameterFile, new String[]{"-file", parameterFile.getCanonicalPath()});		
		
		long startTime=System.currentTimeMillis();
		EvolutionState evaluatedState=null;
		for(int trial=1; trial<=TRIALS; trial++)
		{		
			System.out.println("Trial "+trial+"----------------------------------------");
			UTILS.writeDataItem2CSV(problemName + "/dataset"+trial+".csv", title, false);
			
//			ParameterDatabase copy = (ParameterDatabase) (DataPipe.copy(dBase));
			ParameterDatabase child = new ParameterDatabase();
			child.addParent(dBase);
			child.set(new Parameter("stat.file"), "$"+problemName+"/statics"+trial+".stat");
			child.set(new Parameter("stat.child.0.file"), "$"+problemName+"/staticsP"+trial+".stat");
			
			Output out = Evolve.buildOutput();
//			out.getLog(0).muzzle=true;//stdout
//			out.getLog(1).muzzle=true;//stderr
			
			evaluatedState= Evolve.initialize(child, 0, out);
			evaluatedState.startFresh();
			int result = EvolutionState.R_NOTDONE;
			while (result==EvolutionState.R_NOTDONE)
			{
				result = evaluatedState.evolve();
				if(simDataSet.size()>500)
				{
					UTILS.writeDataSet2CSV(problemName + "/dataset"+trial+".csv", null, simDataSet,true);				
					simDataSet.clear();
				}
			}	
			
			if(simDataSet.size()>0)
			{
				UTILS.writeDataSet2CSV(problemName + "/dataset"+trial+".csv", null, simDataSet,true);				
				simDataSet.clear();
			}

			if(needsRecurrence)
			{
				Object[] options= new Object[]{"Recurrence","Close"};
				int confirmationResult = JOptionPane.showOptionDialog(null, "choose the next step", "What's next", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, 0);
				
				if (confirmationResult == 0 )
				{
					recurrenceWithGUI(evaluatedState);
				}	

			}
			
			evaluatedState.finish(result);	
			Evolve.cleanup(evaluatedState);	
		}	
		
		long endTime=System.currentTimeMillis();
		System.out.println(String.format("These evolutions take %d seconds", (endTime-startTime)/1000));
	}
	

	
	public static void recurrenceWithGUI(EvolutionState evaluatedState) throws IOException
	{
		Individual[] inds = ((SimpleStatistics)(evaluatedState.statistics)).getBestSoFar();
			
	    DoubleVectorIndividual ind2 = (DoubleVectorIndividual)inds[0];          
	    Configuration config =MaxNMAC.ind2Config(ind2);
	    
		System.out.println("\nRecurrenceWithGUI");
		System.out.println(config);
		SimulationWithUI.main(null);
	}
	
}
