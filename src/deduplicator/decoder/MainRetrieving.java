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
	public MainRetrieving(String Path, String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		WriteToFile(Path,filename);
	}
    public boolean checkExisting(String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
    {
    	ReadInFile rr = new ReadInFile(NAMEPATH,"byte");
    	String[] str = rr.ss.get(0).getFileContent().split("\n");
    	SAMPLEFILE = str[0].substring(0,str[0].length()-1);
    	for (String s: str)
    	{
    		s=s.substring(0, s.length()-1);
    		if (s.equals(filename)==true)
    			return true;
    	}
    	return false;
    	
    }
    public String decoder(String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
    {
    	if (checkExisting(filename)==true)
    	{
    		ReadInFile ff = new ReadInFile();
    		ReadInFile ori = new ReadInFile();
    		
    		if (SAMPLEFILE.equals(filename)==false)
    		{
    			ff = new ReadInFile(FILEPATH+filename,"line");
    			ori = new ReadInFile(FILEPATH + SAMPLEFILE,"byte");
    		}
    		else
    		{
    			ff = new ReadInFile(FILEPATH+filename,"byte");
    			return ff.ss.get(0).getFileContent();
    		}
    		
    		
    		
    		
    		
    		
    		
    		for (SaveLet s : ff.ss)
    		{
    			String[] str = s.getFileContent().split(":");
    			AC.add(new Change(Integer.parseInt(str[0]),Integer.parseInt(str[2]),str[1]));
    			
    		}
		
    		for (Change c:AC)
    			c.Print();
    		String output = ori.ss.get(0).getFileContent();
    		log(output.length(),true);
    		for (int ii = AC.size()-1; ii >=0 ; ii--)
    		{
    			switch (AC.get(ii).getOperation())
    			{
    			case 0:
    				output = output.substring(0, AC.get(ii).getPosition()) + AC.get(ii).getContent() + output.substring(AC.get(ii).getPosition()+1, output.length());
    				break;
    			case 1:
    				log(AC.get(ii).getPosition()+AC.get(ii).getContent().length() + " + " + output.length(),true);
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
    
    public void WriteToFile(String Path, String filename) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
    {
    	File checkexist = new File(Path);
    	if (checkexist.exists())
    	{
	    	String content = decoder(filename);
	    	PrintStream outDecode_file = new PrintStream(new FileOutputStream(Path + "/" + filename));
	   		outDecode_file.println(content);
    	}
    	else
    	{
    		log("Path does not exist!",true);
    	}

    }
    
    public class Change
	{
		public Change(int myposition, int myoperation, String mycontent) {
			position = myposition;
			operation = myoperation;
			content = mycontent;
		}
		
		private int position;
		private int operation; // 0 for replace, 1 for delete, 2 for insert
		private String content;
		
		public int getPosition() {
			return position;
		}
		public int getOperation() {
			return operation;
		}
		public String getContent() {
			return content;
		}
		public void Print()
		{
			log(position + " " + operation + " " + content , true);
		}
		
	}
    private static void log(String a,boolean newline) {
    	if(newline)
    		System.out.println(a);
    	else
    		System.out.print(a);
	}
    private static void log(Integer a,boolean newline) {
    	if(newline)
    		System.out.println(a);
    	else
    		System.out.print(a);
	}
    private static String NAMEPATH = "db/key/name.txt";
    private static String FILEPATH = "db/database/";
    private static String SAMPLEFILE;
    private static ArrayList<Change> AC = new ArrayList<Change>();
}
