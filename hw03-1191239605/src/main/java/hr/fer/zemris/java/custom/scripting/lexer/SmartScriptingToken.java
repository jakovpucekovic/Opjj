package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *	Class SmartScriptingToken...
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptingToken {

	/**	
	 * The type of the {@link SmartScriptingToken}. Can be EOF, WORD, NUMBER
	 * 	or SYMBOL.
	 */
	private SmartScriptingTokenType type;
	
	/**
	 * 	The value of the {@link SmartScriptingToken}.
	 */
	private Object value;
	
	/**
	 *	Constructs a {@link SmartScriptingToken} of the given type
	 *	and assigns it the given value.
	 *	@param type Type of the {@link SmartScriptingToken}.
	 *	@param value Value to give the {@link SmartScriptingToken}.
	 */
	public SmartScriptingToken(SmartScriptingTokenType type, Object value) {
//dodati provjeru da value odgovara type
		this.type = type;
		this.value = value;
	}

	/**
	 * 	Returns the type of the {@link SmartScriptingToken}.
	 * 	@return Type of the {@link SmartScriptingToken}.
	 */
	public SmartScriptingTokenType getType() {
		return type;
	}

	/**
	 * 	Returns the value of the {@link SmartScriptingToken}.
	 *	@return Value of the {@link SmartScriptingToken}.
	 */
	public Object getValue() {
		return value;
	}

	
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
	
	
}


