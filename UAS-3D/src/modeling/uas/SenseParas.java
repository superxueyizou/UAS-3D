package modeling.uas;

public class SenseParas 
{
	private double viewingRange=38.89; //how many units in front of the uas it can see obstacles 3889/100
	private double viewingAngle=60; //220 this is the angle for the viewing in front of the uas, viewingAngle / 2 in both directions from right in front of the uas


	public SenseParas(double viewingRange, double viewingAngle) 
	{
		this.viewingRange = viewingRange;
		this.viewingAngle = viewingAngle;
	}

	public double getViewingRange() {
		return viewingRange;
	}

	public void setViewingRange(double viewingRange) {
		this.viewingRange = viewingRange;
	}

	public double getViewingAngle() {
		return viewingAngle;
	}

	public void setViewingAngle(double viewingAngle) {
		this.viewingAngle = viewingAngle;
	}


}
