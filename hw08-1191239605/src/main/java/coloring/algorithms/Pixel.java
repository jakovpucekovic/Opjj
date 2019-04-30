package coloring.algorithms;

import marcupic.opjj.statespace.coloring.Picture;

/**
 *	Class which represents a pixel in the {@link Picture}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Pixel {

	/**X coordinate*/
	public int x;
	
	/**Y coordinate*/
	public int y;
	
	/**
	 * 	Constructs a new {@link Pixel} with the given coordinates.
	 *	@param x The x-coordinate.
	 *	@param y The y-coordinate.  
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
	/**
	 *	Returns a {@link String} representation of this 
	 *	{@link Pixel} if format "(x,y)".
	 *	@return {@link String} representation of this {@link Pixel}.
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
