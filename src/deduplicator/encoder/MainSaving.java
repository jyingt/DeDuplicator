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
    
	public MainSaving(String file, ArrayList<SaveLet> ss) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
	   
	        saveFile(file, ss);
	}
	
	public MainSaving(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		 writefile(file);
	}
	
	public static boolean initialfilechecker() {
		File ff = new File(KEYPATH);
		
		if (ff.exists())
			return true;
		else
			return false;
	}
	
	public void writefile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		
		ReadInFile rr = new ReadInFile(file);
		ArrayList<SaveLet> ss = rr.ss;
		if (initialfilechecker() == false) {
			log("not exists");
			//File ff = new File(filename);
			
			int ii = 0;
			PrintStream outDecode = new PrintStream(new FileOutputStream(KEYPATH));
			int LENGTH = ss.get(0).getFileContent().length();
			
			while ( ii < LENGTH) {
				HashCodeGenerator hh = new HashCodeGenerator(ss.get(0).getFileContent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH)));
				String str = hh.str;
				outDecode.println(str);
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);

			}
			
			outDecode.close();
			
			saveFile(file, ss);
			if (ss.size() > 1) {
				ss.remove(0);
				Comparison cc = new Comparison(file,ss);
			}
		}
		else {
			log("here");
			saveFile(file, ss);
			//Comparison cc = new Comparison(filename,ss);
		}
	}
	
	public static void saveFile(String filename, ArrayList<SaveLet> ss2) throws IOException {
		
		PrintStream outDecode_file = new PrintStream(new FileOutputStream(DBPATH + filename));
		outDecode_file.print(ss2.get(0).getFileContent());
		FileWriter fw = new FileWriter(NAMEPATH, true);    
		BufferedWriter bw = new BufferedWriter(fw);  
		
		bw.write(ss2.get(0).getFileName());    
		bw.newLine(); 
		bw.flush();    
		bw.close();    
		fw.close();  
		log(filename + " is saved successfully!");

	}
	
	private static void log(String a) {
		System.out.println(a);
	}
	
	private static String KEYPATH = "db/key/data.txt";
	private static String DBPATH = "db/database/";
	private static String NAMEPATH = "db/key/name.txt";
}
