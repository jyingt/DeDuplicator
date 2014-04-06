package deduplicator.encoder;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import deduplicator.compare.Comparison;
import deduplicator.compare.Comparison.CompareLet;

/**
 * Database encoding part - save files
 * @author Yuteng Pan, Hansen Zhang
 */
public class MainSaving extends ReadInFile
{

    /**
     * Constructor
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public MainSaving() throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
	    //
	}
    /**
     * Overloaded Constructor
     * @param file
     * @param ss
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public MainSaving(String file, ArrayList<SaveLet> ss) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
	    saveFile(file, ss, true);
	}
	
	/**
     * Overloaded constructor
     * @param file
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public MainSaving(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		String[] names = file.split("/");
	    file = names[names.length-1];
	   // log(file);
		writeFile(file);
	}
	
	/**
     * Initial database existing check
     * @return boolean
     */
	public static boolean initialFileChecker() {
		File ff = new File(KEYPATHFILE);
		
		if (ff.exists())
			return true;
		else
			return false;
	}
	
	/**
     * Handle all the writing files to database
     * @param file
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public void writeFile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		ReadInFile rr = new ReadInFile(file, "byte");
		ArrayList<SaveLet> ss = rr.ss;
		
		if (initialFileChecker() == false) {
			log("not exists");
			//File ff = new File(filename);
			File key = new File(KEYPATH);
			
			if (key.exists()) {
				//
			}
			else {
				new File(MAINPATH).mkdir();
				new File(KEYPATH).mkdir();
				new File(DBPATH).mkdir();
			}
			
			int ii = 0;
			PrintStream outDecode = new PrintStream(new FileOutputStream(KEYPATHFILE));
			int LENGTH = ss.get(0).getFileContent().length();
			
			// Keep list of hashes
			ArrayList<String> hashList = new ArrayList<String>();
			
			while (ii < LENGTH) {
				Hash hh = new Hash(ss.get(0).getFileContent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH)));
				String str = hh.str;
				outDecode.println(str);     // write hash to database
				ii = Math.min(ii + LENGTH/CHUNKS, LENGTH);
				
				// Add hash to list
				hashList.add(hh.str);
			}
			
			// Serialize list of hashes
			try {
			    FileOutputStream fileOut = new FileOutputStream("hashes.ser");
			    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			    objectOut.writeObject(hashList);
			    objectOut.close();
			    fileOut.close();
			} 
			catch (IOException e) {
			    e.printStackTrace();
			}
			
			outDecode.close();
			
			
			if (ss.size() > 1) {
				ArrayList<SaveLet> tmp = new ArrayList<SaveLet>();	
				tmp.add(ss.get(0));
				saveFile(file,tmp, false);
				ss.remove(0);
				//log(ss.size());
				Comparison cc = new Comparison(file, ss);
				ArrayList<CompareLet> result = cc.getResult();

				
				for (CompareLet s : result) {
					ArrayList<SaveLet> slresult = new ArrayList<SaveLet>();
					for (String sss: s.getFileDiff())
					{
						slresult.add(new SaveLet(s.getFileName(),sss));
					}
//					for ( SaveLet str : slresult)
//						log(str.getFileContent());
					saveFile(file, slresult, true);
				}


				result.clear();
			}
			else
			{
				saveFile(file,ss, false);
			}
		}
		else {
			log("here");
			//saveFile(file, ss);
			Comparison cc = new Comparison(file, ss);
			ArrayList<CompareLet> result = cc.getResult();		
			for (CompareLet s : result) {
				ArrayList<SaveLet> slresult = new ArrayList<SaveLet>();
				
				for (String sss: s.getFileDiff())
				{
					slresult.add(new SaveLet(s.getFileName(),sss));
				}
				if ( slresult.isEmpty()==true)
				{
					slresult.add(new SaveLet(s.getFileName(),""));
					saveFile(file, slresult, true);
				}
				else
				{
					saveFile(file, slresult, true);
				}
			}
			
			
			result.clear();

 		}
	}
	
	/**
     * Output files to database
     * @param filename
     * @param ss2 
     * @param newline
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public static void saveFile(String filename, ArrayList<SaveLet> savelets, boolean newline) throws IOException {
		File ff = new File(filename);
		if (ff.isFile()==false)
		{
			if (new File(DBPATH + filename).exists()==false)
				new File(DBPATH + filename).mkdir();
			PrintStream outDecode_file = new PrintStream(new FileOutputStream(DBPATH + filename + "/" + savelets.get(0).getFileName() ));
	
				for (SaveLet savelet : savelets) {
					if (newline == true) 
					{
						outDecode_file.println(savelet.getFileContent());
					}
					else
					{
						outDecode_file.print(savelet.getFileContent());
					}
	
				}
				outDecode_file.close();
			
			log(filename + "/" + savelets.get(0).getFileName() + " is saved successfully!");

			FileWriter writer = new FileWriter(NAMEPATHFILE, true);    
			BufferedWriter bufferedWriter = new BufferedWriter(writer);  
			bufferedWriter.write(filename + "/" +  savelets.get(0).getFileName());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			writer.close();
			

		}
		else
		{	
			PrintStream outDecode_file = new PrintStream(new FileOutputStream(DBPATH + filename));
			for (SaveLet savelet : savelets) {
				if (newline == true) 
				{
					outDecode_file.println(savelet.getFileContent());
				}
				else
				{
					outDecode_file.print(savelet.getFileContent());
				}
				
			}
			outDecode_file.close();
			log(filename + " is saved successfully!");
			FileWriter writer = new FileWriter(NAMEPATHFILE, true);    
			BufferedWriter bufferedWriter = new BufferedWriter(writer);  
			bufferedWriter.write(savelets.get(0).getFileName());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			writer.close();
		}
		
		
		
		

	}
	
	/**
     * Consoler output helper function
     * @param string
     */
	private static void log(Object a) {
		System.out.println(a.toString());
	}
	
	private static final String MAINPATH = "db";
	private static final String KEYPATH = "db/key";
	private static final String KEYPATHFILE = "db/key/data.txt";
	private static final String DBPATH = "db/database/";
	private static final String NAMEPATHFILE = "db/key/name.txt";
    private static int CHUNKS = 1000;
}
