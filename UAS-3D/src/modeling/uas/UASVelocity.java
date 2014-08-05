package modeling.uas;

import sim.util.Double3D;

public class UASVelocity 
{
	private Double3D velocity = new Double3D(0,0,0);
	
	public UASVelocity(Double3D velocity) 
	{
		super();
		this.velocity = velocity;
	}

	public UASVelocity(double vx, double vy, double vz) 
	{
		super();
		this.velocity = new Double3D(vx,vy,vz);

	}
	
	public Double3D getVelocity() 
	{
		return velocity;
	}

	public void setVelocity(Double3D velocity) 
	{
		this.velocity = velocity;	
	}
	
	public void setVelocity(double vx, double vy, double vz) 
	{
		this.velocity = new Double3D(vx,vy,vz);
	}
	
	

}
