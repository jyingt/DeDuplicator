package deduplicator.decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import deduplicator.encoder.ReadInFile;

public class MainRetrieving extends ReadInFile
{
    private static String NAMEPATH = "db/key/name.txt";
    private static String FILEPATH = "db/database/";
    private static String SAMPLEFILE;
    
    private static ArrayList<Change> AC = new ArrayList<Change>();
    
    /**
     * Constructor
     * @param Path
     * @param filename
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public MainRetrieving(String path, String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		WriteToFile(path, filename);
	}
	
	/**
     * Check whether the database has this file or not
     * @param filename
     * @return boolean 
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public boolean checkExisting(String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
    	ReadInFile rr = new ReadInFile(NAMEPATH, "byte");
    	String[] str = rr.ss.get(0).getFileContent().split("\n");
    	SAMPLEFILE = str[0].substring(0,str[0].length()-1);
    	
    	for (String s : str) {
    		s = s.substring(0, s.length()-1);
    		
    		if (s.equals(filename) == true)
    			return true;
    	}
    	
    	return false;
    }
    
	/**
     * Regenerate encoded file and prepare for retrieving
     * @param filename
     * @return string
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String decodeFile(String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
    	if (checkExisting(filename) == true) {
    		ReadInFile ff = new ReadInFile();
    		ReadInFile ori = new ReadInFile();
    		
    		if (SAMPLEFILE.equals(filename) == false) {
    			ff = new ReadInFile(FILEPATH + filename, "line");
    			ori = new ReadInFile(FILEPATH + SAMPLEFILE, "byte");
    		}
    		else {
    			ff = new ReadInFile(FILEPATH + filename, "byte");
    			return ff.ss.get(0).getFileContent();
    		}

    		for (SaveLet s : ff.ss) {
    			String[] str = s.getFileContent().split(":");
    			AC.add(new Change(Integer.parseInt(str[0]),Integer.parseInt(str[2]),str[1]));
    		}
    		
    		String output = ori.ss.get(0).getFileContent();
    		log(output.length(),true);
    		
    		for (int ii = AC.size()-1; ii >= 0; ii--) {
    			switch (AC.get(ii).getOperation()) {
        			case 0:
        				output = output.substring(0, AC.get(ii).getPosition()) + AC.get(ii).getContent() + output.substring(AC.get(ii).getPosition()+1, output.length());
        				break;
        			case 1:
        				log(AC.get(ii).getPosition() + AC.get(ii).getContent().length() + " + " + output.length(), true);
        				output = output.substring(0, AC.get(ii).getPosition()) + output.substring(AC.get(ii).getPosition()+AC.get(ii).getContent().length(), output.length());
        				break;
        			case 2:
        				output = output.substring(0, AC.get(ii).getPosition()) + AC.get(ii).getContent() + output.substring(AC.get(ii).getPosition(), output.length());
        				break;
        			default:
    			        break;
    			}
    		}
    		
    		//log(output,true);
    		AC.clear();
    		return output;
    	}
    	
    	return null;
    }
    
	/**
     * Retrieve the files from database
     * @param filename
     * @param Path
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public void WriteToFile(String path, String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
    	File file = new File(path);
    	
    	if (!file.exists())
    		file.mkdir();
    	
    	int length = filename.length();
    	String fn;
    	
    	if (filename.charAt(length-1) == 13)
    		fn = filename.substring(0, filename.length()-1);
    	else
    		fn = filename;
    	String content = decodeFile(fn);
    	PrintStream outDecode_file = new PrintStream(new FileOutputStream(path + "/" + fn));
   		outDecode_file.println(content);
    }
    
	/**
     * Consoler output helper function
     * @param string
     * @param newline
     */
    private static void log(String a, boolean newline) {
    	if (newline)
    		System.out.println(a);
    	else
    		System.out.print(a);
	}
    
	/**
     * Overloaded consoler output helper function
     * @param Integer
     * @param newline
     */
    private static void log(Integer a, boolean newline) {
    	if (newline)
    		System.out.println(a);
    	else
    		System.out.print(a);
	}
    
    public class Change
    {
        private int position;
        private int operation; // 0 for replace, 1 for delete, 2 for insert
        private String content;
        
        public Change(int myposition, int myoperation, String mycontent) {
            position = myposition;
            operation = myoperation;
            content = mycontent;
        }
        
        public int getPosition() {
            return position;
        }
        
        public int getOperation() {
            return operation;
        }
        
        public String getContent() {
            return content;
        }
        
        public void print() {
            log(position + " " + operation + " " + content, true);
        }
    }
}
