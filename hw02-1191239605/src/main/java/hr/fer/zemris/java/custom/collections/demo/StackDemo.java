package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 *	Class which enables the user to calculate an expression in
 *	postfix notation.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class StackDemo {

	/**
	 * 	Method that starts the application, evaluates the expression
	 * 	and writes the user the appropriate message.
	 * 
	 * 	@param args The expression in postfix notation inside of
	 * 				double quotations (" ").
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Input should be written in \" \" and in postfix notation.");
			return;
		}
		
		ObjectStack stack = new ObjectStack();

		String[] str = args[0].split(" +");
		for(int i = 0; i < str.length; i++) {
			try {
				int n = Integer.parseInt(str[i]);
				stack.push(n);
			} catch(NumberFormatException ex1) {
				int first, second;
				try {
					second = (int)stack.pop();
					first = (int)stack.pop();
					stack.push(calculate(first, second, str[i]));
				} catch(EmptyStackException ex2){
					System.out.println("The expression can't be evaluated.");
					return;
				} catch(IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
		}
		if(stack.size() != 1) {
			System.out.println("The expression can't be evaluated.");
		} else {
			System.out.format("The expression evaluates to %d.%n", stack.peek());
		}
	}
	
	private static int calculate(int a, int b, String operator) {
		switch(operator) {
			case ("+"): { 
				return a+b;
			}
			case("-"): {
				return a-b;
			}
			case("*"): {
				return a*b;
			}
			case("/"): {
				if(b == 0) {
					throw new IllegalArgumentException("Division by zero occured.");
				}
				return a/b;
			}
			case("%"):{
				if(b == 0) {
					throw new IllegalArgumentException("Division by zero occured.");
				}
				return a%b;
			}
			default:{
				throw new IllegalArgumentException("Invalid operator in expression.");
			}
		}
	}
}
