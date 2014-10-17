package dominant;

import modeling.SAAModel;
import modeling.SimInitializer;
import modeling.observer.AccidentDetector;
import modeling.uas.UAS;
import configuration.Configuration;
import configuration.IntruderConfig;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String str = "56.928250746505256 292.2708469901651 0.0 970.3981767124237 6498.582172479638 15.212951449948095 -34.30815513080462 298.2252380001182 -143.34806150439846 ";
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
		
		SAAModel simState=new SAAModel(785945568, config, true); 	
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
	
		fitness=1.0f;
		System.out.println(config+"  "+simState.seed()+" --> "+ownship.getMinProximity()+numAccident);
	
	}

}
