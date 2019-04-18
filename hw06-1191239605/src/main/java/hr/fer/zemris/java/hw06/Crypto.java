package hr.fer.zemris.java.hw06;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
 *	Class {@link Crypto} which allows user to check the sha-256 hash
 *	of a file and encrypt or decrypt a file with the aes algorithm.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Crypto {
	
	/**
	 *	Main method which interacts with the user and calls
	 *	other methods to enable the functionality of checking the
	 *	sha-256 of a given file and encryption and decryption using the
	 *	aes cipher.
	 *	@param args Command name and parameters needed for that command.
	 */
	public static void main(String[] args) {
		if(args.length < 2 || args.length > 3) {
			System.out.println("Give command to run and necessary arguments.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
		if(args[0].equals("checksha")) {
			if(args.length != 2) {
				System.out.println("Invalid number of arguments given.");
			}
			checksha(sc, args[1]);
		} else if(args[0].equals("encrypt")) {
			if(args.length != 2) {
				System.out.println("Invalid number of arguments given.");
			}
			encryptDecrypt(sc, args[1], args[2], true);
		} else if(args[0].equals("decrypt")) {
			if(args.length != 2) {
				System.out.println("Invalid number of arguments given.");
			}
			encryptDecrypt(sc, args[1], args[2], false);
		} else {
			System.out.println("Unknown command given");
		}

		sc.close();
	}

	/**
	 * 	Method which interacts the with the user, gets password and 
	 * 	initialization vector from the user and calls further methods
	 * 	which actually encrypt or decrypt the given file.
	 * 	@param sc Scanner used for reading user input.
	 * 	@param fileNameBefore Name of the file which needs to be encrypted or decrypted.
	 * 	@param fileNameAfter Name of the newly created encrypted or decrypted file.
	 * 	@param encrypt Signal whether this command should call the command for encryption or decryption.
	 */
	private static void encryptDecrypt(Scanner sc, String fileNameBefore, String fileNameAfter, boolean encrypt) {
		System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
		String passwordString = sc.next();		
		/*All passwords have 16 bytes which is 32 hex-digits.*/
		if(passwordString.length() != 32) {
			System.out.println("Given input is not a valid password.");
			return;			
		}
				
		System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
		String vectorString = sc.next();
		/*All vectors have 16 bytes which is 32 hex-digits.*/
		if(vectorString.length() != 32) {
			System.out.println("Given input is not a valid initialization vector.");
			return;			
		}
		
		if(encrypt) {
			actualEncrypt(fileNameBefore, fileNameAfter, passwordString, vectorString);
			System.out.println("Encryption completed. Generated file " + fileNameAfter + " based on file " + fileNameBefore + ".");
		} else {			
			actualDecrypt(fileNameBefore, fileNameAfter, passwordString, vectorString);
			System.out.println("Decryption completed. Generated file " + fileNameAfter + " based on file " + fileNameBefore + ".");
		}
		
	}
	
	/**
	 * 	Method which decrypts the given file.
	 * 	@param whatToDecrypt File which needs to be decrypted.
	 * 	@param whereToSave Name of the decrypted file.
	 * 	@param password Password to initialize {@link Cipher}.
	 * 	@param vector Initialization Vector to initialize {@link Cipher}.
	 */
	private static void actualDecrypt(String whatToDecrypt, String whereToSave ,String password, String vector) {
		Cipher cipher = configureCipher(password, vector, false);
		if(cipher == null) {
			return;
		}
		actualEncryptOrDecrypt(whatToDecrypt, whereToSave, cipher);
	}
	
	/**
	 * 	Method which encrypts the given file.
	 * 	@param whatToDecrypt File which needs to be encrypted.
	 * 	@param whereToSave Name of the encrypted file.
	 * 	@param password Password to initialize {@link Cipher}.
	 * 	@param vector Initialization Vector to initialize {@link Cipher}.
	 */
	private static void actualEncrypt(String whatToEncrypt, String whereToSave ,String password, String vector) {
		Cipher cipher = configureCipher(password, vector, true);
		if(cipher == null) {
			return;
		}
		actualEncryptOrDecrypt(whatToEncrypt, whereToSave, cipher);
	}
	
	
	/**
	 * 	Returns a new {@link Cipher} configured with given key and
	 * 	initialization vector which encrypts or decrypts the aes cipher.
	 * 	@param keyText Password
	 * 	@param ivText Initialization vector
	 * 	@param encrypt Signals whether the cipher should do the encryption or decyption.
	 * 	@return Configured {@link Cipher} or <code>null</code> if {@link Cipher} couldn't be configured.
	 */
	private static Cipher configureCipher(String keyText, String ivText, boolean encrypt) {
		Cipher cipher;
		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException ex) {
			System.out.println("Error while configuring cipher.");
			return null;
		}
		return cipher;	
	}
	
	/**
	 * 	Actual method which performs the encryption and decryption of the given files.
	 * 	@param inputName File which needs to be encrypted or decrypted.
	 *	@param outputName Name of the file gotten after encryption or decryption.
	 *	@param cipher {@link Cipher} to use.
	 */
	private static void actualEncryptOrDecrypt(String inputName, String outputName, Cipher cipher) {
		Path inputPath;
		Path outputPath;
		try {
			inputPath = Paths.get(inputName);
			outputPath = Paths.get(outputName);
		} catch(InvalidPathException ex) {
			System.out.println("Path isn't valid");
			return;
		}
	
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(inputPath)); 
			 BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(outputPath))) {
			byte[] processed;
			byte[] buff = new byte[1024];
			while(true) {
				int read = bis.read(buff);
				if(read < 1) {
					break;
				}
				
				processed = cipher.update(buff, 0, read);
				bos.write(processed);	
			}
			
			try {
				processed = cipher.doFinal();
				bos.write(processed);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				System.out.println("Illegal block size");
				return;
			}
			
		} catch(IOException ex) {
			System.out.println("File cannot be read");
			return;
		}

	}

	/**
	 *	Method which calculates the sha256 digest of the given file
	 *	and compares it with the given digest to see if they are equal.
	 *	This method does all communication with the user and calls sha()
	 *	method which calculates the digest.
	 *	@param sc {@link Scanner} used to input expected digest.
	 *	@param file {@link String} which contains path to the file which needs to be checked. 
	 */
	private static void checksha(Scanner sc, String file) {		
		Path filepath;
		try {
			filepath = Paths.get(file);
		} catch (InvalidPathException ex) {
			System.out.println("Given argument cannot be resolved to a file.");
			return;
		}
		
		System.out.print("Please provide expected sha-256 digest for hw06test.bin:\n> ");
		String expectedString = sc.next();
		/*All sha-256 digest have 32 bytes which is 64 hex digits.*/
		if(expectedString.length() != 64) {
			System.out.println("Given digest is not a valid sha-256 digest");
			return;			
		}
		
		byte[] expected;
		try {
			expected = Util.hextobyte(expectedString);
		} catch (IllegalArgumentException ex) {
			System.out.println("Given digest is not a valid sha-256 digest");
			return;
		}
		
		byte[] calculated = sha(filepath);
		if(calculated == null) {
			return;
		}
		
		if(compareByteArray(calculated, expected)) {
			System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest.\nDigest was: " + Util.bytetohex(calculated)); 
		}
	}
	
	/**
	 * 	Method which calculates the sha-256 digest of the given file.
	 * 	@param filepath Path to the file whose sha-256 needs to be calculated.
	 * 	@return byte[] which contains the sha-256 digest, <code>null</code> if digest
	 * 			couldn't be calculated.
	 */
	private static byte[] sha(Path filepath) {
		MessageDigest messageDigest;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Unknown algorithm");
			return null;
		}
		
		byte[] digested;
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(filepath))) {
			byte[] buff = new byte[1024];
			while(true) {
				int read = bis.read(buff);
				if(read < 1) {
					break;
				}
				messageDigest.update(buff, 0, read);	
			}
			digested = messageDigest.digest();
			
		} catch(IOException ex) {
			System.out.println("File cannot be read");
			return null;
		}
		
		return digested;
	}

	/**
	 * 	Private method which compares 2 byte arrays element by element
	 * 	to see if they are equal.
	 * 	@param array1 First byte array.
	 * 	@param array2 Second byte array.
	 * 	@return <code>true</code> if the arrays contain the same elements, <code>false</code> if not.
	 */
	private static boolean compareByteArray(byte[] array1, byte[] array2) {
		if(array1.length != array2.length) {
			return false;
		}
		for(int i = 0, len = array1.length; i < len; ++i) {
			if(array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}

}
