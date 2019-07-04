package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Point;

/**
 *	FilledCircle TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FilledCircle extends Circle {

	/**Fill color of the circle.*/
	private Color fillColor;

	/**Border color of the circle.*/
	private Color borderColor;
	
	/**
	 * 	Constructs a new FilledCircle with the given radius at the given
	 * 	center point with the given border color and fill color.
	 * 	@param center Center of the circle.
	 * 	@param radius Radius of the circle.
	 * 	@param fillColor Fill color of the circle.
	 * 	@param borderColor Border color of the circle.
	 */
	public FilledCircle(Point center, int radius, Color fillColor, Color borderColor) {
		super(center, radius);
		this.fillColor = fillColor;
		this.borderColor = borderColor;
	}
	
	/**
	 * 	Returns the fill {@link Color} of the {@link FilledCircle}.
	 * 	@return the fill {@link Color} of the {@link FilledCircle}.
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * 	Returns the border {@link Color} of the {@link FilledCircle}.
	 * 	@return the border {@link Color} of the {@link FilledCircle}.
	 */
	public Color getBorderColor() {
		return borderColor;
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
