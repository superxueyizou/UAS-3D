/**
 * 
 */
package saa.collsionavoidance;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import modeling.SAAModel;
import modeling.uas.UAS;
import saa.collsionavoidance.acasx3d.ACASX3DCState;
import saa.collsionavoidance.acasx3d.ACASX3DDTMC;
import saa.collsionavoidance.acasx3d.ACASX3DMDP;
import saa.collsionavoidance.acasx3d.ACASX3DUState;
import saa.collsionavoidance.acasx3d.ACASX3DUtils;
import saa.collsionavoidance.acasx3d.MDPValueIteration;
import sim.engine.SimState;
import sim.util.Double2D;
import configuration.Configuration;

/**
 * @author Xueyi
 *
 */
public class ACASX3D extends CollisionAvoidanceAlgorithm
{
	private static final long serialVersionUID = 1L;
	private static final String entryTimeDistributionMethod="DTMC";//DTMC, MonteCarlo, SimplePointEstimate
	
	private SAAModel state; 
	private UAS hostUAS;
	private ArrayList<UAS> intruders;
	private LookupTable3D lookupTable3D;
	private int ra=0;//"COC"
	
	public ACASX3D(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;	
		intruders=new ArrayList<>();
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		int numUAS=state.uasBag.size();
		for(int i=0; i<numUAS; i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				continue;
			}
			else
			{
				intruders.add(uas);
			}			
		
		}
		if(Configuration.getInstance().globalConfig.collisionAvoidanceEnabler)
		{
			lookupTable3D=LookupTable3D.getInstance();
		}			
	}	
	
	
	
	@Override
	public void step(SimState simState)
	{
		if(hostUAS.isActive == true)
		{	
			execute();			
		}		 
	}
	
	
	public void execute()
	{
		//max-min
		Map<Integer, Double> qValuesMap = new TreeMap<>();
		for (UAS intruder: intruders)
		{
			Double2D vctDistance = new Double2D(intruder.getLocation().x-hostUAS.getLocation().x, intruder.getLocation().z-hostUAS.getLocation().z);
			double r=vctDistance.length();
			double h=(intruder.getLocation().y-hostUAS.getLocation().y);
			
			if(Math.abs(h)<=ACASX3DMDP.UPPER_H && r<=ACASX3DDTMC.UPPER_R)
			{
				Map<Integer, Double> entryTimeDistribution;
				switch (entryTimeDistributionMethod)
				{
				case "DTMC":
					entryTimeDistribution= calculateEntryTimeDistributionDTMC(intruder);
					break;
				case "MonteCarlo":
					entryTimeDistribution= calculateEntryTimeDistributionMC(intruder);
					break;
				case "SimplePointEstimate":
					entryTimeDistribution= calculateEntryTimeDistributionSimple(intruder);
					break;
				default:
					entryTimeDistribution= calculateEntryTimeDistributionDTMC(intruder);
					break;
				}
//				for(Integer t: entryTimeDistribution.keySet())
//				{
//					System.out.println(t+"  , "+entryTimeDistribution.get(t));
//				}
				
				Map<Integer, Double> qValuesMap2 = calculateQValuesMap(intruder, entryTimeDistribution);
				for (Integer action : qValuesMap2.keySet()) 
				{
					if(qValuesMap.containsKey(action))
					{
						double a=qValuesMap.get(action);
						double b=qValuesMap2.get(action);
						qValuesMap.put(action, Math.min(a, b));
//						qValuesMap.put(action, a+b);
					}
					else
					{
						qValuesMap.put(action, qValuesMap2.get(action));						
					}
				}				
			}
			else
			{
				continue;				
			}			
				
		}
		
		double maxQValue=Double.NEGATIVE_INFINITY;
		int bestActionCode=0;
		Set<Entry<Integer,Double>> entrySet = qValuesMap.entrySet();
		for (Entry<Integer,Double> entry : entrySet) 
		{
			double value=entry.getValue();
			if(value-maxQValue>=0.0001)
			{
				maxQValue=value;
				bestActionCode=entry.getKey();
			}
		}	
		ra=bestActionCode;	
		hostUAS.getAp().setActionCode(bestActionCode);
	}

	
	private Map<Integer, Double> calculateEntryTimeDistributionDTMC(UAS intruder)
	{
		Double2D vctDistance = new Double2D(intruder.getLocation().x-hostUAS.getLocation().x, intruder.getLocation().z-hostUAS.getLocation().z);
		Double2D vctVelocity = new Double2D(intruder.getVelocity().x-hostUAS.getVelocity().x, intruder.getVelocity().z-hostUAS.getVelocity().z);
		double r=vctDistance.length();
		double rv=vctVelocity.length();
		double alpha=vctVelocity.angle()-vctDistance.angle();
		if(alpha> Math.PI)
 	   	{
			alpha= -2*Math.PI +alpha; 
 	   	}
		if(alpha<-Math.PI)
 	   	{
			alpha=2*Math.PI+alpha; 
 	   	}
		double theta = Math.toDegrees(alpha);
		
		double rRes=ACASX3DDTMC.rRes;
		double rvRes=ACASX3DDTMC.rvRes;
		double thetaRes=ACASX3DDTMC.thetaRes;
		
		ArrayList<AbstractMap.SimpleEntry<Integer, Double>> entryTimeMapProbs = new ArrayList<AbstractMap.SimpleEntry<Integer, Double>>();
		Map<Integer, Double> entryTimeDistribution = new TreeMap<>();// must be a sorted map

		assert (r<=ACASX3DDTMC.UPPER_R);
		assert (rv<=ACASX3DDTMC.UPPER_RV);
		assert (alpha>=-180 && alpha<=180);
	
		int rIdxL = (int)Math.floor(r/rRes);
		int rvIdxL = (int)Math.floor(rv/rvRes);
		int thetaIdxL = (int)Math.floor(theta/thetaRes);
		for(int i=0;i<=1;i++)
		{
			int rIdx = (i==0? rIdxL : rIdxL+1);
			int rIdxP= rIdx< 0? 0: (rIdx>ACASX3DDTMC.nr? ACASX3DDTMC.nr : rIdx);			
			for(int j=0;j<=1;j++)
			{
				int rvIdx = (j==0? rvIdxL : rvIdxL+1);
				int rvIdxP= rvIdx<0? 0: (rvIdx>ACASX3DDTMC.nrv? ACASX3DDTMC.nrv : rvIdx);
				for(int k=0;k<=1;k++)
				{
					int thetaIdx = (k==0? thetaIdxL : thetaIdxL+1);
					int thetaIdxP= thetaIdx<-ACASX3DDTMC.ntheta? -ACASX3DDTMC.ntheta: (thetaIdx>ACASX3DDTMC.ntheta? ACASX3DDTMC.ntheta : thetaIdx);
					
					ACASX3DUState approxUState= new ACASX3DUState(rIdxP, rvIdxP, thetaIdxP);
					int approxUStateOrder = approxUState.getOrder();
					double probability= (1-Math.abs(rIdx-r/rRes))*(1-Math.abs(rvIdx-rv/rvRes))*(1-Math.abs(thetaIdx-theta/thetaRes));
					for(int t=0;t<=MDPValueIteration.T;t++)
					{
						entryTimeMapProbs.add(new SimpleEntry<Integer, Double>(t,probability*lookupTable3D.entryTimeDistributionArr.get((t*lookupTable3D.numUStates)+ approxUStateOrder)) );
					}
					
				}
			}
		}
		double entryTimeLessThanTProb=0;
		for(AbstractMap.SimpleEntry<Integer, Double> entryTime_prob :entryTimeMapProbs)
		{			
			if(entryTimeDistribution.containsKey(entryTime_prob.getKey()))
			{
				entryTimeDistribution.put(entryTime_prob.getKey(), entryTimeDistribution.get(entryTime_prob.getKey())+entryTime_prob.getValue());
			}
			else
			{
				entryTimeDistribution.put(entryTime_prob.getKey(), entryTime_prob.getValue());
			}
			entryTimeLessThanTProb+=entryTime_prob.getValue();
		}
		entryTimeDistribution.put(MDPValueIteration.T+1, 1-entryTimeLessThanTProb);
		return entryTimeDistribution;
	}
	
	private Map<Integer, Double> calculateEntryTimeDistributionMC(UAS intruder)
	{
		return null;
	}
	
	private Map<Integer, Double> calculateEntryTimeDistributionSimple(UAS intruder)
	{
		Map<Integer, Double> entryTimeDistribution = new TreeMap<>();// must be a sorted map
		
		Double2D vctDistance = new Double2D(intruder.getLocation().x-hostUAS.getLocation().x, intruder.getLocation().z-hostUAS.getLocation().z);
		Double2D vctVelocity = new Double2D(intruder.getVelocity().x-hostUAS.getVelocity().x, intruder.getVelocity().z-hostUAS.getVelocity().z);
		double timeToGo=-vctDistance.lengthSq()/vctDistance.dot(vctVelocity);
		if(timeToGo<0 || timeToGo>MDPValueIteration.T)
		{
			entryTimeDistribution.put(0, 0.0);
		}
		else 
		{
			int tL=(int) Math.floor(timeToGo);
			double tLProb=1-Math.abs(timeToGo-tL);
			int tU=tL+1;
			double tUProb=1-Math.abs(timeToGo-tU);
			
			entryTimeDistribution.put(tL, tLProb);
			entryTimeDistribution.put(tU, tUProb);
		}
		
		return entryTimeDistribution;
	}
	
	private Map<Integer, Double> calculateQValuesMap(UAS intruder, Map<Integer, Double> entryTimeDistribution)
	{
		double h=(intruder.getLocation().y-hostUAS.getLocation().y);
		double oVy=hostUAS.getVelocity().y;
		double iVy=intruder.getVelocity().y;
//		state.information=String.format( "(%.1f, %.1f, %.1f, %d)",h,oVy,iVy,ra);
		
		double hRes=ACASX3DMDP.hRes;
		double oVRes=ACASX3DMDP.oVRes;
		double iVRes=ACASX3DMDP.iVRes;
		
		assert (Math.abs(h)<=ACASX3DMDP.UPPER_H);
		assert (Math.abs(oVy)<=ACASX3DMDP.UPPER_VY);
		assert (Math.abs(iVy)<=ACASX3DMDP.UPPER_VY);
		assert (ra>=0);
		
		Map<Integer, Double> qValuesMap = new TreeMap<>();
		ArrayList<AbstractMap.SimpleEntry<Integer, Double>> actionMapValues = new ArrayList<AbstractMap.SimpleEntry<Integer, Double>>();

		int hIdxL = (int)Math.floor(h/hRes);
		int oVyIdxL = (int)Math.floor(oVy/oVRes);
		int iVyIdxL = (int)Math.floor(iVy/iVRes);
		for(int i=0;i<=1;i++)
		{
			int hIdx = (i==0? hIdxL : hIdxL+1);
			int hIdxP= hIdx< -ACASX3DMDP.nh? -ACASX3DMDP.nh: (hIdx>ACASX3DMDP.nh? ACASX3DMDP.nh : hIdx);			
			for(int j=0;j<=1;j++)
			{
				int oVyIdx = (j==0? oVyIdxL : oVyIdxL+1);
				int oVyIdxP= oVyIdx<-ACASX3DMDP.noVy? -ACASX3DMDP.noVy: (oVyIdx>ACASX3DMDP.noVy? ACASX3DMDP.noVy : oVyIdx);
				for(int k=0;k<=1;k++)
				{
					int iVyIdx = (k==0? iVyIdxL : iVyIdxL+1);
					int iVyIdxP= iVyIdx<-ACASX3DMDP.niVy? -ACASX3DMDP.niVy: (iVyIdx>ACASX3DMDP.niVy? ACASX3DMDP.niVy : iVyIdx);
					
					ACASX3DCState approxCState= new ACASX3DCState(hIdxP, oVyIdxP, iVyIdxP, ra);
					int approxCStateOrder = approxCState.getOrder();
					double probability= (1-Math.abs(hIdx-h/hRes))*(1-Math.abs(oVyIdx-oVy/oVRes))*(1-Math.abs(iVyIdx-iVy/iVRes));

					for(Entry<Integer, Double> entryTime_prob :entryTimeDistribution.entrySet())
					{
						int t=entryTime_prob.getKey();
						double entryTimeProb= entryTime_prob.getValue();					

						int index=0, numActions=0;
						try
						{
							index =lookupTable3D.indexArr.get((t*lookupTable3D.numCStates)+ approxCStateOrder);
							numActions = lookupTable3D.indexArr.get((t*lookupTable3D.numCStates)+approxCStateOrder+1)-index;	
						}
						catch(ArrayIndexOutOfBoundsException e)
						{
							System.out.println((t*lookupTable3D.numCStates)+"    "+ approxCState+"     "+approxCStateOrder);
							System.exit(-1);
						}
														
						for (int n=0;n<numActions;n++) 
						{
							double qValue= lookupTable3D.costArr.get(index+n);
							int actionCode= lookupTable3D.actionArr.get(index+n);							
							actionMapValues.add(new SimpleEntry<Integer, Double>(actionCode,probability*entryTimeProb*qValue) );
						}			
					}														
				}
			}
		}
					
		for(AbstractMap.SimpleEntry<Integer, Double> action_value :actionMapValues)
		{
			if(qValuesMap.containsKey(action_value.getKey()))
			{
				qValuesMap.put(action_value.getKey(), qValuesMap.get(action_value.getKey())+action_value.getValue());
			}
			else
			{
				qValuesMap.put(action_value.getKey(), action_value.getValue());
			}
		}
		
		return qValuesMap;
	}
	
	public double getActionV(int actionCode)
	{
		return ACASX3DUtils.getActionV(actionCode);
	
	}
	
	public double getActionA(int actionCode)
	{
		return ACASX3DUtils.getActionA(actionCode);
	
	}
	
}
