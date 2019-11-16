package hr.fer.zemris.java.custom.scripting.elems;

/**
*	Class which is used to represent an function.
* 	@author Jakov Pucekovic
* 	@version 1.0
*/
public class ElementFunction extends Element {

	/**
	 *	The name is stored here.
	 */
	private String name;

	/**
	 *	Constructs a new {@link ElementFunction}
	 *	and stores the given name.
	 *	@param name Name to store.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 *	Returns the stored name.
	 *	@return The stored name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *	Returns the stored expression as text.
	 *	@return Stored value as text.
	 */
	@Override
	public String asText() {
		return name;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return name;
	}
	
}
