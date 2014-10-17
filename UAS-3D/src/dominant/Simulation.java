package dominant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import configuration.Configuration;
import configuration.IntruderConfig;
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
		String[] params = new String[]{"-file", "src/dominant/MaxNMAC.params"}; //MaxNMAC, MaxNMACRand
		ParameterDatabase database = Evolve.loadParameterDatabase(params);
		EvolutionState eState= Evolve.initialize(database, 0);
		eState.startFresh();
		int result=EvolutionState.R_NOTDONE;		
		
		String title = "generation,ownshipVy,ownshipGs,ownshipBearing,"+
				   "intruder1OffsetY,intruder1R,intruder1Theta,intruder1Vy,intruder1Gs,intruder1Bearing,"+  
//				   "intruder2OffsetY,intruder2R,intruder2Theta,intruder2Vy,intruder2Gs,intruder2Bearing,"+  
				   "fitness," +"accident"+"\n";
		boolean isAppending = false;
		String label = database.getLabel();
		String problemName= (String) label.subSequence(label.lastIndexOf("\\")+1, label.lastIndexOf("."));
				
		long startTime=System.currentTimeMillis();
		int i=1;
		while(result == EvolutionState.R_NOTDONE)
		{
			result=eState.evolve();
			System.out.println("simulation of generation "+i +" finished :)");
						
			if(simDataSet.size()>=200)
			{  				
				UTILS.writeDataSet2CSV(problemName + "Dataset.csv", title, simDataSet,isAppending);
				isAppending =true;
				simDataSet.clear();
			}
			i++;
		}	
		long endTime=System.currentTimeMillis();
		System.out.println(String.format("This evolution takes %d seconds", (endTime-startTime)/1000));
				
		eState.finish(result);	
		Evolve.cleanup(eState);	
		
		Object[] options= new Object[]{"Recurrence","Close"};
		int confirmationResult = JOptionPane.showOptionDialog(null, "choose the next step", "What's next", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, 0);
		
		if (confirmationResult == 0 )
		{
			String str = UTILS.readLastLine(new File(problemName+"Statics.stat"), "utf-8").trim();
			String[] pArr= str.split(" ");
					
			Configuration config = Configuration.getInstance();
			
			config.ownshipConfig.ownshipVy=Double.parseDouble(pArr[0]);
			config.ownshipConfig.ownshipGs=Double.parseDouble(pArr[1]);
			config.ownshipConfig.ownshipBearing=Double.parseDouble(pArr[2]);
			
			IntruderConfig intruderConfig1=new IntruderConfig();
			intruderConfig1.intruderOffsetY=Double.parseDouble(pArr[3]);
			intruderConfig1.intruderR=Double.parseDouble(pArr[4]);
			intruderConfig1.intruderTheta=Double.parseDouble(pArr[5]);			
			intruderConfig1.intruderVy=Double.parseDouble(pArr[6]);
			intruderConfig1.intruderGs=Double.parseDouble(pArr[7]);
			intruderConfig1.intruderBearing=Double.parseDouble(pArr[8]);
			config.intrudersConfig.put("intruder1", intruderConfig1);
			
//			IntruderConfig intruderConfig2=new IntruderConfig();
//			intruderConfig2.intruderOffsetY=Double.parseDouble(pArr[9]);
//			intruderConfig2.intruderR=Double.parseDouble(pArr[10]);
//			intruderConfig2.intruderTheta=Double.parseDouble(pArr[11]);			
//			intruderConfig2.intruderVy=Double.parseDouble(pArr[12]);
//			intruderConfig2.intruderGs=Double.parseDouble(pArr[13]);
//			intruderConfig2.intruderBearing=Double.parseDouble(pArr[14]);
//			config.intrudersConfig.put("intruder21", intruderConfig2);
	    		
			System.out.println("\nRecurrenceWithGUI");
			System.out.println(config);
			SimulationWithUI.main(null);
		}	
		else if (confirmationResult == 2 )
		{
			Evolve.cleanup(eState);	
		}
		
	}
	
}
