/**
 * 
 */
package saa.collsionavoidance.acasx3d;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import sim.util.Double2D;

/**
 * @author viki
 *
 */
public class ACASX3DDTMC
{
	public static final int nr = 40;//40
	public static final int nrv= 100;//100
	public static final int ntheta= 36;//36
	
	public static final double UPPER_R=20000.0;
	public static final double UPPER_RV=1000.0;
	public static final double UPPER_THETA=180.0;
	
	public static final double rRes = UPPER_R/nr;
	public static final double rvRes = UPPER_RV/nrv;
	public static final double thetaRes = UPPER_THETA/ntheta;

	private final int numUStates= (nr+1)*(nrv+1)*(2*ntheta+1);
	private ACASX3DUState[] uStates= new ACASX3DUState[numUStates];
	
	public final static double WHITE_NOISE_SDEV=3.0;
	private ArrayList<ThreeTuple<Double, Double, Double>> sigmaPoints = new ArrayList<>();

	
	public ACASX3DDTMC() 
	{		
		for(int rIdx=0; rIdx<=nr;rIdx++)//
		{
			for(int rvIdx=0; rvIdx<=nrv;rvIdx++)//
			{
				for(int thetaIdx=-ntheta; thetaIdx<=ntheta;thetaIdx++)
				{
					ACASX3DUState state = new ACASX3DUState(rIdx, rvIdx, thetaIdx);							
					uStates[state.getOrder()]=state;						
				}
			}
		}
		
		sigmaPoints.add(new ThreeTuple<>(0.0,0.0,1.0/3));
		sigmaPoints.add(new ThreeTuple<>(Math.sqrt(3.0)*2*WHITE_NOISE_SDEV,0.0,1.0/6));
		sigmaPoints.add(new ThreeTuple<>(-Math.sqrt(3.0)*2*WHITE_NOISE_SDEV,0.0,1.0/6));
		sigmaPoints.add(new ThreeTuple<>(0.0,Math.sqrt(3.0)*2*WHITE_NOISE_SDEV,1.0/6));
		sigmaPoints.add(new ThreeTuple<>(0.0,-Math.sqrt(3.0)*2*WHITE_NOISE_SDEV,1.0/6));
	}

	/**
	 * Get the set of states associated with the DTMC. 
	 * @return the set of states associated with the DTMC
	 */
	public ACASX3DUState[] states()
	{		
		return uStates;
	}

	
	public Map<ACASX3DUState,Double> getTransitionStatesAndProbs(ACASX3DUState ustate)
	{
		Map<ACASX3DUState, Double> TransitionStatesAndProbs = new LinkedHashMap<ACASX3DUState,Double>();

		ArrayList<AbstractMap.SimpleEntry<ACASX3DUState, Double>> nextStateMapProbabilities = new ArrayList<>();
		
		for(ThreeTuple<Double, Double, Double> sigmaPoint : sigmaPoints)
		{
			double rAccel_r=sigmaPoint.x1;
			double rAccel_pr=sigmaPoint.x2;
			double sigmaP=sigmaPoint.x3;
			
			double r=ustate.getR();
			double rv=ustate.getRv();
			double theta=ustate.getTheta();
						
			Double2D vel= new Double2D(rv*Math.cos(Math.toRadians(theta)), rv*Math.sin(Math.toRadians(theta)));
			Double2D velP= new Double2D(Math.max(-UPPER_RV, Math.min(UPPER_RV, vel.x+rAccel_r)), Math.max(-UPPER_RV, Math.min(UPPER_RV, vel.y+rAccel_pr)));
			if(velP.length()>UPPER_RV)
			{
				velP=velP.resize(UPPER_RV);
			}
			double rvP=velP.length();
			
			Double2D pos= new Double2D(r,0);
			Double2D posP= new Double2D(pos.x+0.5*(vel.x+velP.x), pos.y+0.5*(vel.y+velP.y));
			if(posP.length()>UPPER_R)
			{
				posP=posP.resize(UPPER_R);
			}
			double rP=posP.length();
			
			double alpha=velP.angle()-posP.angle();
			if(alpha> Math.PI)
	 	   	{
				alpha= -2*Math.PI +alpha; 
	 	   	}
			if(alpha<-Math.PI)
	 	   	{
				alpha=2*Math.PI+alpha; 
	 	   	}
			double thetaP = Math.toDegrees(alpha);
		

			int rIdxL = (int)Math.floor(rP/rRes);
			int rvIdxL = (int)Math.floor(rvP/rvRes);
			int thetaIdxL = (int)Math.floor(thetaP/thetaRes);
			for(int i=0;i<=1;i++)
			{
				int rIdx = (i==0? rIdxL : rIdxL+1);
				int rIdxP= rIdx< 0? 0: (rIdx>nr? nr : rIdx);			
				for(int j=0;j<=1;j++)
				{
					int rvIdx = (j==0? rvIdxL : rvIdxL+1);
					int rvIdxP= rvIdx<0? 0: (rvIdx>nrv? nrv : rvIdx);
					for(int k=0;k<=1;k++)
					{
						int thetaIdx = (k==0? thetaIdxL : thetaIdxL+1);
						int thetaIdxP= thetaIdx<-ntheta? -ntheta: (thetaIdx>ntheta? ntheta : thetaIdx);
						
						ACASX3DUState nextState= new ACASX3DUState(rIdxP, rvIdxP, thetaIdxP);
						double probability= sigmaP*(1-Math.abs(rIdx-rP/rRes))*(1-Math.abs(rvIdx-rvP/rvRes))*(1-Math.abs(thetaIdx-thetaP/thetaRes));
						nextStateMapProbabilities.add(new SimpleEntry<ACASX3DUState, Double>(nextState,probability) );
					}
				}
			}	
			
		}			

		for(AbstractMap.SimpleEntry<ACASX3DUState, Double> nextStateMapProb :nextStateMapProbabilities)
		{	
			ACASX3DUState nextState=nextStateMapProb.getKey();
			if(TransitionStatesAndProbs.containsKey(nextState))
			{				
				TransitionStatesAndProbs.put(nextState, TransitionStatesAndProbs.get(nextState)+nextStateMapProb.getValue());
			}
			else
			{
				TransitionStatesAndProbs.put(nextState, nextStateMapProb.getValue());
			}		
			
		}
		
		return TransitionStatesAndProbs;
	}	
	
}

class FiveTuple<X1, X2, X3, X4, X5> 
{ 
	  public final X1 x1; 
	  public final X2 x2; 
	  public final X3 x3; 
	  public final X4 x4; 
	  public final X5 x5; 
	  public FiveTuple(X1 x1, X2 x2, X3 x3, X4 x4, X5 x5) 
	  { 
	    this.x1 = x1; 
	    this.x2 = x2;
	    this.x3 = x3;
	    this.x4 = x4;
	    this.x5 = x5;
	  } 
} 
