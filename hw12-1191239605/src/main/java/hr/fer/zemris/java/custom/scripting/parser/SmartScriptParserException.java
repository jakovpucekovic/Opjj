package hr.fer.zemris.java.custom.scripting.parser;

/**
 *	Class which describes the exception which
 *	is thrown when {@link SmartScriptParser} encounters
 *	unexpected behavior.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptParserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 	Constructs a new {@link SmartScriptParserException} with {@code null}
	 *  as its detail message.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptParserException} with given message.
	 * 	@param message The detail message.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 *	Constructs a new {@link SmartScriptParserException} with given message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptParserException} with given message.
	 * 	@param message The detail message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}