package saa.sense;

public class PerfectSensor extends Sensor 
{	
	public double range;
	public double falsePositive;
	public double falseNegative;	 

	public PerfectSensor()
	{
		range = 9260;
		falsePositive=0;
		falseNegative=0;
	}

}
