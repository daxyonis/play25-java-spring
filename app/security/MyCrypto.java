package security;

import javax.inject.Inject;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

import org.apache.commons.lang3.ArrayUtils;

import play.Configuration;
import controllers.GBOlogger;

/**
 * Takes care of strong cryptographic stuff
 * @author Eva
 *
 */
public class MyCrypto {
	
	private final static int SALT_LENGTH = 32;
	private final static String SHA256_DIGEST_ALGORITHM = "SHA-256";
	private final static String HMAC_SHA256_ALGORITHM = "HmacSHA256";	
	private static Mac mac;		
	
	/**
	 * Crypto constructor
	 * Initializes MAC object
	 */
	@Inject
	public MyCrypto(Configuration playConfig){		
		String secret = playConfig.getString("play.crypto.secret");
		// Initialize MAC 		
		SecretKeySpec signingKey;
		signingKey = new SecretKeySpec(secret.getBytes(),	HMAC_SHA256_ALGORITHM);
		try {
			mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		} catch (NoSuchAlgorithmException e1) {			
			GBOlogger.LogError("Crypto()", e1, null);
		}
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {			
			GBOlogger.LogError("Crypto()", e, null);
		}			
	}
	
	
	/**
	 * Generates salt
	 * @return  byte array containing salt
	 */
	public static byte[] generateSalt(){
		try{
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[SALT_LENGTH];
	        random.nextBytes(salt);
	        return salt;
		}
		catch(NoSuchAlgorithmException e){
			GBOlogger.LogError("generateSalt()", e, null);
			return null;
		}
		
	}
	
	/**
	 * Encrypt with SHA256
	 * @param value  value to encrypt
	 * @return  digested value as a byte[]
	 */
	public static byte[] getSha256(String value) {
        try {
            return MessageDigest.getInstance(SHA256_DIGEST_ALGORITHM).digest(value.getBytes());
        }
        catch (NoSuchAlgorithmException e) {
        	GBOlogger.LogError("getSha256()", e, null);
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * Calculate the Hashed Message Authentification Code (HMAC)
	 * @param data  : the message
	 * @return  the hashed message as byte[]
	 */
	public static byte[] calculateHMAC(byte[] data) {
			byte[] rawHmac = mac.doFinal(data);
			return rawHmac;
	}
	
	/**
	 * Encrypt some data using salt and HMAC
	 * @param salt
	 * @param data
	 * @return hashed+salted data
	 */
	public static byte[] encryptHMAC(byte[] salt, String data){		
    	byte[] saltedPwd;
		saltedPwd = ArrayUtils.addAll(salt, data.getBytes());
		return ArrayUtils.addAll(salt, calculateHMAC(saltedPwd));       
	}

}
