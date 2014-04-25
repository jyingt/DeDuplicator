package deduplicator.compare;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

import deduplicator.compare.StringComparison.Change;
import deduplicator.main.StoreFile;
import deduplicator.main.ReadInFile;
import deduplicator.serializer.Serializer;

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

		ReadInFile rr = new ReadInFile("database/" + tmpfilename, "byte");
		ArrayList<SaveLet> data =rr.ss;
		
		for (SaveLet s: ss2)
		{

			StringComparison tt = new StringComparison(data.get(0).getFileContent(),s.getFileContent());
			Vector<Change> vc = tt.getLOC();
			if (s.getFolderName()==null)
				Serializer.serializeObjectToPath(vc, DBPATH +s.getFileName());
			else
			{
				if (s.getFolderName().charAt(s.getFolderName().length()-1)=='/')
					Serializer.serializeObjectToPath(vc, DBPATH + s.getFolderName() +s.getFileName());
				else
					Serializer.serializeObjectToPath(vc, DBPATH + s.getFolderName() +"/" +s.getFileName());
			}
			ArrayList<String> tmpresult = new ArrayList<String>();
	        for (int i = 0; i < vc.size(); i++) {
				Change tmpc = vc.get(i);
				tmpresult.add(tmpc.getPosition() + ":" + tmpc.getContent() + ":" + tmpc.getOperation());
			
			}

	        result.add(new CompareLet(s.getFileName(),tmpresult));
	        
		}
		
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
    private static String NAMEPATH = "database/name.txt";
	private static final String DBPATH = "database/";
	private static final long serialVersionUID = 1L;
}
