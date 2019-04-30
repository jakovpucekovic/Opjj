package coloring.algorithms;

import java.util.Objects;

/**
 *	Pixel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Pixel {

	public int x;
	public int y;
	
	
	
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
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
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	
	
}
