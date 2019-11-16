package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 *	Class which models a ray caster which uses multithreading and shows
 *	the scene from multiple angles.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class RayCasterParallel2 {

	/**Color of scene AMBIENT_COLOR.*/
	private static final short[] AMBIENT_COLOR = {15, 15, 15};
	
	/**
	 * 	Runs the program.
	 * 	@param args None.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
				getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
		);
	}
	
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;
		
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}
			
			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0,0,10);
			}
			
			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2,0,-0.5);
			}
			
			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}
		
			@Override
			public Point3D getEye() { // changes in time
				double t = (double)time / 10000 * 2 * Math.PI;
				double t2 = (double)time / 5000 * 2 * Math.PI;
				double x = 50*Math.cos(t);
				double y = 50*Math.sin(t);
				double z = 30*Math.sin(t2);
				return new Point3D(x,y,z);
			}
		};
	}
	
	/**
	 *	Returns a {@link IRayTracerProducer} which creates the scene.
	 *	@return A {@link IRayTracerProducer}. 
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
				
			private ForkJoinPool pool = new ForkJoinPool();
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
				double horizontal, double vertical, int width, int height,
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
				
				Scene scene = RayTracerViewer.createPredefinedScene2();
	
				pool.invoke(new Work(0, height, width, height, xAxis, yAxis, eye, screenCorner, scene, horizontal, vertical, red, green, blue, cancel));
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
			
		};
	}
	
	/**
	 *   Class which represents work that should be done.
	 */
	public static class Work extends RecursiveAction{

		private static final long serialVersionUID = 1L;
		
		/**Number of rows that 1 thread will execute, not forking into 2.*/
		private static final int treshold = 16;

		
		/**Minimal height for which the {@link Work} should be done.*/
		private int yMin;
		/**Maximal height for which the {@link Work} should be done.*/
		private int yMax;
		/**Height of the window in pixels.*/
		private int height;
		/**Width of the window in pixels.*/
		private int width;
		/**X axis of the coordinate system.*/
		private Point3D xAxis;
		/**Y axis of the coordinate system.*/
		private Point3D yAxis;
		/**Point from where the spectator is looking.*/
		private Point3D eye;
		/**Corner of the screen.*/
		private Point3D screenCorner;
		/**Scene in which the work should be done.*/
		private Scene scene;
		/**Horizontal length of coordinate system.*/
		private double horizontal;
		/**Vertical length of coordinate system.*/
		private double vertical;
		/**Amount of red color in rgb color.*/
		private short[] red;
		/**Amount of green color in rgb color.*/
		private short[] green;
		/**Amount of blue color in rgb color.*/
		private short[] blue;
		/**Signals whether to cancel an operation.*/
		private AtomicBoolean cancel;
	
		/**
		 * 	Constructs a new {@link Work} with the given arguments.
		 * 	@param yMin Minimal height for which the {@link Work} should be done.
		 * 	@param yMax Maximal height for which the {@link Work} should be done.
		 * 	@param width Width of the window in pixels.
		 * 	@param height Height of the window in pixels.
		 * 	@param xAxis X axis of the coordinate system.
		 * 	@param yAxis Y axis of the coordinate system.
		 * 	@param eye Point from where the spectator is looking.
		 * 	@param screenCorner Corner of the screen.
		 * 	@param scene Scene in which the work should be done.
		 * 	@param horizontal Horizontal length of coordinate system.
		 * 	@param vertical Vertical length of coordinate system.
		 * 	@param red Amount of red color in rgb color.
		 * 	@param green Amount of green color in rgb color.
		 * 	@param blue Amount of blue color in rgb color.
		 * 	@param cancel Signals whether to cancel an operation.
		 */
		public Work(int yMin, int yMax, int width, int height, Point3D xAxis, Point3D yAxis, Point3D eye, Point3D screenCorner, Scene scene, double horizontal, double vertical, short[] red, short[] green, short[] blue, AtomicBoolean cancel) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.width = width;
			this.height = height;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.eye = eye;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.cancel = cancel;
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		protected void compute() {
			if(yMax - yMin <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(new Work(yMin, yMin+(yMax-yMin)/2, width, height, xAxis, yAxis, eye, screenCorner, scene, horizontal, vertical, red, green, blue, cancel), 
					  new Work(yMin+(yMax-yMin)/2, yMax, width, height, xAxis, yAxis, eye, screenCorner, scene, horizontal, vertical, red, green, blue, cancel));
		}
		
		/**
		 * 	Computes the colors for the pixels in the given range.
		 */
		public void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for(int y = yMin; y < yMax; y++) {
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
				if(cancel.get()) {
					return;
				}
			}
		}
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
			if(intersectRayToLight != null && intersectRayToLight.getDistance() + 1e-8 < intersect.getPoint().sub(light.getPoint()).norm()) {
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
