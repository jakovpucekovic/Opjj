package hr.fer.zemris.math;

/**
 *	Class which models a vector in 3 dimensions.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Vector3 {
	
	/**X coordinate*/
	private double x;
	
	/**Y coordinate*/
	private double y;
	
	/**Z coordinate*/
	private double z;
	
	/**
	 *	Constructs a new {@link Vector3} with given coordinates.
	 *	@param x X coordinate. 
	 *	@param y Y coordinate.
	 *	@param z Z coordinate.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 *	Returns the norm of this {@link Vector3}.
	 *	@return The norm of this {@link Vector3}. 
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * 	Returns a new {@link Vector3} which is this {@link Vector3}
	 * 	but normalized.
	 * 	@return A new {@link Vector3} which is this {@link Vector3}
	 * 			but normalized.
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x/norm, y/norm, z/norm);
	}
	
	/**
	 * 	Adds another {@link Vector3} to this {@link Vector3}.
	 * 	@param other The {@link Vector3} to add to this {@link Vector3}.
	 * 	@return A new {@link Vector3} containing the sum.
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * 	Subtracts the given {@link Vector3} from this {@link Vector3}.
	 * 	@param other The {@link Vector3} to subtract from this {@link Vector3}.
	 * 	@return A new {@link Vector3} which contains the difference.
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * 	Returns the scalar product of this {@link Vector3} with
	 * 	the given {@link Vector3}.
	 * 	@param other The {@link Vector3} to calculate the scalar product with.
	 * 	@return The scalar product.
	 */
	public double dot(Vector3 other) {
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * 	Returns the cross product of this {@link Vector3} with 
	 * 	the given {@link Vector3}.
	 * 	@param other The {@link Vector3} to calculate the cross product with.
	 * 	@return The cross product.
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(y*other.z - z*other.y,
						   x*other.z - z*other.x,
						   x*other.y - y*other.x);
	}

	/**
	 * 	Returns a new {@link Vector3} which is this {@link Vector3}
	 * 	but scaled.
	 * 	@param s Value to scale this {@link Vector3} with.
	 * 	@return A new scaled {@link Vector3}.
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * 	Returns the cosine of the angle between this {@link Vector3}
	 * 	and the given {@link Vector3}.
	 * 	@param other The other {@link Vector3}.
	 * 	@return The cosine of the angle.
	 */
	public double cosAngle(Vector3 other) {
		return dot(other)/(norm() * other.norm());
	}

	/**
	 * 	Returns the x coordinate of the {@link Vector3}.
	 * 	@return the x coordinate of the {@link Vector3}.
	 */
	public double getX() {
		return x;
	}

	/**
	 * 	Returns the y coordinate of the {@link Vector3}.
	 * 	@return the y coordinate of the {@link Vector3}.
	 */
	public double getY() {
		return y;
	}

	/**
	 * 	Returns the z coordinate of the {@link Vector3}.
	 * 	@return the z coordinate of the {@link Vector3}.
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * 	Returns an array which contains the coordinates.
	 * 	@return An array which contains the coordinates.
	 */
	public double[] toArray() {
		double[] array = {x,y,z};
		return array;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("(%6f, %6f, %6f)",x, y, z);
	}
	
}
