package hr.fer.zemris.java.custom.scripting.elems;

/**
*	Class which is used to represent an operator.
* 	@author Jakov Pucekovic
* 	@version 1.0
*/
public class ElementOperator extends Element {

	/**
	 *	The symbol is stored here.
	 */
	private String symbol;

	/**
	 *	Constructs a new {@link ElementOperator}
	 *	and stores the given symbol.
	 *	@param symbol Symbol to store.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 *	Returns the stored symbol.
	 *	@return The stored symbol.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 *	Returns the stored symbol as string.
	 *	@return Stored symbol as string.
	 */
	@Override
	public String asText() {
		return symbol;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return symbol;
	}
	
}
