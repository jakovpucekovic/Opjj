package hr.fer.zemris.java.hw17.jvdraw.graphicalObjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	Class which represents a {@link Circle} filled with a color.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class FilledCircle extends Circle {

	/**Fill color of the circle.*/
	private Color fillColor;
	
	/**
	 * 	Constructs a new FilledCircle with the given radius at the given
	 * 	center point with the given border color and fill color.
	 * 	@param center Center of the circle.
	 * 	@param radius Radius of the circle.
	 * 	@param fillColor Fill color of the circle.
	 * 	@param borderColor Border color of the circle.
	 */
	public FilledCircle(Point center, int radius, Color borderColor, Color fillColor) {
		super(center, radius, borderColor);
		this.fillColor = fillColor;
	}
	
	/**
	 * 	Returns the fill {@link Color} of the {@link FilledCircle}.
	 * 	@return the fill {@link Color} of the {@link FilledCircle}.
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * 	Sets the fillColor of the {@link FilledCircle}.
	 * 	@param fillColor the fillColor to set.
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
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
		return new FilledCircleEditor(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("Filled circle (%d,%d), %d, #%02X%02X%02X", 
							  center.x, 
							  center.y, 
							  radius, 
							  fillColor.getRed(), 
							  fillColor.getGreen(), 
							  fillColor.getBlue());
	}
	
}
