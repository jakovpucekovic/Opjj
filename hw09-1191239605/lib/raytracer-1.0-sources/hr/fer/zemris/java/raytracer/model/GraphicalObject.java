package hr.fer.zemris.java.raytracer.model;

/**
 * This is an abstract model of any graphical object that can exist is
 * our scene.
 * 
 * @author marcupic
 *
 */
public abstract class GraphicalObject {

	/**
	 * This method calculates intersection between given ray and this object.
	 * In case there exists more than one intersection, this method must return
	 * first (closest) intersection encountered by observer that is placed
	 * in point {@linkplain Ray#start} and that looks in direction determined
	 * by {@linkplain Ray#direction}. Intersections that are behind observer
	 * are not considered or returned. If there exists no acceptable intersection
	 * between given ray and this object, the method must return <code>null</code>.
	 * 
	 * @param ray ray for which intersections are searched
	 * @return closest intersection of this object and given ray that is in front
	 *         of observer of <code>null</code> if such intersection does not exists.
	 */
	public abstract RayIntersection findClosestRayIntersection(Ray ray);

}
