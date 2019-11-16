package hr.fer.zemris.java.hw05.db.parser.utils;

/**
 * 	Class which describes the exception which
 *	is thrown when {@link QueryParser}
 *	encounters unexpected behavior.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class QueryParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 *	Constructs a new {@link QueryParserException} with {@code null}
	 *	as its detail message.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 *	Constructs a new {@link QueryParserException} with the given 
	 *	message.
	 *	@param message The detail message. 
	 */
	public QueryParserException(String message) {
		super(message);
	}
}