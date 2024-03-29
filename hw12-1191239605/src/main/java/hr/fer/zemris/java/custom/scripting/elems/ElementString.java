package hr.fer.zemris.java.custom.scripting.elems;

/**
*	Class which is used to represent a string.
* 	@author Jakov Pucekovic
* 	@version 1.0
*/
public class ElementString extends Element {

	/**
	 *	The string is stored here.
	 */
	private String value;

	/**
	 *	Constructs a new {@link ElementString}
	 *	and stores the given value.
	 *	@param value Value to store.
	 */	
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 *	Returns the stored string.
	 *	@return The stored string.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 *	Returns the stored string as text.
	 *	@return Stored string as text.
	 */
	@Override
	public String asText() {
		if(value.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append('\"');
		builder.append(value.replace("\\", "\\\\").replace("\"", "\\\""));
		builder.append('\"');

		return builder.toString();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return value;
	}
}
