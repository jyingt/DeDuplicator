package naive.encoder;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class readinfile {
	
	public readinfile()
	{
		
	}
	public readinfile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		ss = readinmain(file);
	}
	
	
	public ArrayList<savelet> readinfolder(String foldername, File folder) throws IOException
	{
		
		File[] listoffiles = folder.listFiles();
		ArrayList<savelet> result = new ArrayList<savelet>();
		for (File file: listoffiles)
		{
			
			result.add(readinbybyte(file.getName(),file).get(0));

		}
		return result;
	}
	
	public ArrayList<savelet> readinbybyte(String filename, File file) throws IOException
	{
		
		ArrayList<savelet> save = new ArrayList<savelet>();
		FileInputStream fin = new FileInputStream(file);
		byte[] buffer = new byte[(int)file.length()];
		new DataInputStream(fin).readFully(buffer);
		fin.close();
		String result = new String(buffer,"UTF-8");
		savelet sa =  new savelet(filename,result);
		save.add(sa);
		return save;
	}
	
	public ArrayList<savelet> readinmain(String name) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{	
		ArrayList<savelet> result = new ArrayList<savelet>();
		File file = new File(name);
		if (file.isFile())
		{
			return result = readinbybyte(name,file);
		}
		else
		{
			return result = readinfolder(name,file);
		}
		
	}
	
	protected class savelet
	{
		public savelet(String filenames, String filecontents)
		{
			filename = filenames;
			filecontent = filecontents;
		}
		public String getfilename()
		{
			return filename;
		}
		public String getfilecontent()
		{
			return filecontent;
		}
		public void setfilename(String filenames)
		{
			filename=filenames;
		}
		public void setfilecontent(String filecontents)
		{
			filecontent=filecontents;
		}
		public  String filename;
		public String filecontent;
	}
	
	private static void log(String a)
	{
		System.out.println(a);
	}
	
	public ArrayList<savelet> ss;
	
}
