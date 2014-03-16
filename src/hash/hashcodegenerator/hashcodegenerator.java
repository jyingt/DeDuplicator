package hash.hashcodegenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class hashcodegenerator {
	public static String makehash(String input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.reset();
		byte[] buffer = input.getBytes();
		md.update(buffer);
		byte[] digest = md.digest();
		String hexStr="";
		for (int ii = 0 ; ii < digest.length ; ii++)
		{
			hexStr += Integer.toString((digest[ii] & 0xff) + 0x100,16).substring(1);
		}
		return hexStr;
	}
	
	

}
