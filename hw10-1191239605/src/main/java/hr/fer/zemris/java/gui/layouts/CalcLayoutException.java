package hr.fer.zemris.java.gui.layouts;

/**
 *	An exception that is thrown when there is the
 *	{@link CalcLayout} encounters unexpected behaviour.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 	Constructs a new CalcLayoutException.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * 	Constructs a new CalcLayoutException.
	 * 	@param message Message to show.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
}
