package naive.encoder;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


import algorithms.compare.comparison;

public class mainsaving extends readinfile{
	
	public mainsaving(String file, ArrayList<savelet> ss, String action) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		switch (action)
		{
		case "write":
			writefile(file);
			break;
		case "save":
			savefile(file, ss);
			
		}
		
	}
	public static boolean initialfilechecker()
	{
		File ff = new File("data/data.txt");
		if (ff.exists())
			return true;
		else
			return false;
	}
	
	public void writefile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		//temp var
		String filename="xx.txt";
		//-----------------
		
		readinfile rr = new readinfile(file);
		ArrayList<savelet>  ss = rr.ss;
		log(Integer.toString(ss.size()));
		if (initialfilechecker()==false)
		{
			log("not exists");
			//File ff = new File(filename);
			
			int ii=0;
			PrintStream outDecode = new PrintStream(new FileOutputStream("data/data.txt"));
			int LENGTH = ss.get(0).getfilecontent().length();
			while ( ii < LENGTH)
			{
				hashcodegenerator hh = new hashcodegenerator(ss.get(0).getfilecontent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH)));
				String str = hh.str;
				outDecode.println(str);
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);

			}
			outDecode.close();
			
			savefile(file, ss);
			if(ss.size()>1)
			{
				ss.remove(0);
				comparison cc = new comparison(filename,ss);
				
			}
		}
		else
		{
			log("here");
			comparison cc = new comparison(filename,ss);
		}
			
		
		
		
	}
	
	public static void savefile(String filename, ArrayList<savelet> ss2) throws FileNotFoundException
	{
		PrintStream outDecode = new PrintStream(new FileOutputStream("database/" + filename));
		
		outDecode.print(ss2.get(0).getfilecontent());
		
		outDecode.close();
	}
	
	
	private static void log(String a)
	{
		System.out.println(a);
	}
	private static int CHUNKS =  1000;
}
