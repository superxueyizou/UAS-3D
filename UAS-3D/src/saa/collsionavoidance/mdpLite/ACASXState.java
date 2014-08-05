package saa.collsionavoidance.mdpLite;

public class ACASXState
{
	private final double h;
	private final double oVz;
	private final double iVz;
	private final int t;
	private final int ra;
	private final int hashCode;
	private final int order;
	
	public ACASXState(int hIdx, int oVzIdx, int iVzIdx, int tIdx, int raIdx)
	{ 

		this.h= ACASXMDP.hRes*hIdx;
		this.oVz= ACASXMDP.oVRes*oVzIdx;
		this.iVz = ACASXMDP.iVRes*iVzIdx;
		this.t= tIdx;
		this.ra = raIdx;	
		
		int a= hIdx +ACASXMDP.nh;
		int b= oVzIdx +ACASXMDP.noV;		
		int c= iVzIdx +ACASXMDP.niV;

		this.order=a*(2*ACASXMDP.noV+1)*(2*ACASXMDP.niV+1)*(ACASXMDP.nt+1)*(ACASXMDP.nra)
				+ b*(2*ACASXMDP.niV+1)*(ACASXMDP.nt+1)*(ACASXMDP.nra)
				+ c*(ACASXMDP.nt+1)*(ACASXMDP.nra)
				+ t*(ACASXMDP.nra) 
				+ ra;

		this.hashCode=order;
	}
	
	public double getH() {
		return h;
	}



	public double getoVz() {
		return oVz;
	}



	public double getiVz() {
		return iVz;
	}



	public int getT() {
		return t;
	}



	public int getRa() {
		return ra;
	}

	public boolean equals(Object obj)
	{
		if (this==obj)
		{
			return true;
		}
		
		if(obj !=null && obj.getClass()==ACASXState.class)
		{
			ACASXState state = (ACASXState)obj;
			if(this.getH()==state.getH()
					&& this.getoVz()==state.getoVz()
					&& this.getiVz() == state.getiVz()
					&& this.getT()==state.getT()
					&& this.getRa()==state.getRa())
			{
				return true;
			}
		}
		return false;
	}
	
	public int hashCode()
	{
		return this.hashCode;
	}
	
	public String toString()
	{
		return "("+h+","+oVz+","+iVz+","+t+","+ra+")";
	}

	public int getOrder() {
		return order;
	}

}
