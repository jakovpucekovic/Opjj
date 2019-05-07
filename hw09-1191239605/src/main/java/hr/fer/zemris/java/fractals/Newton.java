package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.math.Complex;

/**
 *	Newton TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done");

		List<Complex> roots = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int i = 0;
		System.out.printf("Root %d> ", i);
		while(sc.hasNext()) {
			String input = sc.nextLine();//TODO popravi da ne prihvaca prazan input
			if(input.equals("done")) {
				break;
			}			
			try {
				roots.add(parse(input));
			} catch(NumberFormatException ex) {
				System.out.println("Given argument is not a complex number.");
				System.out.printf("Root %d> ", i);
				continue;
			}
			System.out.printf("Root %d> ", ++i);
			
		}
		roots.forEach(System.out::println);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		sc.close();
	}
	
	
	
	public static Complex parse(String input) {
		input = input.replaceAll("\\s+", "");
		int plus = input.lastIndexOf('+');
		int minus = input.lastIndexOf('-');
		if(plus > 0) {
			if(plus + 2 < input.length()) {
				return new Complex(Double.parseDouble(input.substring(0, plus)),
								   Double.parseDouble(input.substring(plus + 2)) );
			}
			return new Complex(Double.parseDouble(input.substring(0, plus)), 1);
		}
		if(minus > 0) {
			if(minus + 2 < input.length()) {
				return new Complex(Double.parseDouble(input.substring(0, minus)),
								  -Double.parseDouble(input.substring(minus + 2)) );
			}
			return new Complex(Double.parseDouble(input.substring(0, minus)), -1);
		}
		if(input.equals("i")) {
			return Complex.IM;
		}
		if(input.equals("-i")) {
			return Complex.IM_NEG;
		}
		if(input.indexOf('i')!= -1) {
			return new Complex(0, Double.parseDouble(input.substring(input.indexOf('i')+1)));
		}
		return new Complex(Double.parseDouble(input), 0);
	}
	
}
