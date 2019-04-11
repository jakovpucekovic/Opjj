package hr.fer.zemris.java.hw05.db.parser.utils;

/**
 * 	Class which describes the exception which
 *	is thrown when {@link QueryLexer}
 *	encounters unexpected behavior.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class QueryLexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 *	Constructs a new {@link QueryLexerException} with {@code null}
	 *	as its detail message.
	 */
	public QueryLexerException() {
		super();
	}

	/**
	 *	Constructs a new {@link QueryLexerException} with the given 
	 *	message.
	 *	@param message The detail message. 
	 */
	public QueryLexerException(String message) {
		super(message);
	}
}