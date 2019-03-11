package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * @author Jakov Pucekovic
 * @version 1.0.0 
 */

public class Rectangle {

	public static void main(String ... args) {
		if(args.length != 0 && args.length != 2) {
			System.out.format("Prilikom pokretanja programa moguće je unijeti širinu i visinu.%n");
			return;
		}
		
		double width=0.0, height=0.0;
		
		if(args.length != 2) {
			
			width = input("width");
			height = input("height");
		
		}
		else {
			try {
				height = Double.parseDouble(args[0]);
			} catch(NullPointerException | NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", args[0]);
				return;
			}
			
			try {
				width = Double.parseDouble(args[1]);
			} catch(NullPointerException | NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", args[1]);
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
	 * Deals with the input of the values. Checks for input until the input can be converted to doubles and converts it to double.
	 * @param string "width" or "height" depending on what you want to input.
	 * @exception IllegalArgumentException Throws an exception when the argument is not one of the specified.
	 */
	public static double input(String string) {
		
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
		
		Scanner sc = new Scanner(System.in);
		double returnValue = 0.0;
		
		while(true) {
			//zasto ovaj kod ne radi?
			System.out.format("Unesite %s > ", printHelp);
			String scannerInput = sc.next();
			try {
				returnValue = Double.parseDouble(scannerInput);
			} catch(NullPointerException | NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", scannerInput);
				continue;
			}
			if(returnValue<0) {
				System.out.format("Unijeli ste negativnu vrijednost.%n");
				continue;
			}
			break;
		}

		sc.close();
		return returnValue;
	}
	
	
	/**\
	 *	Calculates the area of the rectangle.
	 *	@param width The width of the rectangle.
	 *	@param height The height of the rectangle. 
	 */
	public static double area(double width, double height) {
		return width*height;
	}
	
	/**
	 *	Calculates the circumference of the rectangle.
	 * 	@param widht The width of the rectangle.
	 *  @param height The height of the rectangle. 
	 */
	public static double circumference(double width, double height) {
		return 2*(width+height);
	}
	
}
