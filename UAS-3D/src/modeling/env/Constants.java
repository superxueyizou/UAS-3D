package modeling.env;
/**
 *
 * @author Xueyi Zou
 */
public interface Constants
{
	//Entity Types
	public static enum EntityType
	{	
		TOTHER,//a placeholder - save 0 for entities which aren't mentioned elsewhere	
		TUAS,//the type constant of a uas
		TWAYPOINT,//the type constant of a waypoint
		TObserver,//the type constant of a observer
				
	}

	//Accident types
	public static enum AccidentType
	{
		CLASHWITHOBSTACLE,
		CLASHWITHWALL,
		CLASHWITHOTHERUAS;
		
	}

}
