package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 *	Class which models a ray caster.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class RayCaster {

	/**Color of scene ambient.*/
	private static final short[] AMBIENT_COLOR = {15, 15, 15};
	
	/**
	 * 	Runs the program.
	 * 	@param args None.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
		new Point3D(10,0,0),
		new Point3D(0,0,0),
		new Point3D(0,0,10),
		20, 20);
		}
	
	/**
	 *	Returns a {@link IRayTracerProducer} which creates the scene.
	 *	@return A {@link IRayTracerProducer}. 
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
				
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
				double horizontal, double vertical, int width, int height,//nigdje ne koristim atomic boolean cancel
				long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.sub(eye).modifyNormalize();				
				Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize())));
				yAxis.modifyNormalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).modifyNormalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {

						Point3D first = xAxis.scalarMultiply(horizontal * x /(width - 1));
						Point3D second = yAxis.scalarMultiply(vertical * y /(height - 1));
						Point3D screenPoint = screenCorner.add(first).sub(second);
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
			
		};
	}
	
	/**
	 *	Method which does the tracing.
	 *	@param scene Scene in which the tracing should be done.
	 *	@param ray Ray that is traced.
	 *	@param rgb Short[] to store color. 
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		
		RayIntersection closest = findClosestIntersection(scene, ray);
		if(closest == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(closest, scene, ray, rgb);
		}
	}
			
	/**
	 * 	Determines the color of the given intersection.
	 * 	@param intersect The intersection whose color should be determined.
	 * 	@param scene The scene in which the intersection is.
	 * 	@param rgb Short[] to store color.
	 */
	private static void determineColorFor(RayIntersection intersect, Scene scene, Ray ray, short[] rgb) {
		rgb[0] = AMBIENT_COLOR[0];
		rgb[1] = AMBIENT_COLOR[1];
		rgb[2] = AMBIENT_COLOR[2];
		
		for(var light : scene.getLights()) {
			Ray rayFromLight = Ray.fromPoints(light.getPoint(), intersect.getPoint());
			RayIntersection intersectRayToLight = findClosestIntersection(scene, rayFromLight);
			if(intersectRayToLight != null && intersectRayToLight.getDistance() + 1e-4 < intersect.getPoint().sub(light.getPoint()).norm()) {
				continue;
			}
			addColor(rgb, intersect, ray, light);
		}
	}

	/**
	 *	Adds the color colors the light source emits onto the intersection.
	 *	@param rgb Short[] to store color.
	 *	@param intersect Intersection whose color is updated.
	 *	@param ray Ray from spectator to intersection.
	 *	@param light Light whose color is added.
	 */
	private static void addColor(short[] rgb, RayIntersection intersect, Ray ray, LightSource light) {
		Point3D normalOfSurface = intersect.getNormal();
		Point3D vectorFromIntersectionToLightSource = light.getPoint().sub(intersect.getPoint()).modifyNormalize();
		
		/*Diffuse components*/
		double cos = normalOfSurface.scalarProduct(vectorFromIntersectionToLightSource);
		if(cos < 0) {
			cos = 0;
		}
		
		rgb[0] += intersect.getKdr() * light.getR() * cos;
		rgb[1] += intersect.getKdg() * light.getG() * cos;
		rgb[2] += intersect.getKdb() * light.getB() * cos;
		
		/*Reflective components*/
		Point3D reflectedRay = normalOfSurface.scalarMultiply(normalOfSurface.scalarProduct(vectorFromIntersectionToLightSource)*2).modifySub(vectorFromIntersectionToLightSource).modifyNormalize();
		Point3D vectorFromIntersectToEye = ray.start.sub(intersect.getPoint()).modifyNormalize();
		double cosN = reflectedRay.scalarProduct(vectorFromIntersectToEye);
		if(cosN < 0) {
			cosN = 0;
		} else {
			cosN = Math.pow(cosN, intersect.getKrn());
		}
	
		rgb[0] += intersect.getKrr() * light.getR() * cosN;
		rgb[1] += intersect.getKrg() * light.getG() * cosN;
		rgb[2] += intersect.getKrb() * light.getB() * cosN;
	}
	
	/**
	 * 	Finds the closes intersection of the given ray and any object in this scene or
	 * 	<code>null</code> if there are no intersections.
	 * 	@param scene Scene in which to search for intersections.
	 * 	@param ray Ray to intersect with.
	 * 	@return Closes intersection in the scene.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {		
		RayIntersection closestIntersection = null;
		for(var object : scene.getObjects()) {
			if(closestIntersection == null) {
				closestIntersection = object.findClosestRayIntersection(ray);
				continue;
			}
			RayIntersection rayInt = object.findClosestRayIntersection(ray);
			if(rayInt != null && rayInt.getDistance() < closestIntersection.getDistance()) {
				closestIntersection = rayInt;
			}
		}
		return closestIntersection;
		
	}
				
}
