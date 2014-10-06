package modeling.uas;

import modeling.SAAModel;
import modeling.env.Constants;
import modeling.env.Entity;
import modeling.env.Waypoint;
import saa.AutoPilot;
import saa.collsionavoidance.CollisionAvoidanceAlgorithm;
import saa.selfseparation.SelfSeparationAlgorithm;
import saa.sense.SensorSet;
import sim.engine.*;
import sim.util.*;

/**
 *
 * @author Robert Lee
 */
public class UAS extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String alias;
	
	//parameters for subsystems	
	private SensorSet sensorSet;
	private CollisionAvoidanceAlgorithm caa;
	private SelfSeparationAlgorithm ssa;
	private AutoPilot ap;

	//parameters for UAS movement	
	private Double3D oldLocation;
	private Double3D location;
	private UASVelocity oldUASVelocity;		
	private UASVelocity UASVelocity;	
	
	private Bag achievedWaypoints;

	private UASPerformance uasPerformance;//the set performance for the uas;
	
	//parameters for UAS's sensing capability. They are the result of the sensor subsystem.
	private SenseParas senseParas;

	//parameters for navigation
	private Waypoint nextWp;
	private Waypoint apWp = null;//for auto-pilot

	
	
/*************************************************************************************************/
	//parameters for recording information about simulation
	private double tempDistanceToDanger = Double.MAX_VALUE; //records the closest distance to danger in each step
	private double minDistanceToDanger = Double.MAX_VALUE; //records the closest distance to danger experienced by the uas
	
	private double tempOscillation = 0; //records the oscillation in each step: area
	private double Oscillation = 0; //records the oscillation in a simulation: area

	private double numOscillation = 0; //records the oscillation times in a simulation
	
/*************************************************************************************************/

	public boolean isActive;	

	private SAAModel state;	


	public UAS(int idNo, Double3D location, UASVelocity uasVelocity, UASPerformance uasPerformance, SenseParas senseParas)
	{
		super(idNo, Constants.EntityType.TUAS);

		this.location=location;
		this.uasPerformance = uasPerformance;
		this.UASVelocity = uasVelocity; 
		this.senseParas = senseParas;
		
		this.oldUASVelocity= uasVelocity;
		this.oldLocation= location;
		
		nextWp=null;
	
		achievedWaypoints = new Bag();	

		this.isActive=true;

	}
	
	public void init(SensorSet sensorSet, AutoPilot ap, CollisionAvoidanceAlgorithm caa, SelfSeparationAlgorithm ssa)
	{
		this.sensorSet=sensorSet;
		this.ap = ap;
		this.caa = caa;
		this.ssa = ssa;
		
	}
	

	@Override
	public void step(SimState simState)
	{
		state = (SAAModel) simState;		
		if(this.isActive == true)
		{				
			if(apWp != null)
			{
				nextWp=apWp;
				state.environment3D.setObjectLocation(nextWp, nextWp.getLocation());
		
				this.setOldLocation(this.location);
				this.setLocation(nextWp.getLocation());
				state.environment3D.setObjectLocation(this, this.location);
				achievedWaypoints.add(nextWp);
				
			}			
			else
			{
				System.out.println("approaching the destination (impossible)!");
			}	
			
		}		
		
		if(state!=null)
		{
			dealWithTermination();
		}
		
    }

//**************************************************************************
	
	public SensorSet getSensorSet() {
		return sensorSet;
	}

	public void setSensorSet(SensorSet sensorSet) {
		this.sensorSet = sensorSet;
	}
	
	public AutoPilot getAp() {
		return ap;
	}

	public void setAp(AutoPilot ap) {
		this.ap = ap;
	}
	
	public CollisionAvoidanceAlgorithm getCaa() {
		return caa;
	}

	public void setCaa(CollisionAvoidanceAlgorithm aa) {
		this.caa = aa;
	}
	
	
	public SelfSeparationAlgorithm getSsa() {
		return ssa;
	}

	public void setSsa(SelfSeparationAlgorithm ss) {
		this.ssa = ss;
	}

	public UASPerformance getStats() {
		return uasPerformance;
	}


	public void setStats(UASPerformance stats) {
		this.uasPerformance = stats;
	}


	
	public double getViewingRange() {
		return this.senseParas.getViewingRange();
	}

	public void setViewingRange(double viewingRange) {
		this.senseParas.setViewingRange(viewingRange);
	}
	
	public double getViewingAngle() {
		return this.senseParas.getViewingAngle();
	}

	public void setViewingAngle(double viewingAngle) {
		this.senseParas.setViewingAngle(viewingAngle);
	}
		
	public Double3D getOldVelocity() {
		return oldUASVelocity.getVelocity();
	}
	public void setOldVelocity(Double3D velocity) {
		oldUASVelocity.setVelocity(velocity);
	}

	public Double3D getVelocity() {
		return UASVelocity.getVelocity();
	}
	public void setVelocity(Double3D velocity) {
		UASVelocity.setVelocity(velocity);
	}

	public Double3D getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(Double3D oldLocation) {
		this.oldLocation = oldLocation;
	}
	
	public Double3D getLocation() {
		return location;
	}

	public void setLocation(Double3D location) {
		this.location = location;
	}

	public Bag getAchievedWaypoints() {
		return achievedWaypoints;
	}


	public UASPerformance getUasPerformance() {
		return uasPerformance;
	}

	public void setUasPerformance(UASPerformance performance) {
		this.uasPerformance = performance;
	}
	
	public double getTempDistanceToDanger() {
		return tempDistanceToDanger;
	}

	public void setTempDistanceToDanger(double tempDistanceToDanger) {
		this.tempDistanceToDanger = tempDistanceToDanger;
	}

	public double getMinDistanceToDanger() {
		return minDistanceToDanger;
	}

	public void setMinDistanceToDanger(double distanceToDanger) {
		this.minDistanceToDanger = distanceToDanger;
	}
	
	public double getTempOscillation() {
		return tempOscillation;
	}

	public void setTempOscillation(double tempOscillation) {
		this.tempOscillation = tempOscillation;
	}

	public double getOscillation() {
		return Oscillation;
	}

	public void setOscillation(double oscillation) {
		Oscillation = oscillation;
	}

	public double getNumOscillation() {
		return numOscillation;
	}

	public void setNumOscillation(double oscillationNo) {
		numOscillation = oscillationNo;
	}
	
	public Waypoint getApWp() {
		return apWp;
	}

	public void setApWp(Waypoint apWp) {
		this.apWp = apWp;
	}

	public SAAModel getState() {
		return state;
	}

	public void setState(SAAModel state) {
		this.state = state;
	}


    public void dealWithTermination()
	{
    	int noActiveAgents =0;
    	for(Object o: state.uasBag)
    	{
    		if(((UAS)o).isActive)
    		{
    			noActiveAgents++;
    		}
    		
    	}
    	
		if(noActiveAgents < 1)
		{
			state.schedule.clear();
			state.kill();
		}
	 }

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
