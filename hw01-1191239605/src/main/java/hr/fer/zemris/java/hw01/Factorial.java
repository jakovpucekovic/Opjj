package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Opis paketa
 * 
 * @author Jakov Pucekovic
 * @version 1.0.0
 */

public class Factorial {

	public static void main(String ... args) {
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
	
	public static long factorial(int number) {
		long factorial = 1;
		while(number > 1) {
			factorial *= number;
			if(factorial < 1) {
				throw new IllegalArgumentException("Overflow happened");
			}
			number--;
		}
		return factorial;
	}
	
	
}
