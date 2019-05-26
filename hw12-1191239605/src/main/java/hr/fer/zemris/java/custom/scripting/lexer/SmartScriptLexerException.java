package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *	Class which describes the exception which
 *	is thrown when {@link SmartScriptLexer}
 *	encounters unexpected behavior.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptLexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new {@link SmartScriptLexerException} with {@code null}
	 *  as its detail message.
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptLexerException} with given message.
	 * 	@param message The detail message.
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
	
	/**
	 *	Constructs a new {@link SmartScriptLexerException} with given message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 	Constructs a new {@link SmartScriptLexerException} with given message.
	 * 	@param message The detail message.
	 * 	@param cause The cause for the exception.
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}