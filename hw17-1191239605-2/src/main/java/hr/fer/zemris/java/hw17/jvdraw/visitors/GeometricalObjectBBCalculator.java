package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.awt.Dimension;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

/**
 *	GeometricalObjectBBCalculator TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor{

	/**The bounding box which needs to be calculated.*/
	private Rectangle boundingBox = new Rectangle(new Dimension(-1, -1));
	
	/**
	 *	Returns the calculated bounding box.
	 *	@return The calculated bounding box.
	 */
	public Rectangle getBoundingBox() {	
		return boundingBox;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		boundingBox.add(line.getBoundingBox());
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		boundingBox.add(circle.getBoundingBox());
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		boundingBox.add(filledCircle.getBoundingBox());
	}
	
}
