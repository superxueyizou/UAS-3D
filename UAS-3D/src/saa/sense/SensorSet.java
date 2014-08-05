package saa.sense;

public class SensorSet 
{
	public PerfectSensor perfectSensor;
	public ADS_B ads_b;
	public TCAS tcas;
	public Radar radar;
	public EOIR eoir;
	
	public double falsePositive;
	public double falseNegative;
	public double range;

	public SensorSet() 
	{
		// TODO Auto-generated constructor stub		
		perfectSensor = new PerfectSensor();
		ads_b=null;
		tcas=null;
		radar=null;
		eoir=null;
	}
	
	public void synthesize()
	{
		falsePositive=perfectSensor.falsePositive;
		falseNegative=perfectSensor.falseNegative;
		range=perfectSensor.range;
	}

}
