package hr.fer.zemris.java.custom.scripting.parser;

/**
 *	Class which describes the exception which
 *	is thrown when ... 
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptingParserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 	Constructs a new {@link SmartScriptingParserException} with {@code null}
	 *  as its detail message.
	 */
	public SmartScriptingParserException() {
		super();
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptingParserException} with given message.
	 * 	@param message The detail message.
	 */
	public SmartScriptingParserException(String message) {
		super(message);
	}
	
	/**
	 *	Constructs a new {@link SmartScriptingParserException} with given message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptingParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptingParserException} with given message.
	 * 	@param message The detail message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptingParserException(String message, Throwable cause) {
		super(message, cause);
	}
}