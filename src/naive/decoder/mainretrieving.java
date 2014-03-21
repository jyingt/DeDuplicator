package naive.decoder;




import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import naive.encoder.readinfile;



public class mainretrieving extends readinfile{
	public static void decode(String inputfilename, String outputfilename, String change, int line, int startpt, int orilength ) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
	{
		/*readinfile rr = new readinfile(inputfilename);
		ArrayList<savelet> defaultversion = rr.ss;
		String ll = defaultversion.get(0).getfilecontent().get(line-1);
		System.out.println(defaultversion.get(line-1));
		String llpre = ll.substring(0,startpt);
		String llsuf = ll.substring(startpt+orilength,ll.length());
		ll = llpre + change + llsuf;
		defaultversion.get(0).getfilecontent().add(line-1,ll);
		defaultversion.remove(line);
		
		System.out.println(defaultversion.get(line-1));
		PrintStream outDecode = new PrintStream(new FileOutputStream(outputfilename));
		outDecode.print(defaultversion.get(line-1));
		outDecode.close();*/
	}
	
		
	
}
