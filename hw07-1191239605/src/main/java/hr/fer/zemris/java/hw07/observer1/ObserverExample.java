package hr.fer.zemris.java.hw07.observer1;

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
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);	//	Provided new value: 5, square is 25
		istorage.setValue(2);	//	Provided new value: 2, square is 4
		istorage.setValue(25);	//	Provided new value: 25, square is 625
			
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		
		istorage.setValue(13);	//	Number of value changes since tracking: 1
								//	Double value: 26
								//	Double value: 26
								//	Double value: 26
		istorage.setValue(22);	//	Number of value changes since tracking: 2
								//	Double value: 44
								//	Double value: 44
		istorage.setValue(15);	//	Number of value changes since tracking: 3
								
	}
	
}
