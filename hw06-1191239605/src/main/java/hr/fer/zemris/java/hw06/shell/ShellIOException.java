package hr.fer.zemris.java.hw06.shell;

/**
 *	Class ShellIOException which represents an exception which is throw
 *	when {@link MyShell} has problems with input or output of data.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 *  {@inheritDoc}
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 	{@inheritDoc}
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 	{@inheritDoc}
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * 	{@inheritDoc}
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	
	
}
