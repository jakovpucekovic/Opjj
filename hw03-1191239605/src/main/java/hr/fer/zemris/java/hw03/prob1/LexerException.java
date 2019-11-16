package hr.fer.zemris.java.hw03.prob1;

/**
 *	Class which describes the exception which
 *	is thrown when the {@link Lexer} encounters
 *	unexpected behavior. 
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class LexerException extends RuntimeException {
		
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new {@link LexerException} with {@code null}
	 *  as its detail message.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * 	Constructs a new {@link LexerException} with given message.
	 * 	@param message The detail message.
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 *	Constructs a new {@link LexerException} with given message.
	 * 	@param cause The cause for the exception.
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 	Constructs a new {@link LexerException} with given message.
	 * 	@param message The detail message.
	 * 	@param cause The cause for the exception.
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
	}