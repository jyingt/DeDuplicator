package deduplicator.main;

import java.io.*;
import java.security.*;
import java.util.*;

import javax.swing.JOptionPane;

import deduplicator.compare.Comparison;
import deduplicator.compare.Comparison.CompareLet;
import deduplicator.serializer.*;

/**
 * Database encoding part - save files
 * @author Yuteng Pan, Hansen Zhang
 */
public class StoreFile extends ReadInFile
{
    /**
     * Constructor
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public StoreFile() throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
	    //
	}
	
//    /**
//     * Overloaded Constructor
//     * @param file
//     * @param ss
//     * @throws ClassNotFoundException
//     * @throws NoSuchAlgorithmException
//     * @throws IOException
//     */
//	public StoreFile(String file, ArrayList<SaveLet> ss) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
//	    saveFile(file, ss, true);
//	    
//	    File database = new File(DBPATH);
//	    System.out.print("Current storage: ");
//	    
//	    if (database.exists())
//	        System.out.print(folderSize(database));
//	    else
//	        System.out.print("0");
//	    
//	    System.out.println(" B");
//	}
	
	/**
     * Overloaded constructor
     * @param file
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public StoreFile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		String[] names = file.split("/");
	    file = names[names.length-1];
	    // log(file);
		writeFile(file);
		
		File database = new File(DBPATH);
        System.out.print("Current storage: ");
        
        if (database.exists())
            System.out.print(folderSize(database));
        else
            System.out.print("0");
        
        System.out.println(" B");
	}
	
	/**
     * Initial database existing check
     * @return boolean
     */
	public static boolean initialFileChecker() {
		File ff = new File(NAMEPATHFILE);
		
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
		flag=true;
		ReadInFile rr = new ReadInFile(file, "byte");
		ArrayList<SaveLet> ss = rr.ss;
          if (ss==null)
          {
        	 flag=false;
        	 System.out.println(flag);
         	 return;
          }
		
		if (initialFileChecker() == false) {
			//File key = new File(MAINPATH);
		    File key = new File(DBPATH);
			
			if (!key.exists()) {
				//new File(MAINPATH).mkdir();
				new File(DBPATH).mkdir();
			}
			
			if (ss.size() > 1) {
				ArrayList<SaveLet> tmp = new ArrayList<SaveLet>();	
				tmp.add(ss.get(0));
				saveFile(file,tmp, false,true);
				ss.remove(0);
				//log(ss.size());
				Comparison cc = new Comparison(file, ss);
				ArrayList<CompareLet> result = cc.getResult();
				
				for (CompareLet s : result) {
					ArrayList<SaveLet> slresult = new ArrayList<SaveLet>();
					for (String sss: s.getFileDiff()) {
						slresult.add(new SaveLet(s.getFileName(),sss));
					}
//					for ( SaveLet str : slresult)
//						log(str.getFileContent());
					saveFile(file, slresult, true,false);
				}

				result.clear();

			}
			else {
				saveFile(file,ss, false,true);
			}
		}
		else {
			//log("here");
			//saveFile(file, ss);
			Comparison cc = new Comparison(file, ss);
			ArrayList<CompareLet> result = cc.getResult();
			
			for (CompareLet s : result) {
				ArrayList<SaveLet> slresult = new ArrayList<SaveLet>();
				
				for (String sss: s.getFileDiff()) {
					slresult.add(new SaveLet(s.getFileName(),sss));
				}
				if (slresult.isEmpty() == true) {
					slresult.add(new SaveLet(s.getFileName(), ""));
					saveFile(file, slresult, true,false);
				}
				else {
					saveFile(file, slresult, true,false);
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
	public static void saveFile(String filename, ArrayList<SaveLet> savelets, boolean newline, boolean print) throws IOException {
		File ff = new File(filename);
		if (savelets!=null)
		{
		if (ff.isFile() == false) {
			if (new File(DBPATH + filename).exists() == false)
				new File(DBPATH + filename).mkdir();
			
				if(print)	{
				    PrintStream outDecode_file = new PrintStream(new FileOutputStream(DBPATH + filename + "/" + savelets.get(0).getFileName() ));
		
					for (SaveLet savelet : savelets) {
						if (newline == true)  {
							outDecode_file.println(savelet.getFileContent());
						}
						else {
							outDecode_file.print(savelet.getFileContent());
						}
					}
						
					outDecode_file.close();
				}
			
			log(filename + "/" + savelets.get(0).getFileName() + " is saved successfully!");

			FileWriter writer = new FileWriter(NAMEPATHFILE, true);    
			BufferedWriter bufferedWriter = new BufferedWriter(writer);  
			bufferedWriter.write(filename + "/" +  savelets.get(0).getFileName());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			writer.close();
		}
		else {
				if (print){
				PrintStream outDecode_file = new PrintStream(new FileOutputStream(DBPATH + filename));
				
					for (SaveLet savelet : savelets) {
						if (newline == true) {
							outDecode_file.println(savelet.getFileContent());
						}
						else {
							outDecode_file.print(savelet.getFileContent());
						}				
					}
				
				outDecode_file.close();
				}
			log(filename + " is saved successfully!");
			log("haha");
			FileWriter writer = new FileWriter(NAMEPATHFILE, true);    
			BufferedWriter bufferedWriter = new BufferedWriter(writer);  
			bufferedWriter.write(savelets.get(0).getFileName());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			writer.close();
		}
		}
		else
		{
			
		}
	}
	
	/**
	 * 
	 * @param directory
	 * @return
	 */
	public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
	}
    
	
	/**
     * Consoler output helper function
     * @param string
     */
	private static void log(Object a) {
		System.out.println(a.toString());
	}
	
	//private static final String MAINPATH = "db";
	private static final String DBPATH = "database/";
	private static final String NAMEPATHFILE = "database/name.txt";
	public static boolean flag;
}
