package saa.collsionavoidance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import saa.collsionavoidance.acasx2d.ACASXMDP;

public class LookupTable2D
{
	private static LookupTable2D lookupTable2D;
	

	public ArrayList<Integer> indexArr;
	public ArrayList<Double> costArr=new ArrayList<>();
	public ArrayList<Integer> actionArr=new ArrayList<>();
	public BufferedReader indexFileReader = null;
	public BufferedReader costFileReader = null;
	public BufferedReader actionFileReader = null;
	
	private LookupTable2D()
	{
		System.out.println("Reading look-up table...!");
		long startTime = System.currentTimeMillis();		
	
		int numEntries=(2*ACASXMDP.nh+1)*(2*ACASXMDP.noVy+1)*(2*ACASXMDP.niVy+1)*(ACASXMDP.nt+1)*(ACASXMDP.nra) + 1;
		indexArr= new ArrayList<>();
		try 
        {
			indexFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/acasx2d/generatedFiles/indexFile")));
			costFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/acasx2d/generatedFiles/costFile")));
			actionFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/acasx2d/generatedFiles/actionFile")));

			String buffer=null;
			while((buffer=indexFileReader.readLine())!=null)
			{
				indexArr.add(Integer.parseInt(buffer));

			}
			
			if(indexArr.size()!=numEntries)
			{
				System.err.println(indexArr.size()+" entries, duplicates in indexFile, should be"+numEntries);
			}
			
			while((buffer=costFileReader.readLine())!=null)
			{
				costArr.add(Double.parseDouble(buffer));
			}
			
			while((buffer=actionFileReader.readLine())!=null)
			{
				actionArr.add(Integer.parseInt(buffer));
			}
        }
		catch(Exception e) 
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
		finally
		{
			 try 
	         {
	                if(indexFileReader!=null)
	                {
	                	indexFileReader.close();
	                }
	                if(costFileReader!=null)
	                {
	                	costFileReader.close();
	                }
	                if(actionFileReader!=null)
	                {
	                	actionFileReader.close();
	                }
	         } 
	         catch (IOException e) 
	         {
	             e.printStackTrace();
	         }
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Done! The time for reading look-up table is "+ (endTime-startTime)/1000 +" senconds.");
	}

	public static LookupTable2D getInstance()
	{
		if(lookupTable2D==null)
		{
			lookupTable2D= new LookupTable2D();
		}
		return lookupTable2D;
	}
}
