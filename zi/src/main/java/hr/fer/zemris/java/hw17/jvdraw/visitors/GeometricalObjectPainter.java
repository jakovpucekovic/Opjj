package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

/**
 *	A {@link GeometricalObjectVisitor} which paints the {@link GeometricalObject}s it
 *	visits onto the given {@link Graphics2D}.
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
		g2d.setColor(circle.getBorderColor());
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
		g2d.fillOval(filledCircle.getCenter().x - filledCircle.getRadius(),
				 filledCircle.getCenter().y - filledCircle.getRadius(),
				 filledCircle.getRadius() * 2,
				 filledCircle.getRadius() * 2);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void visit(FilledTriangle filledTriangle) {
		int x[] = new int[] {filledTriangle.getA().x, filledTriangle.getB().x, filledTriangle.getC().x};
		int y[] = new int[] {filledTriangle.getA().y, filledTriangle.getB().y, filledTriangle.getC().y};

		g2d.setColor(filledTriangle.getFillColor());
		g2d.fillPolygon(x, y, filledTriangle.getN());
		
		g2d.setColor(filledTriangle.getBorderColor());      
		g2d.drawPolygon(x, y, filledTriangle.getN());
	}

}
