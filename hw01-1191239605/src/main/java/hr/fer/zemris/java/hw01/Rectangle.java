package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/** 
 * 	Class <code>Rectangle</code> which allows the user to calculates the area and circumference of the rectangle with the given width and height.
 * 
 *	@author Jakov Pucekovic
 * 	@version 1.0 
 */
public class Rectangle {

	/**
	 *	The method interacts with the user and prints the area and circumference of the rectangle.
	 *
	 *	@param args Width and height of the rectangle. 
	 */
	public static void main(String[] args) {
		if(args.length != 0 && args.length != 2) {
			System.out.format("Prilikom pokretanja programa moguće je unijeti širinu i visinu.%n");
			return;
		}
		
		double width, height;
		
		if(args.length == 0) {
			Scanner sc = new Scanner(System.in);
			
			width = input("width", sc);
			height = input("height", sc);
			
			sc.close();
		}
		else {
			try {
				height = Double.parseDouble(args[0]);
				width = Double.parseDouble(args[1]);
			} catch(NullPointerException | NumberFormatException ex) {
				String errorMessage = ex.getMessage();
				errorMessage = errorMessage.substring(errorMessage.indexOf(':') + 2);
				System.out.format("%s se ne može protumačiti kao broj.%n", errorMessage);
				return;
			}
			if(height < 0 || width < 0) {
				System.out.format("Unijeli ste negativnu vrijednost.%n");
				return;
			}
		}
		System.out.format("Pravokutnik širine %f i visine %f ima površinu %f i opseg %f.", width, height, area(width, height), circumference(width, height));
	}
	
	/**
	 * Deals with the input of the values. Checks for input until the input is equal to or greater than 0 and can be converted to double.
	 * 
	 * @param string "width" or "height" depending on what you want to input.
	 * @return The input of the user converted to double.
	 * @exception IllegalArgumentException Throws an exception when the argument is not one of the specified.
	 */
	public static double input(String string, Scanner scanner) {
		String printHelp;
		
		if(string.equals("width")) {
			printHelp= "širinu";
		}
		else if(string.equals("height")) {
			printHelp = "visinu";
		}
		else {
			throw new IllegalArgumentException("Wrong input.");
		}
		
		double returnValue = 0.0;
		
		while(true) {
			System.out.format("Unesite %s > ", printHelp);
			String scannerInput = scanner.next();
			
			try {
				returnValue = Double.parseDouble(scannerInput);
			} catch(NullPointerException | NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", scannerInput);
				continue;
			}
			if(returnValue < 0) {
				System.out.format("Unijeli ste negativnu vrijednost.%n");
				continue;
			}
			break;
		}
		return returnValue;
	}
	
	/**\
	 *	Calculates the area of the rectangle.
	 *
	 *	@param width The width of the rectangle.
	 *	@param height The height of the rectangle. 
	 *	@return The area of the rectangle.
	 *	@exception IllegalArgumentException The exception is thrown when one of the parameters is less than 0.
	 */
	public static double area(double width, double height) {
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException("Arguments lesser than zero.");
		}
		return width*height;
	}
	
	/**
	 *	Calculates the circumference of the rectangle.
	 *
	 * 	@param widht The width of the rectangle.
	 *  @param height The height of the rectangle. 
	 *  @return The circumference of the rectangle.
	 *	@exception IllegalArgumentException The exception is thrown when one of the parameters is less than 0.
	 */
	public static double circumference(double width, double height) {
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException("Arguments lesser than zero.");
		}
		return 2*(width+height);
	}
	
}
