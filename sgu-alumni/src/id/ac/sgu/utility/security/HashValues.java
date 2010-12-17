package id.ac.sgu.utility.security;

import id.ac.sgu.utility.Cons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class HashValues {

	private static Logger logger = Logger.getLogger(HashValues.class);
	
	public final static String SHA1 = "SHA-1";
	public final static String SHA256 = "SHA-256";
	public final static String SHA512 = "SHA-512";

	// Default
	public static String HASH_TYPE = SHA512;

	public String hashValue(String in) {
		String out = "";
		try {
			MessageDigest md;
			md = MessageDigest.getInstance(HASH_TYPE);
			md.update(in.getBytes());
			byte[] mb = md.digest();
			
			for (int i = 0; i < mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp));
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}
			logger.info(out.length());
			logger.info("Hash: " + out);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error("ERROR: " + e.getMessage());
		}
		return out;
	}

	public boolean compare(String h1, String h2) {
		return (h1.equalsIgnoreCase(h2)) ? Cons.IS_MATCH : Cons.NOT_MATCH;
	}
		
}
