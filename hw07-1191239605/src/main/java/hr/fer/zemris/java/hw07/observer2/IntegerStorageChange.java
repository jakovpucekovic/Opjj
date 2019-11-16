package hr.fer.zemris.java.hw07.observer2;

/**
 *	Class IntegerStorageChange which models a change in class
 *	{@link IntegerStorage}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class IntegerStorageChange {

	/**{@link IntegerStorage} which created this {@link IntegerStorageChange}.*/
	private IntegerStorage istorage;
	/**Value before the change*/
	private int beforeChange;
	/**Value after the change*/
	private int afterChange;
	
	/**
	 * 	Constructs a new IntegerStorageChange with the given values.
	 * 	@param istorage {@link IntegerStorage} which created this change.
	 * 	@param beforeChange Old value.
	 * 	@param afterChange New value.
	 */
	public IntegerStorageChange(IntegerStorage istorage, int beforeChange, int afterChange) {
		this.istorage = istorage;
		this.beforeChange = beforeChange;
		this.afterChange = afterChange;
	}
	/**
	 * 	Returns the {@link IntegerStorage} of the IntegerStorageChange.
	 * 	@return the {@link IntegerStorage} of the IntegerStorageChange.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	/**
	 * 	Returns the value before the change.
	 * 	@return the value before the change.
	 */
	public int getBeforeChange() {
		return beforeChange;
	}
	/**
	 * 	Returns the value after the change.
	 * 	@return the value after the change.
	 */
	public int getAfterChange() {
		return afterChange;
	}

}
