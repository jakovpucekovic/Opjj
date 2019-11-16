package hr.fer.zemris.java.raytracer.model;

/**
 * <p>This class represents a ray. A ray is half-line. It is
 * a segment of line that starts in point {@link #start}
 * and contains all points obtainable by equation
 * <code>p(lambda) = start + lambda * direction</code>
 * for <code>lambda &gt;= 0</code>.</p>
 * 
 * <p>It is expected for a directional vector {@link #direction} to be
 * normalized. However, due to cost of checking if this is true, this
 * is not checked by constructor. However, please be aware that if you
 * do not provide normalized directional vector, other operations that
 * depend on this fact will break.</p>
 * 
 * @author marcupic
 *
 */
public class Ray {

	/**
	 * Starting point of ray
	 */
	public Point3D start;
	/**
	 * Directional normalized vector of ray 
	 */
	public Point3D direction;
	
	/**
	 * Constructor.
	 * 
	 * @param start starting point for ray
	 * @param direction directional normalized vector for ray
	 */
	public Ray(Point3D start, Point3D direction) {
		super();
		this.start = start;
		this.direction = direction;
	}
	
	/**
	 * Builds a ray object from start point to end point by calculating
	 * and normalizing directional vector.
	 * 
	 * @param startPoint start point for ray
	 * @param endPoint end point for ray
	 * @return ray object
	 */
	public static Ray fromPoints(Point3D startPoint, Point3D endPoint) {
		return new Ray(startPoint, endPoint.sub(startPoint).modifyNormalize());
	}
	
}
