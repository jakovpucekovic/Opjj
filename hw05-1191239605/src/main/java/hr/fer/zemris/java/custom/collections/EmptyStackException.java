package hr.fer.zemris.java.custom.collections;

/**
 *	Class which describes the exception which
 *	is thrown when a method that needs 
 *	a non-empty stack(e.g. pop, peek) gets an
 *	empty stack. 
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 	Constructs a new {@link EmptyStackException} with {@code null}
	 *  as its detail message.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * 	Constructs a new {@link EmptyStackException} with given message.
	 * 	@param message The detail message.
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 *	Constructs a new {@link EmptyStackException} with given message.
	 * 	@param cause The cause for the exception.
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 	Constructs a new {@link EmptyStackException} with given message.
	 * 	@param message The detail message.
	 * 	@param cause The cause for the exception.
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}