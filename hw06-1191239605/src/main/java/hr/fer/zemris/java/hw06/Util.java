package hr.fer.zemris.java.hw06;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;

/**
 *	Class Util. TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Util {

	/**
	 *	TODO 
	 *	@param keyText String which needs to be converted to byte array.
	 *	@return Byte array which holds the given String.
	 *	@throws IllegalArgumentException KeyText is odd-sized or invalid characters.
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
			int hexPrikazanUIntu =((Character.digit(keyText.charAt(i), 16) * 16 + Character.digit(keyText.charAt(i + 1), 16)));
			byteArray[i/2] = (byte)  hexPrikazanUIntu;
			
		}
		
		return byteArray;
		
	}
	
	/**
	 *	TODO
	 *	@param bytearray Array which needs to be converted to String.
	 *	@return String value of the byte array. 
	 */
	public static String bytetohex(byte[] bytearray) {
		
		StringBuilder sb = new StringBuilder();
		
		for(var i : bytearray) {
			//prva verzija00
//			String s = Integer.toBinaryString(i);
//			if(i < 0) {
//				s = s.substring(24);
//			}
//			String s2 = Integer.toString(Integer.parseInt(s, 2), 16);
//			sb.append("0123456789abcdef".contains(s2)? "0" + s2 : s2);
//			
//			System.out.println(i);
//			System.out.println(Integer.toString(i & 0xff, 16));
			String toAppend = Integer.toString(i & 0xff, 16);
			sb.append(toAppend.length() == 1 ? "0" + toAppend : toAppend);
			
		}
		return sb.toString();
		
		
		
	}
	
}
