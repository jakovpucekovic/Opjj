package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *	Class which describes the exception which
 *	is thrown when ... 
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptingLexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new {@link SmartScriptingLexerException} with {@code null}
	 *  as its detail message.
	 */
	public SmartScriptingLexerException() {
		super();
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptingLexerException} with given message.
	 * 	@param message The detail message.
	 */
	public SmartScriptingLexerException(String message) {
		super(message);
	}
	
	/**
	 *	Constructs a new {@link SmartScriptingLexerException} with given message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptingLexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptingLexerException} with given message.
	 * 	@param message The detail message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptingLexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}