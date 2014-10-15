package modeling.observer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import configuration.Configuration;
import modeling.SAAModel;
import modeling.env.Constants;
import modeling.uas.UAS;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Double3D;


/**
 * 
 */

/**
 * @author xueyi
 *
 */
public class AccidentDetector implements Constants,Steppable
{
	private static final long serialVersionUID = 1L;
	
	private PrintStream ps;
	private int noAccidents=0;	
	private String accidentLogFileName="AccidentLog.txt";
	
	public AccidentDetector()
	{
		File accidentLog = new File(accidentLogFileName);
		try{
			ps= new PrintStream(new FileOutputStream(accidentLog));
		}
		catch(FileNotFoundException e)
		{
			System.out.print("File not found!");
			return;
		}
				
	}
	
	public void reset()
	{
		noAccidents=0;
		if(ps!=null)
		{
			ps.close();
		}
		File accidentLog = new File(accidentLogFileName);
		try{
			ps= new PrintStream(new FileOutputStream(accidentLog));
		}
		catch(FileNotFoundException e)
		{
			System.out.print("File not found!");
			return;
		}
	}

	/* (non-Javadoc)
	 * @see state.engine.Steppable#step(state.engine.SimState)
	 */
	@Override
	public void step(SimState simState)
	{
		if(!Configuration.getInstance().globalConfig.accidentDetectorEnabler)
		{
			return;
		}
		SAAModel state = (SAAModel)simState;		
		
		UAS ownship=(UAS) state.uasBag.get(0);

		for (int k = 1; k<state.uasBag.size(); k++)
		{
			UAS intruder=(UAS)state.uasBag.get(k);
			if(!intruder.isActive)
			{
				continue;
			}
			if (detectCollisionBetweenUAS(ownship, intruder))
			{
				addLog(Constants.AccidentType.CLASHWITHOTHERUAS, ownship.getAlias(), state.schedule.getSteps(), ownship.getLocation(), "the other UAS's is"+intruder.getAlias());
				noAccidents++;
				ownship.isActive=false;
				intruder.isActive=false;
				break;
			}
		}
			
//		UAS uas1;
//        outerLoop:
//	    for(int i=0; i<state.uasBag.size(); i++)
//		{		    	
//			uas1= (UAS)state.uasBag.get(i);
//			if(!uas1.isActive)
//			{
//				continue;
//			}
//			
//			/************
//			 * test if there is a collision with other alive UAS
//			 */
//			for (int k = i+1; k<state.uasBag.size(); k++)
//			{
//				UAS uas2=(UAS)state.uasBag.get(k);
//				if(!uas2.isActive)
//				{
//					continue;
//				}
//				if (detectCollisionBetweenUAS(uas1, uas2))
//				{
//					addLog(Constants.AccidentType.CLASHWITHOTHERUAS, uas1.getID(), state.schedule.getSteps(), uas1.getLocation(), "the other UAS's ID is"+uas2.getID());
//					noAccidents++;
//					uas1.isActive=false;
//					uas2.isActive=false;
//					continue outerLoop;
//				}
//			}
//							
//		}

	}
	
	public void addLog(AccidentType t, String ownshipAlias, long step, Double3D coor, String str)
	{
		ps.println(t.toString() +": "+ownshipAlias + "; time:"+step+"steps; location: ("+coor.x+" , "+coor.y+" , "+coor.z+")" + str);
	}
	
	
	private boolean detectCollisionBetweenUAS(UAS uas1, UAS uas2)
	{	
		double deltaHori=Math.pow((uas1.getLocation().x-uas2.getLocation().x),2)+Math.pow((uas1.getLocation().z-uas2.getLocation().z),2);
		double deltaVert=Math.abs(uas1.getLocation().y-uas2.getLocation().y);	
		return (deltaHori<=500*500)&&(deltaVert<=100);		
	}
	

	public int getNoAccidents() {
		return noAccidents;
	}

}
