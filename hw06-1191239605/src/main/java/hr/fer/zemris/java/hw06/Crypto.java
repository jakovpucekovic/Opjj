package hr.fer.zemris.java.hw06;

import java.io.IOException;
import hr.fer.zemris.java.hw06.Util;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *	Class Crypto TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Crypto {

	private static Path filepath;
	
	public static void main(String[] args) {
		java.util.Scanner sc = new Scanner(System.in);
		System.out.println("checksha/encrypt/decrypt");
		if("checksha".equals(sc.next())) {
			
			Path filepath;
			try {
				System.out.println("Filepath");
				filepath = Paths.get("src/main/resources/" + sc.next());
			} catch(InvalidPathException ex) {
				System.out.println("Path isn't valid");
				sc.close();
				return;
			}
			
			System.out.println("Please provide expected sha-256 digest for hw06test.bin:\n> ");
			String expected = sc.next();
			
			if(checksha(filepath, expected)) {
				System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest."); //TODO do kraja ispis
			}
		}
		
		
		
//		encrypt("e52217e3ee213ef1ffdee3a192e2ac7e", "000102030405060708090a0b0c0d0e0f", false);
//		decrypt("e52217e3ee213ef1ffdee3a192e2ac7e", "000102030405060708090a0b0c0d0e0f");
		
		
		System.out.println("end");
		sc.close();
	}
	
	public static void decrypt(String keyText, String ivText) {
		encrypt(keyText, ivText, false);
	}
	
	public static void encrypt(String keyText, String ivText, boolean encr) {
//		String keyText = "";
//		String ivText =  "";
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e1) {
			System.out.println("Algorithm doesnt exist.");
			return;
		} catch (NoSuchPaddingException e1) {
			System.out.println("Padding doesnt exist.");
			return;
		}
		try {
			cipher.init(encr ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException e) {
			System.out.println("Invalid key.");
			return;
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println("Invalid algorithm parameter.");
			return;
		}
		
		Path output;
		try {
			output = Paths.get("src/main/resources/hw06.part2.pdf");
		} catch(InvalidPathException ex) {
			System.out.println("Path isn't valid");
			return;
		}
		
		byte[] encrypted;
		try (InputStream is = Files.newInputStream(filepath); OutputStream os = Files.newOutputStream(output)) {
			byte[] buff = new byte[1024];
			while(true) {
				int read = is.read(buff);
				if(read < 1) {
					break;
				}
				
				encrypted = cipher.update(buff, 0, read);
				os.write(encrypted);
					
			}
			
			try {
				encrypted = cipher.doFinal();
			} catch (IllegalBlockSizeException e) {
				System.out.println("Illegal block size");
				return;
			} catch (BadPaddingException e) {
				System.out.println("Bad padding");
				return;
			}
			
		} catch(IOException ex) {
			System.out.println("File cannot be read");
			return;
		}
		

	
	}
	
	
	public static byte[] sha(Path filepath) {
		MessageDigest messageDigest;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("sha256");
			return null;
		}
		
		byte[] digest;
		try (InputStream is = Files.newInputStream(filepath)) {
			byte[] buff = new byte[1024];
			while(true) {
				int read = is.read(buff);
				if(read < 1) {
					break;
				}
				
				messageDigest.update(buff, 0, read);
				
					
			}
			digest = messageDigest.digest();
			
		} catch(IOException ex) {
			System.out.println("File cannot be read");
			return null;
		}
		
		return digest;
	}
	
	
	
	public static boolean checksha(Path filepath, String expected) {
		byte[] digested = sha(filepath);
		return compareByteArray(digested, Util.hextobyte(expected));
		
	}
	
	
	private static boolean compareByteArray(byte[] array1, byte[] array2) {
		if(array1.length != array2.length) {
			return false;
		}
		int len = array1.length;
		for(int i = 0; i < len; ++i) {
			if(array1[i] != array2[i]) {
				return false;
			}
		}
		
		return true;
	}
}
