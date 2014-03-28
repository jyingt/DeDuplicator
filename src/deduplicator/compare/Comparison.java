package deduplicator.compare;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import deduplicator.compare.StringComparison.Change;
import deduplicator.encoder.HashCodeGenerator;
import deduplicator.encoder.MainSaving;
import deduplicator.encoder.ReadInFile;

public class Comparison extends ReadInFile{
	
	public Comparison(String filename, ArrayList<SaveLet> ss) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		stepCompare(filename,ss);
	}
	
	public ArrayList<String> stepCompare(String filename, ArrayList<SaveLet> ss2) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		ReadInFile rr = new ReadInFile("db/database/file1.txt");
		ArrayList<SaveLet> data =rr.ss;
		//String[] str = data.get(0).getFileContent().split("\n");
		
		//int ii = 0 ; 
		//int counter = 0;
		//int startpt = 0;
		//int LENGTH = ss2.get(0).getFileContent().length();
		//log(Integer.toString(LENGTH));
		
		/*while ( ii < LENGTH) {
			StringComparison tt = new StringComparison(data.get(0).getFileContent(),ss2.get(0).getFileContent());
			//tt.getDiffSize()
			ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);

		}*/

		StringComparison tt = new StringComparison(data.get(0).getFileContent(),ss2.get(0).getFileContent());
		Vector<Change> vc = tt.getLOC();
        for (int i = 0; i < vc.size(); i++) {
			Change tmpc = vc.get(i);
			result.add(tmpc.getPosition() + ":" + tmpc.getContent());
		
		}
		
		/*while (ii <= LENGTH && counter < data.size())
		{
			String temp = ss2.get(0).getFileContent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH));
			HashCodeGenerator hh = new HashCodeGenerator(temp);
			String ss = hh.str;
			if ( ss.equals(str[counter++]))
			{
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);
				//log("true");
			}
			else
			{
				//log("false");
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);
				result.add(temp);
			}
		}
		
		data.get(0).setfilecontent(result.toString());
		MainSaving mm = new MainSaving(filename, data);
		result.clear();
		log(Integer.toString(counter) + " " + data.size());
		*/
		
		return result;
	}
	
	/*
	public static boolean initialCheckV2(String s1, String s2) throws NoSuchAlgorithmException
	{
		HashCodeGenerator hh1 = new HashCodeGenerator(s1);
		HashCodeGenerator hh2 = new HashCodeGenerator(s1);
		
		if (hh1.str.equals(hh2.str))
			return true;
		else
			return false;
	}
	
	public static void stepCheckV2(String s1, String s2) throws NoSuchAlgorithmException
	{
		if (initialCheckV2(s1,s2) == false) {
			int MAXSIZE = Math.max(s1.length(), s2.length());
			int CHUNKSIZE = MAXSIZE/CHUNKS;
			int startpt = 0 ; 
			while (startpt < MAXSIZE)
			{
				//no relative location here, just naive setup structure
				String sub1 = s1.substring(startpt,Math.min(startpt+CHUNKSIZE, s1.length()));
				String sub2 = s2.substring(startpt,Math.min(startpt+CHUNKSIZE, s2.length()));
				stepCompare(sub1,sub2);
				startpt = Math.min(startpt+CHUNKSIZE, MAXSIZE);				
			}
			log("I run through the while loop");
			
		}
		else {
			log("Same file!");
		}
	}
	public static int stepCompare(String s1, String s2)
	{
		//where we need to find the relative location of different strings
		return -1;
	}
	*/
	
	private static void log(String a)
	{
		System.out.println(a);
	}
	private static void log(Integer a)
	{
		System.out.println(a);
	}
	
	public static ArrayList<String> getResult()
	{
		return result;
	}
	private static int CHUNKS = 1000;
	private static ArrayList<String> result = new ArrayList<String>();
}
