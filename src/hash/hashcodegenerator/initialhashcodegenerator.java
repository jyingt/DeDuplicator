package hash.hashcodegenerator;

import hash.compare.comparison;
import hash.compare.readinfile;
import hash.compare.savefiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class initialhashcodegenerator extends hashcodegenerator{
	public static boolean initialfilechecker()
	{
		File ff = new File("data/data.txt");
		if (ff.exists())
			return true;
		else
			return false;
	}
	
	public static void initialwritefile(String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		if (initialfilechecker()==false)
		{
			log("not exists");
			File ff = new File(filename);
			ArrayList<String>  ss = readinfile.readinbybyte(ff);
			int ii=0;
			PrintStream outDecode = new PrintStream(new FileOutputStream("data/data.txt"));
			int LENGTH = ss.get(0).length();
			while ( ii < LENGTH)
			{
				String str = makehash(ss.get(0).substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH)));
				outDecode.println(str);
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);

			}
			outDecode.close();
			
			savefiles.initialsave("first_file.txt", ss);
		}
		else
		{
			comparison.stepcompare(filename);
		}
			
		
		
		
	}
	private static void log(String a)
	{
		System.out.println(a);
	}
	private static int CHUNKS =  1000;
}
