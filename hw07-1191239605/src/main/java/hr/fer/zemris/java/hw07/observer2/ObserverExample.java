package hr.fer.zemris.java.hw07.observer2;

/**
 *	Class ObserverExample which is an example 
 *	of using the classes in this package. 
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class ObserverExample {

	/**
	 * 	Example method.
	 * 	@param args No arguments.
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);				
		istorage.setValue(13);												
		istorage.setValue(22);				
		istorage.setValue(15);
								
	}
	
}
