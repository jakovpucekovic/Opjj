package hr.fer.zemris.java.tecaj_13.web.servlets.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *	Class {@link Crypto} which allows user to hash input with sha-1
 *	hashing algorithm.
 *TODO javadoc prepraviti
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Crypto {

	/**
	 * 	Method which calculates the sha-1 digest of the given input.
	 * 	@param input {@link String} whose sha-1 needs to be calculated.
	 * 	@return byte[] which contains the sha-1 digest, <code>null</code> if digest
	 * 			couldn't be calculated.
	 */
	public static byte[] sha(String input) {
		MessageDigest messageDigest;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Unknown algorithm");
			return null;
		}
		
		messageDigest.update(input.getBytes());	
		byte[] digested = messageDigest.digest();
		
		return digested;
	}
	
	/**
	 *	Method which converts the given byte array to hex string representation
	 *	where each byte is converted to 2 characters in hex.
	 *	@param bytearray Array which needs to be converted to String.
	 *	@return String value of the byte array. 
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		
		for(var i : bytearray) {
			String toAppend = Integer.toString(i & 0xff, 16);
			sb.append(toAppend.length() == 1 ? "0" + toAppend : toAppend);
		}
		
		return sb.toString();
	}
}
