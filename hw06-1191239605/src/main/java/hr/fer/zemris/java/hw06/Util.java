package hr.fer.zemris.java.hw06;

/**
 *	Class {@link Util} which allows for conversion from arrays 
 *	of bytes to their string representation in hex (each byte
 *	is represented with 2 hex characters) and vise-versa.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Util {

	/**
	 *	Method which converts the given hex string representation of bytes
	 *	to an array of bytes.
	 *	@param keyText String which needs to be converted to byte array.
	 *	@return Byte array which holds the given String.
	 *	@throws IllegalArgumentException KeyText is odd-sized or contains invalid characters.
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() == 0) {
			return new byte[0];
		}
		if(keyText.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		byte[] byteArray = new byte[keyText.length()/2];

		for (int i = 0, s = keyText.length() ; i < s; i += 2) {
			byteArray[i/2] = (byte) (Character.digit(keyText.charAt(i), 16) * 16 + Character.digit(keyText.charAt(i + 1), 16));
		}
		
		return byteArray;
		
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
