package deduplicator.encoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create hash of input string
 * @author Yuteng Pan, Hansen Zhang
 *
 */
public class HashCodeGenerator
{
    public String str;
    /**
     * Constructor
     * @param input
     * @throws NoSuchAlgorithmException
     */
	public HashCodeGenerator(String input) throws NoSuchAlgorithmException {
		str = makeHash(input);		
	}
	 /**
     * Generate the hashcode
     * @param input
     * @return string
     * @throws NoSuchAlgorithmException
     */
	public String makeHash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.reset();
		byte[] buffer = input.getBytes();
		md.update(buffer);
		byte[] digest = md.digest();
		String hexStr = "";
		
		for (int ii = 0 ; ii < digest.length ; ii++) {
			hexStr += Integer.toString((digest[ii] & 0xff) + 0x100,16).substring(1);
		}
		
		return hexStr;
	}
}
