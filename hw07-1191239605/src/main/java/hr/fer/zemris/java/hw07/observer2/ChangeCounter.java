package hr.fer.zemris.java.hw07.observer2;

/**
 *	Class ChangeCounter which implements {@link IntegerStorageObserver} 
 *	and prints the number of times the value in {@link IntegerStorage}
 *	was changed.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class ChangeCounter implements IntegerStorageObserver {

	/**Counts how many times the value has changed.*/
	private int counter;
	
	/**
	 * 	Constructs a new ChangeCounter.
	 */
	public ChangeCounter() {
		counter = 0;
	}
	
	/**
	 * 	Method which prints the number of times the value in 
	 * 	the observed {@link IntegerStorage} was changed.
	 *	@param istorageChange {@link IntegerStorageChange} that happened.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		++counter;
		System.out.printf("Number of value changes since tracking: %d%n", counter);
	}

}
