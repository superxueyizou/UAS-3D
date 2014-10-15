package saa.collsionavoidance.acasx2d;

public class ACASXGen 
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("hi!Process starts...");
		long startTime = System.currentTimeMillis();
		ACASXMDP mdp = new ACASXMDP();
		long time1 = System.currentTimeMillis();
		System.out.println("MDP building Done! The running time is "+ (time1-startTime)/1000 +" senconds.");
		ValueIteration vi = new ValueIteration(mdp, 1.0,0.5);
		long midTime = System.currentTimeMillis();
		System.out.println("Value Iteration Done! The running time is "+ (midTime-time1)/1000 +" senconds.");
		vi.storeQValues();
		long endTime = System.currentTimeMillis();
		System.out.println("QValue Store Done! The running time is "+ (endTime-midTime)/1000 +" senconds.");
		System.out.println("Done! The total running time is "+ (endTime-startTime)/1000 +" senconds.");

	}

}
