package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *	Class SmartScriptToken which represents tokens to
 *	which the {@link SmartScriptLexer} groups the input.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptToken {

	/**	
	 * The type of the {@link SmartScriptToken}.
	 */
	private SmartScriptTokenType type;
	
	/**
	 * 	The value of the {@link SmartScriptToken}.
	 */
	private Object value;
	
	/**
	 *	Constructs a {@link SmartScriptToken} of the given type
	 *	and assigns it the given value.
	 *	@param type Type of the {@link SmartScriptToken}.
	 *	@param value Value to give the {@link SmartScriptToken}.
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * 	Returns the type of the {@link SmartScriptToken}.
	 * 	@return Type of the {@link SmartScriptToken}.
	 */
	public SmartScriptTokenType getType() {
		return type;
	}

	/**
	 * 	Returns the value of the {@link SmartScriptToken}.
	 *	@return Value of the {@link SmartScriptToken}.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
	
}


