package saa.selfseparation;

public class ChorusResData {
	double targetAngle=Double.NaN;
	double targetGs=Double.NaN;
	double targetVs=Double.NaN;
	double targetAltitude=Double.NaN;

	public ChorusResData(double targetAngle, double targetGs, double targetVs, double targetAltitude)
	{
		this.targetAngle = targetAngle;
		this.targetGs = targetGs;
		this.targetVs =targetVs;
		this.targetAltitude =targetAltitude;
	}

	public double getTargetAngle() {
		return targetAngle;
	}

	public double getTargetGs() {
		return targetGs;
	}

	public double getTargetVs() {
		return targetVs;
	}

	public double getTargetAltitude() {
		return targetAltitude;
	}
}
