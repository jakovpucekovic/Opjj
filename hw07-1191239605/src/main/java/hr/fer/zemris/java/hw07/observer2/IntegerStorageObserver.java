package hr.fer.zemris.java.hw07.observer2;

/**
 *	IntegerStorageObserver is an interface which observes {@link IntegerStorage}
 *	and does stuff when the value in {@link IntegerStorage} changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface IntegerStorageObserver {

	/**
	 * 	Method which does something when the value in 
	 * 	the observed {@link IntegerStorage} is changed.
	 *	@param istorageChange {@link IntegerStorageChange} that happened.
	 */
	void valueChanged(IntegerStorageChange istorageChange);

}
