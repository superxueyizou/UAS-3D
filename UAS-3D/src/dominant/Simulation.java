/**
 * 
 */
package dominant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tools.UTILS;
import ec.*;
import ec.util.*;

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
		String[] params = new String[]{"-file", "src/dominant/MaxNMAC.params"}; //MaxOscillation, MaxNMAC, MaxArea,RandMaxNMAC, RandMaxOscillation, RandMaxArea
		ParameterDatabase database = Evolve.loadParameterDatabase(params);
		EvolutionState eState= Evolve.initialize(database, 0);
		eState.startFresh();
		int result=EvolutionState.R_NOTDONE;
		
		
		String title = "generation,selfStdDev,selfVx,selfVy,"+
				   "headOnOffsetY,headOnStdDev,headOnVx,headOnVy,"+                
                "fitness," +"accident"+"\n";
		boolean isAppending = false;
		String label = database.getLabel();
		String fileName= (String) label.subSequence(label.lastIndexOf("/")+1, label.lastIndexOf("."));
		
		int i=0;
		long startTime=System.currentTimeMillis();
		while(result == EvolutionState.R_NOTDONE)
		{
			result=eState.evolve();
			System.out.println("simulation of generation "+i +" finished :)");
						
			if(simDataSet.size()>=200)
			{  				
				UTILS.writeDataSet2CSV(fileName + "Dataset.csv", title, simDataSet,isAppending);
				isAppending =true;
				simDataSet.clear();
			}
			i++;
		}	
		long endTime=System.currentTimeMillis();
		System.out.println(String.format("This evolution takes %d seconds", (endTime-startTime)/1000));
//		for(int j=0; j<MyStatistics.accidents.length; j++)
//		{
//			System.out.print(MyStatistics.accidents[j]);
//		}
				
		eState.finish(result);		
		Object[] options= new Object[]{"Recurrence","Close"};
		int confirmationResult = JOptionPane.showOptionDialog(null, "choose the next step", "What's next", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, 0);
		
		if (confirmationResult == 0 )
		{
//			String str = UTILS.readLastLine(new File(fileName+"Statics.stat"), "utf-8").trim();
//			String[] pArr= str.split(" ");
//			//System.out.println(pArr[3]);
//					
//			CONFIGURATION.selfStdDevY=Double.parseDouble(pArr[0]);			
//			CONFIGURATION.selfVx= Double.parseDouble(pArr[1]);
//			CONFIGURATION.selfVy=Double.parseDouble(pArr[2]);
//			CONFIGURATION.headOnOffsetY=Double.parseDouble(pArr[3]);
//			CONFIGURATION.headOnStdDevY=Double.parseDouble(pArr[4]);
//			CONFIGURATION.headOnVx=Double.parseDouble(pArr[5]);	
//			CONFIGURATION.headOnVy=Double.parseDouble(pArr[6]);			
//    		
//			System.out.println("\nRecurrenceWithGUI");
//			SimulationWithUI.main(null);
		}	
		else if (confirmationResult == 2 )
		{
			Evolve.cleanup(eState);	
		}
		
	}
	
}
