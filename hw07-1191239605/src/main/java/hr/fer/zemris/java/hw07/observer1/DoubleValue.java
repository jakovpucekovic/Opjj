package hr.fer.zemris.java.hw07.observer1;

/**
 *	Class DoubleValue which implements {@link IntegerStorageObserver} 
 *	and prints double the value when the value in {@link IntegerStorage}
 *	changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class DoubleValue implements IntegerStorageObserver {

	/**Counts how many more times it should observe before stopping.*/
	int counter;
	
	/**
	 * 	Constructs a new DoubleValue that will observe the {@link IntegerStorage}
	 * 	the given amount of times.
	 * 	@param counter For how many changes should the {@link IntegerStorage} be observed. 
	 */
	public DoubleValue(int counter) {
		this.counter = counter;
	}
	
	/**
	 *	Method which prints double the value when the value in 
	 * 	the observed {@link IntegerStorage} is changed. Also, it stops
	 * 	observing the storage when the counter reaches zero.
	 *	@param istorage {@link IntegerStorage} which is observed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(counter > 0) {
			System.out.printf("Double value: %d%n", 2 * istorage.getValue());
			--counter;
		} else {
			istorage.removeObserver(this);
		}
	}

}
