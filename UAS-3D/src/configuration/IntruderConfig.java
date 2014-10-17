package configuration;

public class IntruderConfig 
{
	public double intruderOffsetY= 200;
	public double intruderR= 10000;
	public double intruderTheta= 0;
	public double intruderVy =0;
	public double intruderGs =250;
	public double intruderBearing =180;
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
	public String intruderCollisionAvoidanceAlgorithmSelection = "ACASX3DAvoidanceAlgorithm"; //"ACASX2DAvoidanceAlgorithm", "ACASX3DAvoidanceAlgorithm", "None"
	public String intruderSelfSeparationAlgorithmSelection = "NASAChorusAlgorithm"; //"NASAChorusAlgorithm","None"
}
