package algorithms.compare;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import naive.encoder.hashcodegenerator;
import naive.encoder.mainsaving;


public class Deduplicator {
	public static void main(String [] args) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		//ArrayList<String> file1 = readinfile.readinmain("plain.txt");
		//ArrayList<String> file2 = readinfile.readinmain("change.txt");
		
		//stepcheckV2(file1.get(0).toString(),file2.get(0).toString());
		//readinfile.readinmain("testfolder");
		mainsaving mm = new mainsaving("plain.txt",null,"write");
		//initialhashcodegenerator.writefile("testfolder");
		//readinfile.readinbybyte("plain.txt");
		stringcomparison tt = new stringcomparison();
		tt.finddiff("abcdefghijkl", "defghijk", 0);
		tt.show();
	}
	
	private static void log(String a)
	{
		System.out.println(a);
	}
	
}
