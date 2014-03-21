package algorithms.compare;


import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import naive.encoder.hashcodegenerator;
import naive.encoder.mainsaving;
import naive.encoder.readinfile;

public class comparison extends readinfile{
	
	public comparison(String filename, ArrayList<savelet> ss) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		stepcompare(filename,ss);
	}
	
	public void stepcompare(String filename, ArrayList<savelet> ss2) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		readinfile rr = new readinfile("data/data.txt");
		ArrayList<savelet> data =rr.ss;
		String[] str = data.get(0).getfilecontent().split("\n");
		ArrayList<String> result = new ArrayList<String>();
		int ii = 0 ; 
		int counter=0;
		int LENGTH = ss2.get(0).getfilecontent().length();
		log(Integer.toString(LENGTH));
		while (  ii <= LENGTH && counter < data.size() )
		{
			String temp = ss2.get(0).getfilecontent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH));
			hashcodegenerator hh = new hashcodegenerator(temp);
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
		mainsaving mm = new mainsaving(filename, data,"save");
		result.clear();
		log(Integer.toString(counter) + " " + data.size());
	}
	
	public static boolean initialcheckV2(String s1, String s2) throws NoSuchAlgorithmException
	{
		hashcodegenerator hh1 = new hashcodegenerator(s1);
		hashcodegenerator hh2 = new hashcodegenerator(s1);
		if (hh1.str.equals(hh2.str))
		{
			return true;
		}
		else
			return false;
	}
	
	public static void stepcheckV2(String s1, String s2) throws NoSuchAlgorithmException
	{
		if (initialcheckV2(s1,s2)==false)
		{
			int MAXSIZE = Math.max(s1.length(), s2.length());
			int CHUNKSIZE = MAXSIZE/CHUNKS;
			int startpt = 0 ; 
			while (startpt<MAXSIZE)
			{
				//no relative location here, just naive setup structure
				String sub1 = s1.substring(startpt,Math.min(startpt+CHUNKSIZE, s1.length()));
				String sub2 = s2.substring(startpt,Math.min(startpt+CHUNKSIZE, s2.length()));
				stepcompare(sub1,sub2);
				startpt = Math.min(startpt+CHUNKSIZE, MAXSIZE);				
			}
			log("I run through the while loop");
			
		}
		else
		{
			log("Same file!");
		}
	}
	public static int stepcompare(String s1, String s2)
	{
		//where we need to find the relative location of different strings
		return -1;
	}
	
	
	private static void log(String a)
	{
		System.out.println(a);
	}
	private static int CHUNKS = 1000;
}
