package hr.fer.zemris.math;

import java.lang.Math;
import java.util.Objects;

/**
 *	Class which represents a vector in 2 dimensions.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class Vector2D {
	
	/**The x coordinate of the {@link Vector2D}.*/
	private double x;
	/**The y coordinate of the {@link Vector2D}.*/
	private double y;
	/**Used for comparing numbers, if their difference is smaller that this,
	 * they are considered equal.*/
	private static final double EPSILON = 1E-8;
	
	
	/**
	 * 	Constructs a new {@link Vector2D} with the given coordinates.
	 * 	@param x The x coordinate.
	 * 	@param y The y coordinate.
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * 	Returns the x coordinate of the {@link Vector2D}.
	 * 	@return The x coordinate of the {@link Vector2D}. 
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * 	Returns the y coordinate of the {@link Vector2D}.
	 * 	@return The y coordinate of the {@link Vector2D}. 
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * 	Translates the {@link Vector2D} for the given offset.
	 * 	@param offset The offset for which the {@link Vector2D} should be translated.
	 */
	public void translate(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}
	
	/**
	 * 	Returns a new {@link Vector2D} created by translating the {@link Vector2D} for the given offset.
	 * 	@param offset The offset for which the {@link Vector2D} should be translated.
	 *	@return The translated {@link Vector2D}.
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.x, y + offset.y);
	}
	
	/**
	 * 	Rotates the {@link Vector2D} for the given angle.
	 * 	@param angle The angle for which the {@link Vector2D} should be rotated.
	 */
	public void rotate(double angle) {
		double newX = x * Math.cos(angle) - y * Math.sin(angle);
		double newY = x * Math.sin(angle) + y * Math.cos(angle);
		x = newX;
		y = newY;
	}
	
	/**
	 * 	Returns a new {@link Vector2D} created by rotating the {@link Vector2D} for the given angle.
	 * 	@param angle The angle for which the {@link Vector2D} should be rotated.
	 *	@return The rotated {@link Vector2D}.
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}
	
	/**
	 * 	Scales the {@link Vector2D} for the given scaler.
	 * 	@param scaler The scaler for which the {@link Vector2D} should be scaled.
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * 	Returns a new {@link Vector2D} created by scaling the {@link Vector2D} for the given scaler.
	 * 	@param scaler The scaler for which the {@link Vector2D} should be scaler.
	 *	@return The scaled {@link Vector2D}.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}
	
	/**
	 * 	Returns a copy of the {@link Vector2D}.
	 *	@return The copy of the {@link Vector2D}.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	/**
	 * 	Returns the hashCode of this {@link Vector2D}.
	 * 	@return The hashCode of this {@link Vector2D}.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 *	Indicates whether some other object is "equal to" this one.
	 *	The object is considered equal if it's an instance of {@link Vector2D}
	 *	and the differences between the x and y coordinates of this {@link Vector2D}
	 *	and the x and y coordinates of the given object are smaller than EPSILON.
	 *	@param obj Object which should be compared to this {@link Vector2D}.
	 *	@return <code>true</code> if the object is equal, <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		return Math.abs(x - other.x) < EPSILON && Math.abs(y - other.y) < EPSILON;
	}
	
	
}
