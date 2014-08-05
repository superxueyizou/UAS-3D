package modeling.uas;

/**
 *
 * @author Robert Lee
 */
public class UASPerformance
{
	//the maximum possible values of the uasBag statistics
	private double stdDevX;
	private double stdDevY;
	private double stdDevZ;
	private double maxSpeed;
	private double minSpeed;
	private double prefSpeed;
	private double maxClimb;
	private double maxDescent;
	private double maxTurning;
    private double maxAcceleration;
	private double maxDeceleration;
	
	
	public UASPerformance(double stdDevX, double stdDevY,double stdDevZ,double maxUASSpeed, double minUASSpeed,double prefUASSpeed, double maxUASClimb, double maxUASDescent, double maxUASTurning, double maxUASAcceleration, double maxUASDeceleration)
	{
		this.setStdDevX(stdDevX);
		this.setStdDevY(stdDevY);
		this.setStdDevZ(stdDevZ);
		maxSpeed = maxUASSpeed;
		minSpeed = minUASSpeed;
		prefSpeed = prefUASSpeed;
		maxClimb = maxUASClimb;
		maxDescent = maxUASDescent;
		maxTurning = maxUASTurning;
		maxAcceleration = maxUASAcceleration;
		maxDeceleration = maxUASDeceleration;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(double maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	public double getMaxDeceleration() {
		return maxDeceleration;
	}

	public void setMaxDeceleration(double maxDeceleration) {
		this.maxDeceleration = maxDeceleration;
	}

	public double getMaxTurning() {
		return maxTurning;
	}

	public double getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(double minSpeed) {
		this.minSpeed = minSpeed;
	}
	
	public double getPrefSpeed() {
		return prefSpeed;
	}

	public void setPrefSpeed(double prefSpeed) {
		this.prefSpeed = prefSpeed;
	}
	
	public double getMaxClimb() {
		return maxClimb;
	}

	public void setMaxClimb(double maxClimb) {
		this.maxClimb = maxClimb;
	}

	public double getMaxDescent() {
		return maxDescent;
	}

	public void setMaxDescent(double maxDescent) {
		this.maxDescent = maxDescent;
	}
	
	public void setMaxTurning(double maxTurning) {
		this.maxTurning = maxTurning;
	}

	public double getStdDevX() {
		return stdDevX;
	}

	public void setStdDevX(double stdDevX) {
		this.stdDevX = stdDevX;
	}

	public double getStdDevY() {
		return stdDevY;
	}

	public void setStdDevY(double stdDevY) {
		this.stdDevY = stdDevY;
	}
	
	public double getStdDevZ() {
		return stdDevZ;
	}

	public void setStdDevZ(double stdDevZ) {
		this.stdDevZ = stdDevZ;
	}

}
