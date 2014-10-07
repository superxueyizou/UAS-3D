package configuration;

public class IntruderConfig 
{
	public String intruderType = "HeadOn";
	public double intruderOffsetX= 10000;
	public double intruderOffsetY= 200;
	public double intruderOffsetZ= 0;
	public double intruderVx =250;
	public double intruderVy =0;
	public double intruderVz =0;
	public double intruderStdDevX =3;
	public double intruderStdDevY =3;
	public double intruderStdDevZ =3;
	public double intruderMaxSpeed =304;
	public double intruderMinSpeed =169;
	public double intruderMaxClimb = 58;
	public double intruderMaxDescent = 67;
	public double intruderMaxAcceleration = 10;
	public double intruderMaxDeceleration = 10;
	public double intruderMaxTurning = Math.toRadians(2.5);
	public int    intruderSensorSelection = 0B10000; 
	public String intruderAutoPilotAlgorithmSelection = "WhiteNoise";
	public String intruderCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm"; //"ACASXAvoidanceAlgorithm", "ACASX3DAvoidanceAlgorithm", "None"
	public String intruderSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm"; //"NASAChorusAlgorithm","None"
}
