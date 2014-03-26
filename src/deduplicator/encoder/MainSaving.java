package deduplicator.encoder;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import deduplicator.compare.Comparison;

/**
 * Database saving?
 * @author Yuteng Pan, Hansen Zhang
 *
 */
public class MainSaving extends ReadInFile
{
    private static int CHUNKS = 1000;
    
	public MainSaving(String file, ArrayList<SaveLet> ss, String action) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
	    if (action.equals("write"))
	        writefile(file);
	    else if (action.equals("save"))
	        savefile(file, ss);
	}
	
	public static boolean initialfilechecker() {
		File ff = new File("data/data.txt");
		
		if (ff.exists())
			return true;
		else
			return false;
	}
	
	public void writefile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		//temp var
		String filename = "xx.txt";
		//-----------------
		
		ReadInFile rr = new ReadInFile(file);
		ArrayList<SaveLet> ss = rr.ss;
		log("" + ss.size());
		
		if (initialfilechecker() == false) {
			log("not exists");
			//File ff = new File(filename);
			
			int ii = 0;
			PrintStream outDecode = new PrintStream(new FileOutputStream("data/data.txt"));
			int LENGTH = ss.get(0).getFileContent().length();
			
			while ( ii < LENGTH) {
				HashCodeGenerator hh = new HashCodeGenerator(ss.get(0).getFileContent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH)));
				String str = hh.str;
				outDecode.println(str);
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);

			}
			
			outDecode.close();
			
			savefile(file, ss);
			if (ss.size() > 1) {
				ss.remove(0);
				Comparison cc = new Comparison(filename,ss);
			}
		}
		else {
			log("here");
			Comparison cc = new Comparison(filename,ss);
		}
	}
	
	public static void savefile(String filename, ArrayList<SaveLet> ss2) throws FileNotFoundException {
		PrintStream outDecode = new PrintStream(new FileOutputStream("database/" + filename));
		outDecode.print(ss2.get(0).getFileContent());
		outDecode.close();
	}
	
	private static void log(String a) {
		System.out.println(a);
	}
}
