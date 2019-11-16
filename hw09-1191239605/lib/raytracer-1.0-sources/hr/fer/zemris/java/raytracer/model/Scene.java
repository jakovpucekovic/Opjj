package hr.fer.zemris.java.raytracer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model of scene. Scene can contain graphical objects
 * and light sources. For example:
 * <pre>
 *  Scene scene = new Scene();
 *  scene
 *       .add(new Sphere(new Point3D(-2,-2,2), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10))
 *       .add(new Sphere(new Point3D(-2,2,-3), 5, 1, 1, 1, 0.5, 0.5, 0.5, 10))
 *       .add(new LightSource(new Point3D(10,5,5), 100, 0, 0))
 *       .add(new LightSource(new Point3D(10,-5,5), 0, 80, 80))
 *       .add(new LightSource(new Point3D(2,5,-1), 80, 80, 0))
 *       ;
 * </pre>
 * 
 * @author marcupic
 *
 */
public class Scene {

	// Used to track graphical objects
	private List<GraphicalObject> objects = new ArrayList<>();
	// Used to track light sources
	private List<LightSource> lights = new ArrayList<>();
	
	/**
	 * Constructor for empty scene.
	 */
	public Scene() {
	}
	
	/**
	 * Add graphical object to scene.
	 * 
	 * @param object graphical object
	 * @return this scene
	 */
	public Scene add(GraphicalObject object) {
		objects.add(object);
		return this;
	}
	
	/**
	 * Add light source to scene.
	 * 
	 * @param source light source
	 * @return this scene
	 */
	public Scene add(LightSource source) {
		lights.add(source);
		return this;
	}

	/**
	 * Getter for graphical objects.
	 * 
	 * @return list of graphical objects
	 */
	public List<LightSource> getLights() {
		return lights;
	}
	
	/**
	 * Getter for light sources.
	 * @return list of light sources
	 */
	public List<GraphicalObject> getObjects() {
		return objects;
	}
}
