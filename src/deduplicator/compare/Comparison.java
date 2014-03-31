package deduplicator.compare;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import deduplicator.compare.StringComparison.Change;
import deduplicator.encoder.HashCodeGenerator;
import deduplicator.encoder.MainSaving;
import deduplicator.encoder.ReadInFile;

public class Comparison extends ReadInFile{
	
	
	 /**
     * Constructor
     * @param filename
     * @param ArrayList<SaveLet>
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public Comparison(String filename, ArrayList<SaveLet> ss) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		stepCompare(filename,ss);
	}
	
	/**
     * Locate the difference between two files
     * @param filename
     * @param ArrayList<SaveLet>
     * @return ArrayList<SaveLet>
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
	public ArrayList<String> stepCompare(String filename, ArrayList<SaveLet> ss2) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		ReadInFile rr = new ReadInFile("db/database/file1.txt","byte");
		ArrayList<SaveLet> data =rr.ss;
		//String[] str = data.get(0).getFileContent().split("\n");
		
		//int ii = 0 ; 
		//int counter = 0;
		//int startpt = 0;
		//int LENGTH = ss2.get(0).getFileContent().length();
		//log(Integer.toString(LENGTH));
		
		/*while ( ii < LENGTH) {
			StringComparison tt = new StringComparison(data.get(0).getFileContent(),ss2.get(0).getFileContent());
			//tt.getDiffSize()
			ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);

		}*/

		StringComparison tt = new StringComparison(data.get(0).getFileContent(),ss2.get(0).getFileContent());
		Vector<Change> vc = tt.getLOC();
        for (int i = 0; i < vc.size(); i++) {
			Change tmpc = vc.get(i);
			result.add(tmpc.getPosition() + ":" + tmpc.getContent() + ":" + tmpc.getOperation());
		
		}
		
		/*while (ii <= LENGTH && counter < data.size())
		{
			String temp = ss2.get(0).getFileContent().substring(ii,Math.min(ii+LENGTH/CHUNKS, LENGTH));
			HashCodeGenerator hh = new HashCodeGenerator(temp);
			String ss = hh.str;
			if ( ss.equals(str[counter++]))
			{
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);
				//log("true");
			}
			else
			{
				//log("false");
				ii=Math.min(ii+LENGTH/CHUNKS, LENGTH);
				result.add(temp);
			}
		}
		
		data.get(0).setfilecontent(result.toString());
		MainSaving mm = new MainSaving(filename, data);
		result.clear();
		log(Integer.toString(counter) + " " + data.size());
		*/
		
		return result;
	}
	
	/**
     * Consoler output helper function
     * @param string
     */
	private static void log(String a)
	{
		System.out.println(a);
	}
	
	/**
     * Overloaded consoler output helper function
     * @param Integer
     */
	private static void log(Integer a)
	{
		System.out.println(a);
	}
	
	/**
     * Return the result for comparison
     * @return ArrayList<String>
     */
	public static ArrayList<String> getResult()
	{
		return result;
	}
	private static int CHUNKS = 1000;
	private static ArrayList<String> result = new ArrayList<String>();
}
