package hr.fer.zemris.java.hw17.jvdraw.graphicalObjects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	Class which represents a circle which is a {@link GeometricalObject}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Circle extends GeometricalObject{

	/**Center of the circle.*/
	Point center;

	/**Radius of the circle.*/
	int radius;
	
	/**Border color of the circle.*/
	private Color borderColor;
	
	/**
	 * 	Constructs a new Circle of the given radius at the given
	 * 	center point
	 * 	@param center Center of the circle.
	 * 	@param radius Radius of the circle.
	 * 	@param borderColor Border color of the circle.
	 */
	public Circle(Point center, int radius, Color borderColor) {
		this.center = center;
		this.radius = radius;
		this.borderColor = borderColor;
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
	 * 	Returns the border {@link Color} of the {@link FilledCircle}.
	 * 	@return the border {@link Color} of the {@link FilledCircle}.
	 */
	public Color getBorderColor() {
		return borderColor;
	}
	
	/**
	 * 	Sets the center of the {@link Circle}.
	 * 	@param center the center to set.
	 */
	public void setCenter(Point center) {
		this.center = center;
		notifyListeners();
	}

	/**
	 * 	Sets the radius of the {@link Circle}.
	 * 	@param radius the radius to set.
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyListeners();
	}

	/**
	 * 	Sets the borderColor of the {@link Circle}.
	 * 	@param borderColor the borderColor to set.
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		notifyListeners();
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
		return new CircleEditor(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", center.x, center.y, radius);
	}
	
}
