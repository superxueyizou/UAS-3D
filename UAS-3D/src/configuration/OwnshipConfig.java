package configuration;

public class OwnshipConfig 
{	
	public double ownshipVy =0;
	public double ownshipGs =250;
	public double ownshipBearing =0;
	public double ownshipStdDevX =3;
	public double ownshipStdDevY =3;
	public double ownshipStdDevZ =3;
	public double ownshipMaxSpeed =304;
	public double ownshipMinSpeed =169;
	public double ownshipMaxClimb = 58;
	public double ownshipMaxDescent = 67;
	public double ownshipMaxAcceleration = 10;
	public double ownshipMaxDeceleration = 10;
	public double ownshipMaxTurning = Math.toRadians(2.5);
	public int    ownshipSensorSelection = 0B10000; 
	public String ownshipAutoPilotAlgorithmSelection = "WhiteNoise";
	public String ownshipCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm"; //"ACASX2DAvoidanceAlgorithm","ACASX3DAvoidanceAlgorithm", "None"
	public String ownshipSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm"; //"NASAChorusAlgorithm","None"
}
