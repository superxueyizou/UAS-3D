/**
 * 
 */
package saa.collsionavoidance;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.TreeMap;

import modeling.SAAModel;
import modeling.uas.UAS;
import saa.collsionavoidance.mdpLite.ACASXMDP;
import saa.collsionavoidance.mdpLite.ACASXState;
import saa.collsionavoidance.mdpLite.ACASXUtils;
import sim.engine.SimState;
import sim.util.Double2D;


/**
 * @author Xueyi
 *
 */
public class ACASX extends CollisionAvoidanceAlgorithm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SAAModel state; 
	private UAS hostUAS;
	private UAS intruder=null;
	private LookupTable lookupTable;
	private int ra=0;//"COC"
	
	public ACASX(SimState simstate, UAS uas) 
	{
		state = (SAAModel) simstate;
		hostUAS = uas;	
	}
	
	
	/******************************************************************************************************************************************/
	
	public void init()
	{
		if(state.uasBag.size()!=2)
		{
			System.err.println("ACASX.java: only two UAVs are allowed in this setting");
		}
		for(int i=0; i<state.uasBag.size(); i++)
		{
			UAS uas= (UAS)state.uasBag.get(i);
			if(uas == hostUAS)
			{
				continue;
			}
			else
			{
				intruder= uas;
			}			
		
		}
	
		lookupTable=LookupTable.getInstance();
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
		double h=(intruder.getLocation().y-hostUAS.getLocation().y);
		double oVy=hostUAS.getVelocity().y;
		double iVy=intruder.getVelocity().y;
		Double2D vctDistance = new Double2D(hostUAS.getLocation().x-intruder.getLocation().x, hostUAS.getLocation().z-intruder.getLocation().z);
		Double2D vctVelocity = new Double2D(intruder.getVelocity().x-hostUAS.getVelocity().x, intruder.getVelocity().z-hostUAS.getVelocity().z);
		double timeToGo=vctDistance.lengthSq()/vctDistance.dot(vctVelocity);
		int t=(int) Math.ceil(timeToGo);
		state.information=String.format( "(%.1f, %.1f, %.1f, %.1f, %d)",h,oVy,iVy,timeToGo,ra);
		
		double hRes=ACASXMDP.hRes;
		double oVRes=ACASXMDP.oVRes;
		double iVRes=ACASXMDP.iVRes;
		if(Math.abs(h)<ACASXMDP.UPPER_H && t<=ACASXMDP.nt && t>0)
		{
			if(Math.abs(oVy)<=ACASXMDP.UPPER_VY && Math.abs(iVy)<=ACASXMDP.UPPER_VY )
			{
				Map<Integer, Double> qValuesMap = new TreeMap<>();
				ArrayList<AbstractMap.SimpleEntry<Integer, Double>> actionMapValues = new ArrayList<AbstractMap.SimpleEntry<Integer, Double>>();

				int hIdxL = (int)Math.floor(h/hRes);
				int oVyIdxL = (int)Math.floor(oVy/oVRes);
				int iVyIdxL = (int)Math.floor(iVy/iVRes);
				for(int i=0;i<=1;i++)
				{
					int hIdx = (i==0? hIdxL : hIdxL+1);
					int hIdxP= hIdx< -ACASXMDP.nh? -ACASXMDP.nh: (hIdx>ACASXMDP.nh? ACASXMDP.nh : hIdx);			
					for(int j=0;j<=1;j++)
					{
						int oVyIdx = (j==0? oVyIdxL : oVyIdxL+1);
						int oVyIdxP= oVyIdx<-ACASXMDP.noVy? -ACASXMDP.noVy: (oVyIdx>ACASXMDP.noVy? ACASXMDP.noVy : oVyIdx);
						for(int k=0;k<=1;k++)
						{
							int iVyIdx = (k==0? iVyIdxL : iVyIdxL+1);
							int iVyIdxP= iVyIdx<-ACASXMDP.niVy? -ACASXMDP.niVy: (iVyIdx>ACASXMDP.niVy? ACASXMDP.niVy : iVyIdx);
							
							ACASXState approxState= new ACASXState(t,hIdxP, oVyIdxP, iVyIdxP,ra);
							double probability= (1-Math.abs(hIdx-h/hRes))*(1-Math.abs(oVyIdx-oVy/oVRes))*(1-Math.abs(iVyIdx-iVy/iVRes));

							int index=-999;
							int numActions=-999;
							index=lookupTable.indexArr.get(approxState.getOrder());
							numActions = lookupTable.indexArr.get(approxState.getOrder()+1)-index;
								
							for (int n=0;n<numActions;n++) 
							{
								double qValue= lookupTable.costArr.get(index+n);
								int actionCode= lookupTable.actionArr.get(index+n);							
								actionMapValues.add(new SimpleEntry<Integer, Double>(actionCode,probability*qValue) );
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
				
				double maxQValue=Double.NEGATIVE_INFINITY;
				int bestActionCode=-999;
				
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
			else
			{
				ra=0;
				
				hostUAS.getAp().setActionCode(0);	
			}				
		
		}
		else
		{
			hostUAS.getAp().setActionCode(-999);
		}			
		
	}
	
	public double getActionV(int actionCode)
	{
		return ACASXUtils.getActionV(actionCode);
	
	}
	
	public double getActionA(int actionCode)
	{
		return ACASXUtils.getActionA(actionCode);
	
	}	
	
}
