package hr.fer.zemris.java.raytracer.model;

/**
 * <p>Instances of this class represent intersections of ray and some object.</p>
 * <p>The point where ray and object intersected is given by {@link #point}.</p>
 * <p>Intersection points can be inner and outer. Consider intersections of a ray
 * that starts in point (10,0,0) and has directional vector (-1,0,0) and a
 * sphere with center (0,0,0) and radius 1. Our ray will have two intersections:
 * in point (1,0,0) ray will enter into the sphere, so this intersection is outer;
 * it represents a point in which we pass from exterior into the sphere itself.
 * The other intersection point is (-1,0,0) and that is inner intersection; in that point
 * our ray passes from sphere interior into external space.</p>
 * <p>For each intersection point it is expected that a normal to object surface can be
 * obtained. For example, for spheres this is trivial: if point <code>p</code> is point
 * on sphere surface and if sphere is centered in point <code>c</code>, then a normal
 * in point <code>p</code> is <code>p-c</code>; it is a vector that is perpendicular to
 * sphere surface in point <code>p</code> and is directed toward outside of sphere.</p>
 * <p>Parameter {@link #distance} is euclidian distance between between ray start point
 * and the intersection. In our previous case the distance for the outer intersection would
 * be 9 (<code>||(10,0,0)-(1,0,0)||=||(9,0,0)||=9</code>) and the distance for the inner 
 * intersection would be 11 (<code>||(10,0,0)-(-1,0,0)||=||(11,0,0)||=11</code>).</p>
 * 
 * @author marcupic
 *
 */
public abstract class RayIntersection {
	
	// flag: is intersection outer?
	private boolean outer;
	// point of intersection
	private Point3D point;
	// distance between start of ray and intersection
	private double distance;
	
	/**
	 * Constructor for intersection.
	 * 
	 * @param point point of intersection between ray and object
	 * @param distance distance between start of ray and intersection
	 * @param outer specifies if intersection is outer
	 */
	protected RayIntersection(Point3D point, double distance, boolean outer) {
		super();
		this.point = point;
		this.distance = distance;
		this.outer = outer;
	}

	/**
	 * Specifies if intersection is outer.
	 * @return <code>true</code> if intersection is outer, <code>false</code> otherwise.
	 */
	public boolean isOuter() {
		return outer;
	}

	/**
	 * Point where intersection occured.
	 * @return intersection point
	 */
	public Point3D getPoint() {
		return point;
	}

	/**
	 * Distance between start of ray and intersection.
	 * @return distance
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Returns normal to object surface at this intersection point.
	 * 
	 * @return normal
	 */
	public abstract Point3D getNormal();

	/**
	 * Returns coefficient for diffuse component for red color; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return red diffuse component
	 */
	public abstract double getKdr();

	/**
	 * Returns coefficient for diffuse component for green color; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return green diffuse component
	 */
	public abstract double getKdg();

	/**
	 * Returns coefficient for diffuse component for blue blue; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return blue diffuse component
	 */
	public abstract double getKdb();

	/**
	 * Returns coefficient for reflective component for red color; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return red diffuse component
	 */
	public abstract double getKrr();

	/**
	 * Returns coefficient for reflective component for green color; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return green diffuse component
	 */
	public abstract double getKrg();

	/**
	 * Returns coefficient for reflective component for blue color; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return blue diffuse component
	 */
	public abstract double getKrb();

	/**
	 * Returns coefficient <code>n</code> for reflective component; used in
	 * lightning model to calculate point color. Legal values are [0.0,1.0].
	 * 
	 * @return factor <code>n</code>
	 */
	public abstract double getKrn();

}
