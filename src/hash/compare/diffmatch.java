package hash.compare;

import java.util.ArrayList;

public class diffmatch {
		
	public static DiffNoPreandSur diffmatch(String text1, String text2)
	{
	    int commonlength = diff_commonPrefix(text1, text2);
	    String commonprefix = text1.substring(0, commonlength);
	    text1 = text1.substring(commonlength);
	    text2 = text2.substring(commonlength);
	
	    // Trim off common suffix (speedup).
	    commonlength = diff_commonSuffix(text1, text2);
	    String commonsuffix = text1.substring(text1.length() - commonlength);
	    text1 = text1.substring(0, text1.length() - commonlength);
	    text2 = text2.substring(0, text2.length() - commonlength);
	   
	    return (new DiffNoPreandSur(text1,text2));
	}
	
	 public static int diff_commonPrefix(String text1, String text2) {
		    // Performance analysis: http://neil.fraser.name/news/2007/10/09/
		    int n = Math.min(text1.length(), text2.length());
		    for (int i = 0; i < n; i++) {
		      if (text1.charAt(i) != text2.charAt(i)) {
		        return i;
		      }
		    }
		    return n;
		  }
	 
	 public static int diff_commonSuffix(String text1, String text2) {
		    // Performance analysis: http://neil.fraser.name/news/2007/10/09/
		    int text1_length = text1.length();
		    int text2_length = text2.length();
		    int n = Math.min(text1_length, text2_length);
		    for (int i = 1; i <= n; i++) {
		      if (text1.charAt(text1_length - i) != text2.charAt(text2_length - i)) {
		        return i - 1;
		      }
		    }
		    return n;
		  }
	 public static class DiffNoPreandSur {
		  
		    public String ori;
		   
		    public String neww;
		    
		  
		 
		    public DiffNoPreandSur(String a, String b) {
		     
		      this.ori = a;
		      this.neww = b;
		     
		    }
		}
	 private static ArrayList<DiffNoPreandSur> str = new ArrayList<DiffNoPreandSur>();
}
