package test.findcharedits;

public class test1 {

	
	public static void finddiff(String str1, String str2) {
		int i = 0;
		int index;
		int diffptr;
		String diff = new String();
		String del = new String();
		int len1 = str1.length()-1;
		int len2 = str2.length()-1;
		
		if(str1.equals(str2)) {
			System.out.println("no diff till end, complete");
			return;		
		}
		
		while(str1.charAt(i) == str2.charAt(i)) {
			if (i == len1 && i < len2) {
				System.out.println((len2 - len1) + " chars " + str2.substring(i+1) + " inserted at " + len1 + " on string1");
				return;
			}
			else if (i == len2 && i < len1) {
				System.out.println((len1 - len2) + " chars " + str1.substring(i+1) + " deleted at " + len2 + " on string1");
				return;				
			}

			i++;
		}
		diffptr = i;
		
		while(((index = str2.substring(i,Math.min(i+5,str2.length())).indexOf(str1.charAt(i))) == -1)) {				
			del += str1.charAt(i++);
		}
		
		System.out.println((i - diffptr) + " chars " + del + " deleted at " + diffptr);
		diff = str2.substring(diffptr, index);
		System.out.println((index - diffptr) + " chars " + diff + " inserted at " + diffptr);
		
		String newstr1 = str1.substring(i);
		String newstr2 = str2.substring(index);
		System.out.println("string1 is " + newstr1 + " length is " + newstr1.length() + 
						" string2 is " + newstr2 + " length is " + newstr2.length());
		
		finddiff(newstr1, newstr2);

	}

}
