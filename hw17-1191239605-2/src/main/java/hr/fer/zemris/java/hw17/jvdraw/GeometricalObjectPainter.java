package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics2D;

/**
 *	GeometricalObjectPainter TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**Graphics used to draw {@link GeometricalObject}s.*/
	private Graphics2D g2d;
	
	/**
	 * 	Constructs a new {@link GeometricalObjectPainter} with the given {@link Graphics2D}.
	 * 	@param g2d {@link Graphics2D} used for drawing.
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getStart().x,
					 line.getStart().y,
					 line.getEnd().x,
					 line.getEnd().y);
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		g2d.drawOval(circle.getCenter().x - circle.getRadius(),
					 circle.getCenter().y - circle.getRadius(),
					 circle.getRadius() * 2,
					 circle.getRadius() * 2);		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getBorderColor());
		g2d.drawOval(filledCircle.getCenter().x - filledCircle.getRadius(),
				 filledCircle.getCenter().y - filledCircle.getRadius(),
				 filledCircle.getRadius() * 2,
				 filledCircle.getRadius() * 2);
		g2d.setColor(filledCircle.getFillColor());
		g2d.drawOval(filledCircle.getCenter().x - filledCircle.getRadius(),
				 filledCircle.getCenter().y - filledCircle.getRadius(),
				 filledCircle.getRadius() * 2,
				 filledCircle.getRadius() * 2);
	}

}
