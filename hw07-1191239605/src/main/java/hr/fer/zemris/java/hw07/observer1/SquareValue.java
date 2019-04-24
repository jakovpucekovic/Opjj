package hr.fer.zemris.java.hw07.observer1;

/**
 *	Class SquareValue which implements {@link IntegerStorageObserver} 
 *	and prints the square value when the value in {@link IntegerStorage}
 *	changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SquareValue implements IntegerStorageObserver {

	/**
	 * 	Method which prints the square value when the value in 
	 * 	the observed {@link IntegerStorage} is changed.
	 *	@param istorage {@link IntegerStorage} which is observed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Provided new value: %d, square is %d%n", istorage.getValue(), istorage.getValue()*istorage.getValue());
	}

}
