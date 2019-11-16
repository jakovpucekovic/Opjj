package hr.fer.zemris.java.custom.scripting.elems;

/**
*	Class which is used to represent a variable.
* 	@author Jakov Pucekovic
* 	@version 1.0
*/
public class ElementVarible extends Element {
	
	/**
	 *	The name is stored here.
	 */
	private String name;

	/**
	 *	Constructs a new {@link ElementVarible}
	 *	and stores the given name.
	 *	@param name Name to store.
	 */
	public ElementVarible(String name) {
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
	 *	Returns the stored name as string.
	 *	@return Stored name as string.
	 */
	@Override
	public String asText() {
		return name;
	}
	
}
