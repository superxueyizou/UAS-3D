package saa.collsionavoidance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils
{
//	costFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/costFile")));
//	actionFileReader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/actionFile")));

	

	public static boolean compareIndexTable()
	{
		System.out.println("Reading index look-up table...!");
		long startTime = System.currentTimeMillis();	
		long endTime;	
		BufferedReader indexFileReader1=null;
		BufferedReader indexFileReader2=null;
		ArrayList<Integer>	indexArr1= new ArrayList<>();
		ArrayList<Integer>	indexArr2= new ArrayList<>();
		try 
        {
			indexFileReader1 = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/indexFile")));			
			String buffer=null;
			while((buffer=indexFileReader1.readLine())!=null)
			{
				indexArr1.add(Integer.parseInt(buffer));

			}
			
			indexFileReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/acasx3d/generatedFiles/indexFile")));			
			while((buffer=indexFileReader2.readLine())!=null)
			{
				indexArr2.add(Integer.parseInt(buffer));

			}
			
			if(indexArr1.size()!=indexArr2.size())
			{
				System.err.println(indexArr1.size()+" entries isn't equal to "+indexArr2.size());
				return false;
			}
			
			for(int i=0;i<indexArr1.size();i++)
			{
				if(!indexArr1.get(i).equals(indexArr2.get(i)))
				{
					System.out.println(indexArr1.get(i)+ "  "+ indexArr2.get(i));
					endTime = System.currentTimeMillis();
					System.out.println(false+"Done! The time for comparing index look-up tables is "+ (endTime-startTime)/1000 +" senconds.");
					return false;
				}
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
	                if(indexFileReader1!=null)
	                {
	                	indexFileReader1.close();
	                }
	                if(indexFileReader2!=null)
	                {
	                	indexFileReader2.close();
	                }	               
	         } 
	         catch (IOException e) 
	         {
	             e.printStackTrace();
	         }
		}
		
		endTime = System.currentTimeMillis();
		System.out.println(true+"Done! The time for comparing index look-up tables is "+ (endTime-startTime)/1000 +" senconds.");
		return true;
	}
	
	public static boolean compareCostTable()
	{
		System.out.println("Reading cost look-up table...!");
		long startTime = System.currentTimeMillis();
		long endTime ;
		BufferedReader costFileReader1=null;
		BufferedReader costFileReader2=null;
		ArrayList<Double>	costArr1= new ArrayList<>();
		ArrayList<Double>	costArr2= new ArrayList<>();
		try 
        {
			costFileReader1 = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/costFile")));			
			String buffer=null;
			while((buffer=costFileReader1.readLine())!=null)
			{
				costArr1.add(Double.parseDouble(buffer));

			}
			
			costFileReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/acasx3d/generatedFiles/costFile")));			
			while((buffer=costFileReader2.readLine())!=null)
			{
				costArr2.add(Double.parseDouble(buffer));

			}
			
			if(costArr1.size()!=costArr2.size())
			{
				System.err.println(costArr1.size()+" entries isn't equal to "+costArr2.size());
				return false;
			}
			
			for(int i=0;i<costArr1.size();i++)
			{
				if(!costArr1.get(i).equals(costArr2.get(i)))
				{
					System.out.println(costArr1.get(i)+ "  "+ costArr2.get(i));
					endTime = System.currentTimeMillis();
					System.out.println(false+" Done! The time for comparing cost look-up tables is "+ (endTime-startTime)/1000 +" senconds.");
					return false;
				}
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
	                if(costFileReader1!=null)
	                {
	                	costFileReader1.close();
	                }
	                if(costFileReader2!=null)
	                {
	                	costFileReader2.close();
	                }
	         } 
	         catch (IOException e) 
	         {
	             e.printStackTrace();
	         }
		}

		endTime = System.currentTimeMillis();
		System.out.println(true+" Done! The time for comparing cost look-up tables is "+ (endTime-startTime)/1000 +" senconds.");
		return true;
	}
	
	public static boolean compareActionTable()
	{
		System.out.println("Reading action look-up  table...!");
		long startTime = System.currentTimeMillis();	
		long endTime;
		BufferedReader actionFileReader1=null;
		BufferedReader actionFileReader2=null;
		ArrayList<Integer>	actionArr1= new ArrayList<>();
		ArrayList<Integer>	actionArr2= new ArrayList<>();
		try 
        {
			actionFileReader1 = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/mdpLite/generatedFiles/actionFile")));			
			String buffer=null;
			while((buffer=actionFileReader1.readLine())!=null)
			{
				actionArr1.add(Integer.parseInt(buffer));

			}
			
			actionFileReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("./src/saa/collsionavoidance/acasx3d/generatedFiles/actionFile")));			
			while((buffer=actionFileReader2.readLine())!=null)
			{
				actionArr2.add(Integer.parseInt(buffer));

			}
			
			if(actionArr1.size()!=actionArr2.size())
			{
				System.err.println(actionArr1.size()+" entries isn't equal to "+actionArr2.size());
				return false;
			}
			
			for(int i=0;i<actionArr1.size();i++)
			{
				if(!actionArr1.get(i).equals(actionArr2.get(i)))
				{
					System.out.println(actionArr1.get(i)+ "  "+ actionArr2.get(i));
					endTime = System.currentTimeMillis();
					System.out.println(false+" Done! The time for comparing action look-up tables is "+ (endTime-startTime)/1000 +" senconds.");
					return false;
				}
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
	                if(actionFileReader1!=null)
	                {
	                	actionFileReader1.close();
	                }
	                if(actionFileReader2!=null)
	                {
	                	actionFileReader2.close();
	                }	               
	         } 
	         catch (IOException e) 
	         {
	             e.printStackTrace();
	         }
		}

		endTime = System.currentTimeMillis();
		System.out.println(true+ " Done! The time for comparing action look-up tables is "+ (endTime-startTime)/1000 +" senconds.");
		return true;
	}
	
	public static void main(String[] args) 
	{
		compareIndexTable();
		compareCostTable();
		compareActionTable();
	}

}
