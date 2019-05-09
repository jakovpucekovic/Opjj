package hr.fer.zemris.java.raytracer.model;

/**
 *	Class which models a sphere.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Sphere extends GraphicalObject {
		
	/**Center of the {@link Sphere}.*/
	private Point3D center;
	/**Radius of the {@link Sphere}.*/
	private double radius;
	/**Diffuse component red parameter.*/
	private double kdr;
	/**Diffuse component green parameter.*/
	private double kdg;
	/**Diffuse component blue parameter.*/
	private double kdb;
	/**Reflective component red parameter.*/
	private double krr;
	/**Reflective component green parameter.*/
	private double krg;
	/**Reflective component blue parameter.*/
	private double krb;
	/**Shininess factor.*/
	private double krn;
	
	/**
	 * 	Constructs a new {@link Sphere} with the given parameters.
	 * 	@param center Center of the {@link Sphere}.
	 * 	@param radius Radius of the {@link Sphere}.
	 * 	@param kdr Diffuse component red parameter.
	 * 	@param kdg Diffuse component green parameter.
	 * 	@param kdb Diffuse component blue parameter.
	 * 	@param krr Reflective component red parameter.
	 * 	@param krg Reflective component green parameter.
	 * 	@param krb Reflective component blue parameter.
	 * 	@param krn Shininess factor.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr =  kdr;
		this.kdg =  kdg;
		this.kdb =  kdb;
		this.krr =  krr;
		this.krg =  krg;
		this.krb =  krb;
		this.krn =  krn;
	}
	
	/**
	 *	Returns the {@link RayIntersection} of this {@link Sphere}
	 *	closest to the given {@link Ray} if it exists or <code>null</code>
	 *	if the {@link Ray} and this {@link Sphere} do not have and intersection.
	 *	@param ray Ray to check intersection with.
	 *	@return The closest {@link RayIntersection}.
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		double a = ray.direction.scalarProduct(ray.direction);
		double b = 2*(ray.direction.scalarProduct(ray.start.sub(center)));
		Point3D startSubCenter = ray.start.sub(center);
		double c = startSubCenter.scalarProduct(startSubCenter) - radius * radius;
		
		/*Calculate discriminant*/
		double discriminant = b * b - 4 * a * c;
		
		/*If discriminant < 0 the equation has no real solutions.*/
		if(discriminant < 0) {
			return null;
		}
		
		/*Get solutions of equation a*x^2+b*x+c=0*/
		double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
		
		double solution = x1 < x2 ? x1 : x2;
		if(solution < 0) {
			return null;
		}
	
		Point3D point = ray.start.add(ray.direction.scalarMultiply(solution));
		double distance = ray.start.sub(point).norm();
		boolean outer = point.sub(ray.start).norm() > radius; 
		
		return new RayIntersection(point, distance, outer) {
			
			@Override
			public Point3D getNormal() {
				return point.sub(center).modifyNormalize();
			}
			
			/**
			 * 	Returns the value of reflective component red.
			 * 	@return The value of reflective component red.
			 */
			@Override
			public double getKrr() {
				return krr;
			}
			
			/**
			 * 	Returns the shininess factor.
			 * 	@return The shininess factor.
			 */
			@Override
			public double getKrn() {
				return krn;
			}
			
			/**
			 * 	Returns the value of reflective component green.
			 * 	@return The value of reflective component green.
			 */
			@Override
			public double getKrg() {
				return krg;
			}
			
			/**
			 * 	Returns the value of reflective component blue.
			 * 	@return The value of reflective component blue.
			 */
			@Override
			public double getKrb() {
				return krb;
			}
			
			/**
			 * 	Returns the value of diffuse component red.
			 * 	@return The value of diffuse component red.
			 */
			@Override
			public double getKdr() {
				return kdr;
			}
			
			/**
			 * 	Returns the value of diffuse component green.
			 * 	@return The value of diffuse component green.
			 */
			@Override
			public double getKdg() {
				return kdg;
			}
			
			/**
			 * 	Returns the value of diffuse component blue.
			 * 	@return The value of diffuse component blue.
			 */
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
	
}