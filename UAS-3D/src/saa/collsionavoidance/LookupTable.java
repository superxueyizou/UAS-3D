package saa.collsionavoidance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import saa.collsionavoidance.mdpLite.ACASXMDP;

public class LookupTable
{
	private static LookupTable lookupTable;
	

	public ArrayList<Integer> indexArr;
	public ArrayList<Double> costArr=new ArrayList<>();
	public ArrayList<Integer> actionArr=new ArrayList<>();
	public BufferedReader indexFileReader = null;
	public BufferedReader costFileReader = null;
	public BufferedReader actionFileReader = null;
	
	private LookupTable()
	{
		System.out.println("Reading look-up table...!");
		long startTime = System.currentTimeMillis();		
	
		int numEntries=(2*ACASXMDP.nh+1)*(2*ACASXMDP.noV+1)*(2*ACASXMDP.niV+1)*(ACASXMDP.nt+1)*(ACASXMDP.nra) + 1;
		indexArr= new ArrayList<>();
		try 
        {
			indexFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/indexFile")));
			costFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/costFile")));
			actionFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/actionFile")));

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

	public static LookupTable getInstance()
	{
		if(lookupTable==null)
		{
			lookupTable= new LookupTable();
		}
		return lookupTable;
	}
}
