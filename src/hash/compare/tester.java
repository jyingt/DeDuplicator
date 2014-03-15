package hash.compare;
import naive.decoder.decodefile;
import hash.compare.diff_match_patch.Diff;
import hash.compare.diff_match_patch.Operation;
import hash.compare.diffmatch.DiffNoPreandSur;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class tester {
	/*public static void main(String [] args) throws NoSuchAlgorithmException, ClassNotFoundException, IOException
	{
		
		ArrayList<String> ori = readinfile.readin("plain.txt");
		
		ArrayList<String> neww = readinfile.readin("change.txt");
		
		if (initialcheck(ori,neww)==false)
		{
			
			
			for (int kk  = 0 ;  kk < diff.size() ; kk ++)
			{
				//ll.addAll(diff_match_patch.diff_main(diff.get(kk).ori,diff.get(kk).neww));
				str.add(diffmatch.diffmatch(diff.get(kk).ori.toString(), diff.get(kk).neww.toString())); 
			}
		}
		
		
		for (int oo = 0 ; oo < str.size() ; oo++)
		{
			//if (ll.get(oo).operation.toString().equals("EQUAL"))
			//{
			//
			//}
			//else
			//log(Integer.toString(ll.get(oo).text.toString().length()));
			//	log(ll.get(oo).toString());
			log(str.get(oo).ori.toString());
			log(str.get(oo).neww.toString());
			log("------------------------");
			
				
		}
		String str1 = "abcdef";
		String str2 = "1b2c3d4e5f";
		
		test.findcharedits.test1.finddiff(str1, str2);
		decodefile.decode("plain.txt","testoutput.txt","testchangetext",214,2,5);
	}*/
	
	private static boolean initialcheck(ArrayList ori, ArrayList neww) throws NoSuchAlgorithmException
	{
		int flag=0;
		if (ori.size()!=neww.size())
		{
			log("Diff size");
			return false;
		}
		for (int ii = 0 ; ii < ori.size() ; ii++)
		{
			if(hashcodegenerator.makehash(ori.get(ii).toString()).equals(hashcodegenerator.makehash(neww.get(ii).toString())))
			{
			
			}
			else
			{
				flag=1;
				diff.add(new DiffLine(ori.get(ii).toString(),neww.get(ii).toString(),ii+1));
				
				//log(ori.get(ii).toString());
				//log(neww.get(ii).toString());
				
			}
			
		}
		if (flag==0)
			return true;
		else
			return false;
	}
	
	/*	
	private static void checkinsert(String a, String b)
	{
		int ai = 0 ;
		int bi = 0;
		
	
		
		while(ai<a.length()-BYTE && bi<b.length()-BYTE)
		{
			if (a.substring(ai, ai+BYTE).equals(b.substring(bi, bi+BYTE)))
			{
				
			}
			else
			{
				log("diff point : " +ai + " " + bi);
				ai = ai+BYTE;
				bi=bi+BYTE+1;
				
			}
			ai++;
			bi++;
		}
		log("diff point : " + bi);
		
	}
	
	
	private static void checkdelete(String a, String b)
	{

		int ai = 0 ;
		int bi = 0;
		while(ai<a.length()-BYTE && bi<b.length()-BYTE)
		{
			if (a.substring(ai, ai+BYTE).equals(b.substring(bi, bi+BYTE)))
			{
				
			}
			else
			{
				log("diff point : " +ai + " " + bi);
				ai = ai+BYTE+1;
				bi=bi+BYTE;
				
			}
			ai++;
			bi++;
		}
		log("diff point : " + bi);
	}
	
	private static void checkchange(String a, String b)
	{
		for (int ii = 0 ; ii < a.length()-BYTE ; ii++)
		{
			if (a.substring(ii, ii+BYTE).equals(b.substring(ii, ii+BYTE)))
			{
				
			}
			else
			{
				log("diff point : " +ii);
				
			}
		}
	}
	*/
	private static void log(String a)
	{
		System.out.println(a);
	}
	
	public static class DiffLine {
	  
	    public String ori;
	   
	    public String neww;
	    
	    public int line;
	 
	    public DiffLine(String a, String b, int l) {
	     
	      this.ori = a;
	      this.neww = b;
	      this.line = l;
	    }
	}
	
	
	private static int offsetinsert=0;
	private static int offsetdelete=0;
	private final static int BYTE = 5;
	private static ArrayList<DiffLine> diff = new ArrayList<DiffLine>();
	private static ArrayList<Diff> ll = new ArrayList<Diff>();
	private static ArrayList<DiffNoPreandSur> str = new ArrayList<DiffNoPreandSur>();
}
