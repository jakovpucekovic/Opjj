package hr.fer.zemris.java.raytracer.model;

/**
 *	Sphere TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Sphere extends GraphicalObject {
	
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	
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
	
	//vraca "tocku" na kugli kroz koju prolazi ray
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D point;
		
		double a = ray.direction.scalarProduct(ray.direction);
		double b = 2*(ray.direction.scalarProduct(ray.start.sub(center)));
		Point3D startSubCenter = ray.start.sub(center);
		double c = startSubCenter.scalarProduct(startSubCenter) - radius * radius;
		
		double disk = b * b - 4 * a * c;
		if(disk < 0) { //no intersections
			return null;
		}
		
		double d1 = (-b + Math.sqrt(disk)) / (2 * a);
		double d2 = (-b - Math.sqrt(disk)) / (2 * a);
		
		double d = d1 < d2 ? d1 : d2;
		//duljina mora biti nenegativna
		if(d < 0) {
			return null;
		}
	
		point = ray.start.add(ray.direction.scalarMultiply(d));
		
		double distance = ray.start.sub(point).norm(); //udaljenost tocke na kugli i pocetka ray-a
		boolean outer = true; //jer vracamo blizu tocku presjeka, pravac sjece kuglu u 2 tocke, mi vracamo blizu
		
		
		return new RayIntersection(point, distance, outer) {
			
			@Override
			public Point3D getNormal() {
				return point.sub(center).modifyNormalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
	
}