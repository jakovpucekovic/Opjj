package hr.fer.zemris.java.gui.charts;

/**
 *	XYValue TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class XYValue {

	/**X value*/
	private int x;
	
	/**Y value*/
	private int y;
	
	/**
	 *	Constructs a new {@link XYValue} from the given {@link String}.
	 *	@param str String in format "x,y".
	 *	@throws IllegalArgumentException If the string doesn't contain
	 *									 a valid {@link XYValue}.
	 */
	public XYValue(String str) {
		try {
			x = Integer.parseInt(str.substring(0, str.indexOf(',')));
			y = Integer.parseInt(str.substring(str.indexOf(',') + 1));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("String doesn't contain a valid position.");
		}
	}
	
	/**
	 * 	Constructs a new {@link XYValue} with the given parameters.
	 * 	@param x X value.
	 * 	@param y Y value.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 	Returns the x of the {@link XYValue}.
	 * 	@return the x of the {@link XYValue}.
	 */
	public int getX() {
		return x;
	}

	/**
	 * 	Returns the y of the {@link XYValue}.
	 * 	@return the y of the {@link XYValue}.
	 */
	public int getY() {
		return y;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
