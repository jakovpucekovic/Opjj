package hr.fer.zemris.java.raytracer.model;

/**
 * This is a model of point-shaped light source. This light source
 * is placed in 3D space in position {@link #getPoint()} and it emits
 * colored light which red, green and blue intensities are determined
 * by {@link #getR()}, {@link #getG()} and {@link #getB()}.
 *  
 * @author marcupic
 *
 */
public class LightSource {

	// Point where light is placed
	private Point3D point;
	// Red intensity of light
	private int r;
	// Green intensity of light
	private int g;
	// Blue intensity of light
	private int b;

	/**
	 * Constructor for single light source.
	 * 
	 * @param point point in which light source is placed
	 * @param r red intensity of light; must be between 0 and 255 inclusive
	 * @param g green intensity of light; must be between 0 and 255 inclusive
	 * @param b blue intensity of light; must be between 0 and 255 inclusive
	 */
	public LightSource(Point3D point, int r, int g, int b) {
		super();
		if(point==null) {
			throw new NullPointerException("Light placement can not be null.");
		}
		if(r<0 || r>255 || g<0 || g>255 || b<0 || b>255) {
			throw new IllegalArgumentException("Light intensity specification ("+r+","+g+","+b+") is invalid.");
		}
		this.point = point;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Getter for light position.
	 * 
	 * @return light position
	 */
	public Point3D getPoint() {
		return point;
	}

	/**
	 * Getter for color intensity for red component.
	 * 
	 * @return red component of light source color
	 */
	public int getR() {
		return r;
	}

	/**
	 * Getter for color intensity for green component.
	 * 
	 * @return green component of light source color
	 */
	public int getG() {
		return g;
	}

	/**
	 * Getter for color intensity for blue component.
	 * 
	 * @return blue component of light source color
	 */
	public int getB() {
		return b;
	}

	@Override
	public String toString() {
		return "Light @["+point.x+","+point.y+","+point.z+"] intensity["+r+","+g+","+b+"]";
	}
	
}
