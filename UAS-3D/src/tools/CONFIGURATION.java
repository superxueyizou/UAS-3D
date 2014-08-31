/**
 * 
 */
package tools;


/**
 * @author Xueyi
 *
 */
public class CONFIGURATION {

	/**
	 * 
	 */
	public CONFIGURATION() 
	{
		
	}
	public static double worldX = 14000; 
	public static double worldY = 10000; 
	public static double worldZ = 10000; 

	public static boolean collisionAvoidanceEnabler=true;
	public static boolean selfSeparationEnabler=false;
	public static boolean accidentDetectorEnabler=true;
	public static boolean sensorNoiseEnabler=true;
	public static boolean sensorValueUncertainty=true;
	
	
	public static double selfVx =250;
	public static double selfVy =0;
	public static double selfVz =0;
	public static double selfStdDevX =3;
	public static double selfStdDevY =3;
	public static double selfStdDevZ =3;
	public static double selfMaxSpeed =304;
	public static double selfMinSpeed =169;
	public static double selfPrefSpeed = 250;
	public static double selfMaxClimb = 58;
	public static double selfMaxDescent = 67;
	public static double selfMaxAcceleration = 10;
	public static double selfMaxDeceleration = 10;
	public static double selfMaxTurning = Math.toRadians(2.5);
	public static double selfViewingRange =20000;
	public static double selfViewingAngle = Math.toRadians(60);
	public static int selfSensorSelection = 0B10000; 
	public static String selfAutoPilotAlgorithmSelection = "WhiteNoise";
	public static String selfCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm"; //"ACASXAvoidanceAlgorithm","ACASX3DAvoidanceAlgorithm", "None"
	public static String selfSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm"; //"NASAChorusAlgorithm","None"

	
	public static double headOnSelected = 1;
	public static int headOnIntruders=1;
	public static double headOnOffsetY= 0;
	public static double headOnOffsetZ= 0;
	public static double headOnVx =250;
	public static double headOnVy =0;
	public static double headOnVz =0;
	public static double headOnStdDevX =3;
	public static double headOnStdDevY =3;
	public static double headOnStdDevZ =3;
	public static double headOnMaxSpeed =304;
	public static double headOnMinSpeed =169;
	public static double headOnPrefSpeed = 250;
	public static double headOnMaxClimb = 58;
	public static double headOnMaxDescent = 67;
	public static double headOnMaxAcceleration = 10;
	public static double headOnMaxDeceleration = 10;
	public static double headOnMaxTurning = Math.toRadians(2.5);
	public static double headOnViewingRange =20000;
	public static double headOnViewingAngle =  Math.toRadians(60);
	public static int headOnSensorSelection = 0B10000; 
	public static String headOnAutoPilotAlgorithmSelection = "WhiteNoise";
	public static String headOnCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm"; //"ACASXAvoidanceAlgorithm", "ACASX3DAvoidanceAlgorithm", "None"
	public static String headOnSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm"; //"NASAChorusAlgorithm","None"
		
	
}
