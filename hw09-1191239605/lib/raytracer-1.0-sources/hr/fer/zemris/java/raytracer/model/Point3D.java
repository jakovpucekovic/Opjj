package hr.fer.zemris.java.raytracer.model;

/**
 * <p>A model for 3D point. This model will be interchangeably be used either
 * for 3D-point representation or for 3D-vector representation since each
 * point can be observed as appropriate vector from center of coordinate system.</p>
 * 
 * <p>You are provided with two types of methods that operate on instances of
 * this class. Methods whose name starts with <b>modify</b> (such as 
 * {@link #modifyAdd(Point3D)}, {@link #modifyNormalize()} etc. directly
 * modify current instance which can speed up many operations when you do not
 * wish to create temporary intermediate objects. However, this can be problematic
 * if you also use/store the same point objects where they should not change. In than case
 * you are strongly encouriged to use methods whose name does not start with <b>modify</b>
 * and which, hence, do not modify original object but instead create a new one which
 * holds the operation result (such as {@link Point3D#add(Point3D)}, {@link Point3D#difference(Point3D, Point3D)},
 * etc).</p>
 * 
 * @author marcupic
 *
 */
public class Point3D {

	/** Point/vector x-component */
	public double x;
	/**  Point/vector y-component */
	public double y;
	/** Point/vector z-component */
	public double z;
	
	/**
	 * Constructor for (0,0,0).
	 */
	public Point3D() {
	}
	
	/**
	 * Constructor for arbitrary point/vector.
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param z z-coordinate
	 */
	public Point3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns new copy of this point.
	 * 
	 * @return copy of point
	 */
	public Point3D copy() {
		return new Point3D(x,y,z);
	}
	
	/**
	 * Returns vector a-b.
	 * 
	 * @param a first point
	 * @param b second point
	 * @return new vector first-second
	 */
	public Point3D difference(Point3D a, Point3D b) {
		return new Point3D(a.x-b.x, a.y-b.y, a.z-b.z);
	}
	
	/**
	 * Calculates norm of point when observed as vector.
	 * 
	 * @return norm
	 */
	public double norm() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	/**
	 * Translates this point for vector p. Warning: this method modifies current object! 
	 * 
	 * @param p translation vector
	 * @return current point after modification
	 */
	public Point3D modifyAdd(Point3D p) {
		x += p.x;
		y += p.y;
		z += p.z;
		return this;
	}
	
	/**
	 * Translates this point for vector (x,y,z). Warning: this method modifies current object!
	 * 
	 * @param x x-component for translation
	 * @param y y-component for translation
	 * @param z z-component for translation
	 * @return current point after modification
	 */
	public Point3D modifyAdd(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Returns new point that is equals to current translated by given vector.
	 * 
	 * @param p translation vector
	 * @return translated point
	 */
	public Point3D add(Point3D p) {
		return copy().modifyAdd(p);
	}
	
	/**
	 * Returns new point that is equals to current translated by vector (x,y,z).
	 * 
	 * @param x x-component for translation
	 * @param y y-component for translation
	 * @param z z-component for translation
	 * @return translated point
	 */
	public Point3D add(double x, double y, double z) {
		return copy().modifyAdd(x, y, z);
	}

	/**
	 * Translates this point for vector -p. Warning: this method modifies current object! 
	 * 
	 * @param p translation vector
	 * @return current point after modification
	 */
	public Point3D modifySub(Point3D p) {
		x -= p.x;
		y -= p.y;
		z -= p.z;
		return this;
	}
	
	/**
	 * Translates this point for vector (-x,-y,-z). Warning: this method modifies current object! 
	 * 
	 * @param x x-component for translation
	 * @param y y-component for translation
	 * @param z z-component for translation
	 * @return current point after modification
	 */
	public Point3D modifySub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	/**
	 * Returns new point that is equals to current translated by negation of given vector.
	 * 
	 * @param p translation vector
	 * @return translated point
	 */
	public Point3D sub(Point3D p) {
		return copy().modifySub(p);
	}
	
	/**
	 * Returns new point that is equals to current translated by vector (-x,-y,-z).
	 * 
	 * @param x x-component for translation
	 * @param y y-component for translation
	 * @param z z-component for translation
	 * @return translated point
	 */
	public Point3D sub(double x, double y, double z) {
		return copy().modifySub(x, y, z);
	}

	/**
	 * Returns nagation of this vector, i.e. (-x, -y, -z).
	 * 
	 * @return negated vector
	 */
	public Point3D negate() {
		return new Point3D(-x, -y, -z);
	}
	
	/**
	 * Calculates scalar product this * p.
	 * 
	 * @param p other vector
	 * @return scalar product
	 */
	public double scalarProduct(Point3D p) {
		return x*p.x+y*p.y+z*p.z;
	}
	
	/**
	 * Returns new point/vector that is equal to this multiplied by
	 * given scalar value.
	 * 
	 * @param scalar scalar value to multiply with
	 * @return new multiplied point
	 */
	public Point3D scalarMultiply(double scalar) {
		return new Point3D(scalar*x, scalar*y, scalar*z);
	}

	/**
	 * Normalizes current vector. Warning: this call modifies current vector.
	 * 
	 * @return reference to current vector
	 */
	public Point3D modifyNormalize() {
		double n = norm();
		if(n<1E-9) {
			x = 0; y = 0; z = 0;
		} else {
			x /= n;
			y /= n;
			z /= n;
		}
		return this;
	}
	
	/**
	 * Returns a new vector that is normalized version of current vector.
	 * 
	 * @return normalized version of vector
	 */
	public Point3D normalize() {
		return this.copy().modifyNormalize();
	}
	
	/**
	 * Returns vector that is calculated as <i>vector-product</i> this * p.
	 * 
	 * @param p other vector
	 * @return vector product of this vector and given vector
	 */
	public Point3D vectorProduct(Point3D p) {
		return new Point3D(
			this.y*p.z-this.z*p.y,
			-(this.x*p.z-this.z*p.x),
			this.x*p.y-this.y*p.x
		);
	}
}
