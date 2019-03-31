package hr.fer.zemris.java.custom.scripting.elems;

/**
*	Class which is used to represent a double.
* 	@author Jakov Pucekovic
* 	@version 1.0
*/
public class ElementConstantDouble extends Element {

	/**
	 *	The value of the double is stored here.
	 */
	private double value;

	/**
	 *	Constructs a new {@link ElementConstantDouble}
	 *	and stores the given value.
	 *	@param value Value to store.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 *	Returns the stored value.
	 *	@return The stored value.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 *	Returns the stored value as string.
	 *	@return Stored value as string.
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
}
