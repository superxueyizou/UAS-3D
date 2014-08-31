package saa.collsionavoidance.acasx3d;

public class ACASX3DGen 
{

	public ACASX3DGen() 
	{
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("hi!Process starts...");
		long startTime = System.currentTimeMillis();
		ACASX3DMDP mdp = new ACASX3DMDP();
		long time1 = System.currentTimeMillis();
		System.out.println("MDP building Done! The running time is "+ (time1-startTime)/1000 +" senconds.");
		MDPValueIteration mdpVI = new MDPValueIteration(mdp, 1.0,0.5);
		long time2 = System.currentTimeMillis();
		System.out.println("MDP Value Iteration Done! The running time is "+ (time2-time1)/1000 +" senconds.");
		mdpVI.storeQValues();
		long time3 = System.currentTimeMillis();
		System.out.println("MDP QValue Store Done! The running time is "+ (time3-time2)/1000 +" senconds.");
		
		ACASX3DDTMC dtmc = new ACASX3DDTMC();
		long time4 = System.currentTimeMillis();
		System.out.println("DTMC building Done! The running time is "+ (time4-time3)/1000 +" senconds.");
//		System.out.println("DTMC building Done! The running time is "+ (time4-startTime)/1000 +" senconds.");
		DTMCValueIteration dtmcVI = new DTMCValueIteration(dtmc);
		long time5 = System.currentTimeMillis();
		System.out.println("DTMC Value Iteration Done! The running time is "+ (time5-time4)/1000 +" senconds.");
		dtmcVI.storeValues();
		long time6 = System.currentTimeMillis();
		System.out.println("DTMC values Storage Done! The running time is "+ (time6-time5)/1000 +" senconds.");
		
		System.out.println("Done! The total running time is "+ (time6-startTime)/1000 +" senconds.");

	}

}
