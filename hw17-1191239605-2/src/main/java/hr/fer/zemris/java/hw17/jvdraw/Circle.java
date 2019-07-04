package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Point;

/**
 *	Circle TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Circle extends GeometricalObject{

	/**Center of the circle.*/
	Point center;

	/**Radius of the circle.*/
	int radius;
	
	/**
	 * 	Constructs a new Circle of the given radius at the given
	 * 	center point
	 * 	@param center Center of the circle.
	 * 	@param radius Radius of the circle.
	 */
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	/**
	 * 	Returns the center point of the {@link Circle}.
	 * 	@return the center point of the {@link Circle}.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * 	Returns the radius of the {@link Circle}.
	 * 	@return the radius of the {@link Circle}.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
