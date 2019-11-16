package hr.fer.zemris.java.custom.scripting.elems;

/**
*	Class which is used to represent an integer.
* 	@author Jakov Pucekovic
* 	@version 1.0
*/
public class ElementConstantInteger extends Element {

	/**
	 *	The value of the integer is stored here.
	 */
	private int value;

	/**
	 *	Constructs a new {@link ElementConstantInteger}
	 *	and stores the given value.
	 *	@param value Value to store.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 *	Returns the stored value.
	 *	@return The stored value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 *	Returns the stored expression as string.
	 *	@return Stored value as string.
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
}
