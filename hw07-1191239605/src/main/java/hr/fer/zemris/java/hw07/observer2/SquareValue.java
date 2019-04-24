package hr.fer.zemris.java.hw07.observer2;

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
	 *	@param istorageChange {@link IntegerStorageChange} that happened.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.printf("Provided new value: %d, square is %d%n", istorageChange.getAfterChange(), istorageChange.getAfterChange()*istorageChange.getAfterChange());
	}

}
