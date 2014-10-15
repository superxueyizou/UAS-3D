/**
 * 
 */
package saa.collsionavoidance.acasx2d;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author viki
 *
 */
public class ACASXMDP
{

	//"COC"-->0,"CL25"-->1, "DES25"-->2
	//"Loop"-->-1
	public static final int nt=20;
	public static final int nh = 10;//10
	public static final int noVy= 7;//14
	public static final int niVy= 7;//14
	public static final int nra=7;
	
	public static final double UPPER_H=600.0;
	public static final double UPPER_VY=42.0;
	
	public static final double hRes = UPPER_H/nh;
	public static final double oVRes = UPPER_VY/noVy;
	public static final double iVRes = UPPER_VY/niVy;

	private final int numStates= (nt+1)*(2*nh+1)*(2*noVy+1)*(2*niVy+1)*nra;
	private ACASXState[] states= new ACASXState[numStates];
	
	public final static double WHITE_NOISE_SDEV=3.0;
	private ArrayList<Tuple<Double, Double, Double>> sigmaPointsA = new ArrayList<>();
	private ArrayList<Tuple<Double, Double, Double>> sigmaPointsB = new ArrayList<>();

	
	public ACASXMDP() 
	{	
		for(int tIdx=0; tIdx<=nt;tIdx++)//
		{
			for(int hIdx=-nh; hIdx<=nh;hIdx++)//
			{
				for(int oVyIdx=-noVy; oVyIdx<=noVy;oVyIdx++)//
				{
					for(int iVyIdx=-niVy; iVyIdx<=niVy;iVyIdx++)
					{	
						for(int raIdx=0; raIdx<nra;raIdx++)//
						{
							ACASXState state = new ACASXState(tIdx,hIdx, oVyIdx, iVyIdx, raIdx);							
							states[state.getOrder()]=state;		
						}
					}
				}
			}
		}
				
		sigmaPointsA.add(new Tuple<>(0.0,0.0,1.0/2));
		sigmaPointsA.add(new Tuple<>(0.0,WHITE_NOISE_SDEV,1.0/4));
		sigmaPointsA.add(new Tuple<>(0.0,-WHITE_NOISE_SDEV,1.0/4));
			
		sigmaPointsB.add(new Tuple<>(0.0,0.0,1.0/3));
		sigmaPointsB.add(new Tuple<>(WHITE_NOISE_SDEV,0.0,1.0/6));
		sigmaPointsB.add(new Tuple<>(-WHITE_NOISE_SDEV,0.0,1.0/6));
		sigmaPointsB.add(new Tuple<>(0.0,WHITE_NOISE_SDEV,1.0/6));
		sigmaPointsB.add(new Tuple<>(0.0,-WHITE_NOISE_SDEV,1.0/6));
	}

	/**
	 * Get the set of states associated with the Markov decision process.
	 * 
	 * @return the set of states associated with the Markov decision process.
	 */
	public ACASXState[] states()
	{		
		return states;
	}


	/**
	 * Get the set of actions for state s.
	 * 
	 * @param s the state.
	 * @return the list of actions for state s.
	 */
	public ArrayList<Integer> actions(ACASXState state)
	{
		ArrayList<Integer> actions= new ArrayList<Integer>();

		if(state.getT()==0)//leaving state
		{
			actions.add(-1);//"Loop"
			return actions;
		}
		if(state.getH()==UPPER_H || state.getoVy()== UPPER_VY)
		{
			actions.add(0);//"COC"
			actions.add(4);//"SDES25"
			return actions;
		}
		if(state.getH()==-UPPER_H || state.getoVy()== -UPPER_VY)
		{
			actions.add(0);//"COC"
			actions.add(3);//"SCL25"
			return actions;
		}
		else if(state.getRa()==0)//COC
		{
			actions.add(0);//"COC"
			actions.add(1);//"CL25"
			actions.add(2);//"DES25"
			return actions;
		}
		else if(state.getRa()==1)//CL25
		{
			actions.add(0);//"COC"
			actions.add(1);//"CL25"
			actions.add(4);//"SDES25"
			actions.add(5);//"SCL42"
			return actions;
		}
		else if(state.getRa()==2)//DES25
		{
			actions.add(0);//"COC"
			actions.add(2);//"DES25"
			actions.add(3);//"SCL25"
			actions.add(6);//"SDES42"	
			return actions;
		}
		else if(state.getRa()==3)//"SCL25"
		{
			actions.add(0);//"COC"
			actions.add(3);//"SCL25"
			actions.add(4);//"SDES25"
			actions.add(5);//"SCL42"	
			return actions;
		}
		else if(state.getRa()==4)//"SDES25"
		{
			actions.add(0);//"COC"
			actions.add(3);//"SCL25"
			actions.add(4);//"SDES25"
			actions.add(6);//"SDES42"	
			return actions;
		}
		else if(state.getRa()==5)//"SCL42"
		{
			actions.add(0);//"COC"
			actions.add(3);//"SCL25"
			actions.add(4);//"SDES25"
			actions.add(5);//"SCL42"
			return actions;
		}
		else if(state.getRa()==6)//"SDES42"
		{
			actions.add(0);//"COC"
			actions.add(3);//"SCL25"	
			actions.add(4);//"SDES25"	
			actions.add(6);//"SDES42"
			return actions;
		}
							
		System.err.println("Something wrong happend in ACASXMDP.actions(State s).");
		return null;
	}
	
	/**
	 * Get the set of actions for the @code stateOrder th state s.
	 * 
	 * @param stateOrder int.
	 * @return the set of actions for stateOrder th state.
	 */
	public ArrayList<Integer> actions(int stateOrder)
	{
		ACASXState s=states[stateOrder];
		return actions(s);
	}

	
	public Map<ACASXState,Double> getTransitionStatesAndProbs(ACASXState state, int actionCode)
	{
		Map<ACASXState, Double> TransitionStatesAndProbs = new LinkedHashMap<ACASXState,Double>();
		
		if(state.getT()==0)//leaving state
		{ 
			TransitionStatesAndProbs.put(state, 1.0);		
			return TransitionStatesAndProbs;
		}
				
		double targetV=ACASXUtils.getActionV(actionCode);
		double accel=ACASXUtils.getActionA(actionCode);
		ArrayList<AbstractMap.SimpleEntry<ACASXState, Double>> nextStateMapProbabilities = new ArrayList<>();
		
		if( (accel>0 && targetV>state.getoVy() && state.getoVy()<UPPER_VY)
				|| (accel<0 && targetV<state.getoVy() && state.getoVy()>-UPPER_VY))
		{// own aircraft follows a RA other than COC			
			
			for(Tuple<Double, Double, Double> sigmaPoint : sigmaPointsA)
			{
				double oAy=accel;
				double iAy=sigmaPoint.y;
				double sigmaP=sigmaPoint.z;
				
				double hP= state.getH()+ (state.getiVy()-state.getoVy()) + 0.5*(iAy-oAy);
				double oVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getoVy()+oAy));
				double iVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getiVy()+iAy));
				int tP=state.getT()-1;
				int raP=actionCode;
				
				int hIdxL = (int)Math.floor(hP/hRes);
				int oVyIdxL = (int)Math.floor(oVyP/oVRes);
				int iVyIdxL = (int)Math.floor(iVyP/iVRes);
				for(int i=0;i<=1;i++)
				{
					int hIdx = (i==0? hIdxL : hIdxL+1);
					int hIdxP= hIdx< -nh? -nh: (hIdx>nh? nh : hIdx);			
					for(int j=0;j<=1;j++)
					{
						int oVyIdx = (j==0? oVyIdxL : oVyIdxL+1);
						int oVyIdxP= oVyIdx<-noVy? -noVy: (oVyIdx>noVy? noVy : oVyIdx);
						for(int k=0;k<=1;k++)
						{
							int iVyIdx = (k==0? iVyIdxL : iVyIdxL+1);
							int iVyIdxP= iVyIdx<-niVy? -niVy: (iVyIdx>niVy? niVy : iVyIdx);
							
							ACASXState nextState= new ACASXState(tP,hIdxP, oVyIdxP, iVyIdxP, raP);
							double probability= sigmaP*(1-Math.abs(hIdx-hP/hRes))*(1-Math.abs(oVyIdx-oVyP/oVRes))*(1-Math.abs(iVyIdx-iVyP/iVRes));
							nextStateMapProbabilities.add(new SimpleEntry<ACASXState, Double>(nextState,probability) );
						}
					}
				}

			}			
			
		}
		else
		{				
			for(Tuple<Double, Double, Double> sigmaPoint : sigmaPointsB)
			{
				double oAy=sigmaPoint.x;
				double iAy=sigmaPoint.y;
				double sigmaP=sigmaPoint.z;
				
				double hP= state.getH()+ (state.getiVy()-state.getoVy()) + 0.5*(iAy-oAy);
				double oVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getoVy()+oAy));
				double iVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getiVy()+iAy));
				int tP=state.getT()-1;
				int raP=actionCode;

				int hIdxL = (int)Math.floor(hP/hRes);
				int oVyIdxL = (int)Math.floor(oVyP/oVRes);
				int iVyIdxL = (int)Math.floor(iVyP/iVRes);
				for(int i=0;i<=1;i++)
				{
					int hIdx = (i==0? hIdxL : hIdxL+1);
					int hIdxP= hIdx< -nh? -nh: (hIdx>nh? nh : hIdx);			
					for(int j=0;j<=1;j++)
					{
						int oVyIdx = (j==0? oVyIdxL : oVyIdxL+1);
						int oVyIdxP= oVyIdx<-noVy? -noVy: (oVyIdx>noVy? noVy : oVyIdx);
						for(int k=0;k<=1;k++)
						{
							int iVyIdx = (k==0? iVyIdxL : iVyIdxL+1);
							int iVyIdxP= iVyIdx<-niVy? -niVy: (iVyIdx>niVy? niVy : iVyIdx);
							
							ACASXState nextState= new ACASXState( tP,hIdxP, oVyIdxP, iVyIdxP, raP);
							double probability= sigmaP*(1-Math.abs(hIdx-hP/hRes))*(1-Math.abs(oVyIdx-oVyP/oVRes))*(1-Math.abs(iVyIdx-iVyP/iVRes));
							nextStateMapProbabilities.add(new SimpleEntry<ACASXState, Double>(nextState,probability) );
						}
					}
				}	
				
			}			
			
		}

		for(AbstractMap.SimpleEntry<ACASXState, Double> nextStateMapProb :nextStateMapProbabilities)
		{	
			ACASXState nextState=nextStateMapProb.getKey();
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
	
	
	public Map<Integer,Double> getTransitionStatesAndProbs(int stateOrder, int actionCode)
	{
		ACASXState state = states[stateOrder];
		Map<Integer, Double> TransitionStatesAndProbs = new LinkedHashMap<Integer,Double>();
		
		if(state.getT()==0)//leaving state
		{ 
			TransitionStatesAndProbs.put(stateOrder, 1.0);		
			return TransitionStatesAndProbs;
		}
				
		double targetV=ACASXUtils.getActionV(actionCode);
		double accel=ACASXUtils.getActionA(actionCode);
		ArrayList<AbstractMap.SimpleEntry<Integer, Double>> nextStateMapProbabilities = new ArrayList<>();
		
		if( (accel>0 && targetV>state.getoVy() && state.getoVy()<UPPER_VY)
				|| (accel<0 && targetV<state.getoVy() && state.getoVy()>-UPPER_VY))
		{// own aircraft follows a RA other than COC			
			
			for(Tuple<Double, Double, Double> sigmaPoint : sigmaPointsA)
			{
				double oAy=accel;
				double iAy=sigmaPoint.y;
				double sigmaP=sigmaPoint.z;
				
				double hP= state.getH()+ (state.getiVy()-state.getoVy()) + 0.5*(iAy-oAy);
				double oVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getoVy()+oAy));
				double iVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getiVy()+iAy));
				int tP=state.getT()-1;
				int raP=actionCode;
				
				int hIdxL = (int)Math.floor(hP/hRes);
				int oVyIdxL = (int)Math.floor(oVyP/oVRes);
				int iVyIdxL = (int)Math.floor(iVyP/iVRes);
				for(int i=0;i<=1;i++)
				{
					int hIdx = (i==0? hIdxL : hIdxL+1);
					int hIdxP= hIdx< -nh? -nh: (hIdx>nh? nh : hIdx);			
					for(int j=0;j<=1;j++)
					{
						int oVyIdx = (j==0? oVyIdxL : oVyIdxL+1);
						int oVyIdxP= oVyIdx<-noVy? -noVy: (oVyIdx>noVy? noVy : oVyIdx);
						for(int k=0;k<=1;k++)
						{
							int iVyIdx = (k==0? iVyIdxL : iVyIdxL+1);
							int iVyIdxP= iVyIdx<-niVy? -niVy: (iVyIdx>niVy? niVy : iVyIdx);
							
							ACASXState nextState= new ACASXState(tP, hIdxP, oVyIdxP, iVyIdxP, raP);
							double probability= sigmaP*(1-Math.abs(hIdx-hP/hRes))*(1-Math.abs(oVyIdx-oVyP/oVRes))*(1-Math.abs(iVyIdx-iVyP/iVRes));
							nextStateMapProbabilities.add(new SimpleEntry<Integer, Double>(nextState.getOrder(),probability) );
						}
					}
				}

			}			
			
		}
		else
		{				
			for(Tuple<Double, Double, Double> sigmaPoint : sigmaPointsB)
			{
				double oAy=sigmaPoint.x;
				double iAy=sigmaPoint.y;
				double sigmaP=sigmaPoint.z;
				
				double hP= state.getH()+ (state.getiVy()-state.getoVy()) + 0.5*(iAy-oAy);
				double oVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getoVy()+oAy));
				double iVyP= Math.max(-UPPER_VY, Math.min(UPPER_VY, state.getiVy()+iAy));
				int tP=state.getT()-1;
				int raP=actionCode;

				int hIdxL = (int)Math.floor(hP/hRes);
				int oVyIdxL = (int)Math.floor(oVyP/oVRes);
				int iVyIdxL = (int)Math.floor(iVyP/iVRes);
				for(int i=0;i<=1;i++)
				{
					int hIdx = (i==0? hIdxL : hIdxL+1);
					int hIdxP= hIdx< -nh? -nh: (hIdx>nh? nh : hIdx);			
					for(int j=0;j<=1;j++)
					{
						int oVyIdx = (j==0? oVyIdxL : oVyIdxL+1);
						int oVyIdxP= oVyIdx<-noVy? -noVy: (oVyIdx>noVy? noVy : oVyIdx);
						for(int k=0;k<=1;k++)
						{
							int iVyIdx = (k==0? iVyIdxL : iVyIdxL+1);
							int iVyIdxP= iVyIdx<-niVy? -niVy: (iVyIdx>niVy? niVy : iVyIdx);
							
							ACASXState nextState= new ACASXState(hIdxP, oVyIdxP, iVyIdxP, tP, raP);
							double probability= sigmaP*(1-Math.abs(hIdx-hP/hRes))*(1-Math.abs(oVyIdx-oVyP/oVRes))*(1-Math.abs(iVyIdx-iVyP/iVRes));
							nextStateMapProbabilities.add(new SimpleEntry<Integer, Double>(nextState.getOrder(),probability) );
						}
					}
				}	
				
			}			
			
		}

		for(AbstractMap.SimpleEntry<Integer, Double> nextStateMapProb :nextStateMapProbabilities)
		{	
			Integer nextStateOrder = nextStateMapProb.getKey();
			if(TransitionStatesAndProbs.containsKey(nextStateOrder))
			{
				TransitionStatesAndProbs.put(nextStateOrder, TransitionStatesAndProbs.get(nextStateOrder)+nextStateMapProb.getValue());
			}
			else
			{
				TransitionStatesAndProbs.put(nextStateOrder, nextStateMapProb.getValue());
			}		
			
		}
		
		return TransitionStatesAndProbs;
	}
	

	
	
	public double reward(ACASXState state,int actionCode)
	{
		if(actionCode==-1)//"Loop"
		{
//			System.out.println(state+"**********");
			if(Math.abs(state.getH())<100)
			{//NMAC
//				System.out.println(state+"**********");
				return -10000;
			}
			return 0;
		}
//		if(Math.abs(state.getH())<100 && state.getT()==1)
//		{//NMAC
//			return -10000;
//		}
	
		if(actionCode==1)
		{
			if(state.getoVy()>0)
			{
				return -50;
			}
			else if(state.getoVy()<0)
			{
				return -100;
			}
			
		}
		if(actionCode==2)
		{
			if(state.getoVy()>0)
			{
				return -100;
			}
			else if(state.getoVy()<0)
			{
				return -50;
			}
		}
				
		if(actionCode==0)//"COC"
		{//clear of conflict
			return 100;
		}
		
		if( (state.getRa()==1||state.getRa()==3)&& actionCode==5
				|| (state.getRa()==2||state.getRa()==4)&& actionCode==6)
		{//strengthening
			return -500;
		}
		
		if( (state.getRa()==1 || state.getRa()==3 || state.getRa()==5)&& actionCode==4 
				|| (state.getRa()==2 || state.getRa()==4 || state.getRa()==6)&& actionCode==3 )
		{//reversal
			return -1000;
		}
		
		return 0;
	}
	
	public double reward(int stateOrder,int actionCode)
	{
		ACASXState s=states[stateOrder];
		return reward(s,actionCode);		
	}
}

class Tuple<X, Y, Z> 
{ 
	  public final X x; 
	  public final Y y; 
	  public final Z z; 
	  public Tuple(X x, Y y, Z z) 
	  { 
	    this.x = x; 
	    this.y = y; 
	    this.z = z;
	  } 
	} 
