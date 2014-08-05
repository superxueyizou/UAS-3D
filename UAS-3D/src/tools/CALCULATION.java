/**
 * 
 */
package tools;


import sim.util.Double2D;

/**
 * @author Xueyi
 *
 */
public class CALCULATION 
{
	
	public final static double epsilon = 1.0e-6;

	/**providing calculation tool by some static methods
	 * 
	 */
	public CALCULATION() 
	{
		// TODO Auto-generated constructor stub
	}	
	
/****************************************************************************************************************************************/
		
	public static double det(Double2D v1, Double2D v2)
    {
        return v1.x * v2.y - v1.y * v2.x;
    }
	
    /*
     * a first point of a line
     * b second point of a line
     * c testing point
     */
	public static boolean leftOf(Double2D a, Double2D b, Double2D c)
    {
        return (-det(a.subtract(c), b.subtract(a))>epsilon);
    }
	
	public static boolean rightOf(Double2D a, Double2D b, Double2D c)
    {
        return (det(a.subtract(c), b.subtract(a))>epsilon);
    }
      
   public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		Double2D v1= new Double2D(1,1);
		Double2D v2= new Double2D(-1,1);
		Double2D v3= new Double2D(-1,-1);
		Double2D v4= new Double2D(1,-1);
		
		Double2D v5= new Double2D(1,0);
		Double2D v6= new Double2D(0,1);
		Double2D v7= new Double2D(-1,0);
		Double2D v8= new Double2D(0,-1);
		
		Double2D v= v3 ;

		
		System.out.println(rightOf(new Double2D(),v2, v3));
		
	}

}
