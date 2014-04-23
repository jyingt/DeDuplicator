package deduplicator.compare;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import deduplicator.compare.StringComparison.Change;
import deduplicator.main.Hash;
import deduplicator.main.StoreFile;
import deduplicator.main.ReadInFile;

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
	public ArrayList<CompareLet> stepCompare(String filename, ArrayList<SaveLet> ss2) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		ReadInFile ff = new ReadInFile(NAMEPATH, "line");
		String tmpfilename  = ff.ss.get(0).getFileContent();
		//log(tmpfilename);
		ReadInFile rr = new ReadInFile("db/database/" + tmpfilename,"byte");
		ArrayList<SaveLet> data =rr.ss;
		//log(data.get(0).getFileContent());
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
		for (SaveLet s: ss2)
		{
			//log(s.getFileContent());
			//log(data.get(0).getFileContent());
			StringComparison tt = new StringComparison(data.get(0).getFileContent(),s.getFileContent());
			Vector<Change> vc = tt.getLOC();
			ArrayList<String> tmpresult = new ArrayList<String>();
	        for (int i = 0; i < vc.size(); i++) {
				Change tmpc = vc.get(i);
				tmpresult.add(tmpc.getPosition() + ":" + tmpc.getContent() + ":" + tmpc.getOperation());
			
			}
	        //log(vc.size());
	        result.add(new CompareLet(s.getFileName(),tmpresult));
	        
		}
//		for (CompareLet cccc : result)
//			log(cccc.getFileName());
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
	public class CompareLet
	{
		public CompareLet(String _filename, ArrayList<String> _diff)
		{
			filename = _filename;
			diff=_diff;
		}
        public String getFileName() {
            return filename;
        }
        
        public ArrayList<String> getFileDiff() {
            return diff;
        }
		public String filename;
		public ArrayList<String> diff;
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
	public static ArrayList<CompareLet> getResult()
	{
		return result;
	}
	private static int CHUNKS = 1000;
	private static ArrayList<CompareLet> result = new ArrayList<CompareLet>();
    private static String NAMEPATH = "db/key/name.txt";
}
