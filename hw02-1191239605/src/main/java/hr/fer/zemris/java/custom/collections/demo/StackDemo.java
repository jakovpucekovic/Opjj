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
				} catch(EmptyStackException ex2){
					System.out.println("The expression can't be evaluated.");
					return;
				}
				switch(str[i]) {
					case ("+"): { 
						stack.push(first + second);
						break;
					}
					case("-"): {
						stack.push(first - second);
						break;
					}
					case("*"): {
						stack.push(first * second);
						break;
					}
					case("/"): {
						if(second == 0) {
							System.out.println("Error. Division by zero has occured.");
							return;
						}
						stack.push(first / second );
						break;
					}
					case("%"):{
						if(second == 0) {
							System.out.println("Error. Division by zero has occured.");
							return;
						}
						stack.push(first % second);
						break;
					}
					default:{
						System.out.println("Wrong input.");
					}
				}
			}
		}
		if(stack.size() != 1) {
			System.out.println("The expression can't be evaluated.");
		} else {
			System.out.format("The expression evaluates to %d.%n", stack.peek());
		}
	}
}
