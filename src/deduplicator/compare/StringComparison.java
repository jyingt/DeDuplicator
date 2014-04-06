package deduplicator.compare;

import java.util.Vector;

public class StringComparison
{
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
		
	}
	
	public StringComparison(String s1, String s2) {
		LOC = new Vector <Change> ();
		findDiff(s1,s2,0);
	}
	
	public void findDiff(String str1, String str2, int startpos) {
		int i = 0;
		int index;
		int diffptr;
		String diff = new String();
		String del = new String();
		int len1 = str1.length()-1;
		int len2 = str2.length()-1;
		
		//System.out.println("startpos is " + startpos);

		if(str1.equals(str2)) {
			//System.out.println("no diff till end, complete");
			return;		
		}
		
		while(str1.charAt(i) == str2.charAt(i)) {
			if (i == len1 && i < len2) {
				//System.out.println((len2 - len1) + " chars " + str2.substring(i+1) + " inserted at " + len1 + " on string1");
				// sample string has more characters at the end
				LOC.addElement(new Change(startpos + i+1, 2, str2.substring(i+1)));
				return;
			}
			else if (i == len2 && i < len1) {
				//System.out.println((len1 - len2) + " chars " + str1.substring(i+1) + " deleted at " + len2 + " on string1");
				// other string has more characters at the end
				LOC.addElement(new Change(startpos +i+1, 1, str1.substring(i+1)));
				return;				
			}

			i++;
		}
		// here both diffptr and i means the total size of identical section from the start
		diffptr = i;
		
//		String window = str2.substring(Math.max(0, i-5),Math.min(i+5,str2.length()));
		String window = str2.substring(i,Math.min(i+6,str2.length()));
		//System.out.println("window is " + window); 
		
		// locate within 5 character window the char that matches
		while(((index = window.indexOf(str1.charAt(i))) == -1)) {				
			del += str1.charAt(i++);
			if (i == str1.length()) {
				System.out.println(startpos + " " + i);
				String replace = str2.substring(diffptr);
				LOC.addElement(new Change(startpos + i  - del.length() + replace.length(), 1, del.substring(replace.length())));
				LOC.addElement(new Change(startpos + i  - del.length(), 0, replace));
				return;
			}
		}
		
		//System.out.println("index is " + index + " i is " + i);
		//System.out.println("index is " + index + " diffptr is " + diffptr);
		/* after the while loop, i becomes the index of the start of the next 
		 * string1 in original string1
		 */
		// index is the end of insertion
		index = diffptr + index;
		/* here index means the index of the start of the the next string2 in
		 * string2
		 */
		
//		System.out.println((i - diffptr) + " chars " + del + " deleted at " + diffptr);
//		System.out.println((index - diffptr) + " chars " + diff + " inserted at " + diffptr);
		diff = str2.substring(diffptr, index);
		int delcnt = i - diffptr;		// size of deletion
		int inscnt = index - diffptr;	// size of insertion
		int currpos = diffptr + startpos;
		//System.out.println(delcnt + " chars " + del + " deleted at " + currpos);
		//System.out.println(inscnt + " chars " + diff + " inserted at " + currpos);

		String newstr1 = str1.substring(i);
		String newstr2 = str2.substring(index);
		
		// when only insert happens
		if(delcnt == 0) {
			LOC.addElement(new Change(currpos, 2, diff));
		}
		// when only delete happens
		else if(inscnt == 0) {
			LOC.addElement(new Change(currpos, 1, del));
		}
		// when they both happen
		else {
			if (delcnt == inscnt) {
				LOC.addElement(new Change(currpos, 0, diff));
			}
			else if(delcnt > inscnt) {
				LOC.addElement(new Change(currpos, 0, diff));
				LOC.addElement(new Change(currpos + inscnt, 1, del.substring(inscnt)));
			}
			else if(delcnt < inscnt) {
				LOC.addElement(new Change(currpos, 0, diff.substring(0,delcnt)));
				LOC.addElement(new Change(currpos + delcnt, 2, diff.substring(delcnt)));
			}
			
		}
		
//		System.out.println("string1 is " + newstr1 + " length is " + newstr1.length() + 
//						" string2 is " + newstr2 + " length is " + newstr2.length());
		
		findDiff(newstr1, newstr2, startpos + i);

		return;
	}

	public void show() {
		for (int i = 0; i < LOC.size(); i++) {
			Change tmpc = LOC.get(i);
			String tmps;
			if (tmpc.getOperation() == 0) tmps = "replaced";
			else if (tmpc.getOperation() == 1) tmps = "deleted";
			else tmps = "insterted";
			System.out.println("At position " + tmpc.getPosition() + ", " + tmpc.getContent()
								+ " is " + tmps);
		
		}
		int sizediff = getDiffSize();
		System.out.println("Size Diff:" + sizediff);
	}
	
	public int getDiffSize()
	{
		int result=0;
		for (Change cc : LOC )
		{
			if (cc.getOperation()==1)
				result-= cc.getContent().length();
			if (cc.getOperation()==2)
				result+=cc.getContent().length() ;
			
		}
		return result;
	}
	
	public static Vector<Change> getLOC()
	{

		return LOC;
	}
	private static Vector <Change> LOC;
}
