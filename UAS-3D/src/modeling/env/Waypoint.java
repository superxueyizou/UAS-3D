package modeling.env;

/**
 *
 * @author rl576
 */
public class Waypoint extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Waypoint nextPoint; //the id of the point to go to after this waypoint
	private int action=-1;
	
	/** Constructor for Waypoint
	 * 
	 * @param ID the id of the waypoint
	 * @param next the id of the point that should be travelled to after this waypoint is reached
	 */
	public Waypoint(int ID, Waypoint next)
	{
		super(ID, Constants.EntityType.TWAYPOINT);
		nextPoint = next;
	}
	
	/**
	 * A method which returns the id of the point to go to after this waypoint has
	 * been reached
	 * 
	 * @return the id of the next point for the uas to travel to
	 */
	public Waypoint getNextWaypoint() {return nextPoint;}
	public void setNextWaypoint(Waypoint next) {nextPoint = next;}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}
}
