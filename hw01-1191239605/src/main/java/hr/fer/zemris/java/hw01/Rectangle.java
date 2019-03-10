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
			
			input(width, height);
			
//			System.out.format("Pravokutnik širine %f i visine %f ima površinu %f i opseg %f.", width, height, area(width, height), circumference(width, height));
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
		}
		System.out.format("Pravokutnik širine %f i visine %f ima površinu %f i opseg %f.", width, height, area(width, height), circumference(width, height));

		
	}

	
	/**
	 * Deals with the input of the values. Checks for input until the input can be converted to doubles and converts it to double.
	 * @param width Variable where the width is to be saved.
	 * @param height Variable where the height is to be saved.
	 */
	public static void input(Double width, Double height) {
		Scanner sc = new Scanner(System.in);
				
		while(true) {
			System.out.format("Unesite širinu > ");
			String input = sc.next();
			try {
				width = makeDouble(input);
			} catch(IllegalArgumentException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", input);
				continue;
			}
			break;
		}

		while(true) {
			System.out.format("Unesite visinu > ");
			String input = sc.next();
			try {
				height = Double.parseDouble(input);
			} catch(NullPointerException | NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", input);
				continue;
			}
			break;
		}
		
		sc.close();
		return;
	}
	
	/**
	 * Returns the input as as double.
	 * @param input String that needs to be returned as double.
	 * @exception IllegalArgumentException If the input string can not be converted to double.
	 */
	public static double makeDouble(String input) {
		
		double inputDouble = 0.0;
		
		try {
			inputDouble = Double.parseDouble(input);
		} catch(NullPointerException | NumberFormatException ex) {
//			System.out.format("'%s' se ne može protumačiti kao broj.%n", input);
			throw new IllegalArgumentException();
		}
		
		return inputDouble;
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
