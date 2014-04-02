package deduplicator.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create hash of input string
 * @author Yuteng Pan, Hansen Zhang
 *
 */
public class Hash
{
    public String str;
    
    /**
     * Constructor
     * @param input
     */
	public Hash(String input) {
		str = makeHash(input);
	}
	
	/**
     * Generate hash of string
     * @param input the string to hash
     * @return hash
     */
	public String makeHash(String input) {
	    byte[] buffer = input.getBytes();
	    String hexStr = "";
	    
	    try {
    		MessageDigest md = MessageDigest.getInstance("SHA1");
    		md.reset();
    		md.update(buffer);
    		byte[] digest = md.digest();
    		
    		for (int ii = 0; ii < digest.length; ii++)
                hexStr += Integer.toString((digest[ii] & 0xff) + 0x100, 16).substring(1);
	    }
	    catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    
	    return hexStr;
	}
}
