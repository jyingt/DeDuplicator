package hash.compare;

import hash.hashcodegenerator.hashcodegenerator;
import hash.hashcodegenerator.initialhashcodegenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class tester2 {
	public static void main(String [] args) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		//ArrayList<String> file1 = readinfile.readinmain("plain.txt");
		//ArrayList<String> file2 = readinfile.readinmain("change.txt");
		
		//stepcheckV2(file1.get(0).toString(),file2.get(0).toString());
		//readinfile.readinmain("testfolder");
		//initialhashcodegenerator.initialwritefile("plain.txt");
		initialhashcodegenerator.initialwritefile("change.txt");
		//readinfile.readinbybyte("plain.txt");
	}
	public static boolean initialcheckV2(String s1, String s2) throws NoSuchAlgorithmException
	{
		if (hashcodegenerator.makehash(s1).equals(hashcodegenerator.makehash(s2)))
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
	private static int CHUNKS =  1000;
}
