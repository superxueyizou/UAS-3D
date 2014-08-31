package saa.collsionavoidance.mdpLite;

public class ACASXState
{
	private final double h;
	private final double oVy;
	private final double iVy;
	private final int t;
	private final int ra;
	private final int hashCode;
	private final int order;
	
	public ACASXState(int tIdx,int hIdx, int oVyIdx, int iVyIdx, int raIdx)
	{ 

		this.t= tIdx;
		this.h= ACASXMDP.hRes*hIdx;
		this.oVy= ACASXMDP.oVRes*oVyIdx;
		this.iVy = ACASXMDP.iVRes*iVyIdx;		
		this.ra = raIdx;	
		
		int a= hIdx +ACASXMDP.nh;
		int b= oVyIdx +ACASXMDP.noVy;		
		int c= iVyIdx +ACASXMDP.niVy;

		this.order=	  t*(2*ACASXMDP.nh+1)*(2*ACASXMDP.noVy+1)*(2*ACASXMDP.niVy+1)*(ACASXMDP.nra)
					+ a*(2*ACASXMDP.noVy+1)*(2*ACASXMDP.niVy+1)*(ACASXMDP.nra)
					+ b*(2*ACASXMDP.niVy+1)*(ACASXMDP.nra)
					+ c*(ACASXMDP.nra)				
					+ ra;

		this.hashCode=order;
	}
	
	public double getH() {
		return h;
	}



	public double getoVy() {
		return oVy;
	}



	public double getiVy() {
		return iVy;
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
			if(this.getT()==state.getT()
					&&this.getH()==state.getH()
					&& this.getoVy()==state.getoVy()
					&& this.getiVy() == state.getiVy()					
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
		return "("+t+","+h+","+oVy+","+iVy+","+ra+")";
	}

	public int getOrder() {
		return order;
	}

}
