package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class <code>Factorial</code> allows the user to calculate the factorial of the given input. 
 * 
 * @author Jakov Pucekovic
 * @version 1.0
 */
public class Factorial {

	/**
	 *	The method interacts with the user, checks if the input is valid and calculates the factorial value.
	 *	The input is should an integer between 3 and 20 (including). If the input isn't in the specified range, the function asks for another input.
	 *	Enter "kraj" to end.	
	 *
	 *	@param args No arguments.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			System.out.print( "Unesite broj > ");
			String scannerInput = sc.next();
			
			if(scannerInput.equals("kraj")) {
				System.out.format("Doviđenja.%n");
				sc.close();
				return;
			}
			
			int number = 0;
			
			try {
				number = Integer.parseInt(scannerInput);
			} catch(NumberFormatException ex) {
				System.out.format("'%s' nije cijeli broj.%n", scannerInput);
				continue;
			}
			
			if(number < 3 || number > 20) {
				System.out.format("'%s' nije broj u dozvoljenom rasponu.%n", scannerInput);
			}
			else {
				System.out.format("%s! = %d%n", scannerInput, factorial(number));
			}
		}
	}
	
	/**
	 * The method that calculates the factorial of a number.
	 * 
	 * @param number The number whose factorial needs to be calculated.
	 * @return The factorial value of the given number.
	 * @exception IllegalArgumentException The exception is thrown when the given number is less than 0.
	 */
	public static long factorial(int number) {
		if(number < 0) {
			throw new IllegalArgumentException("Can not calculate factorial of a negative number.");
		}
		long factorial = 1;
		while(number > 1) {
			factorial *= number;
			number--;
		}
		return factorial;
	}
	
}
