package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 *	{@link Tool} which enables creation of a {@link Circle}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CircleTool implements Tool {

	/**Center point of the circle.*/
	private Point center = null;

	/**Radius of the circle.*/
	private int radius;
	
	/**{@link Circle} which is being created.*/
	private Circle circle;
	
	/**{@link DrawingModel} in which the constructed {@link Circle} should be added*/
	private DrawingModel model;
	
	/**{@link IColorProvider} which provides the {@link Color} of the {@link Circle}.*/
	private IColorProvider colorProvider;
	
	/**
	 * 	Constructs a new {@link CircleTool}.
	 * 	@param model {@link DrawingModel} in which the constructed {@link Circle} should be added.
	 * 	@param colorProvider {@link IColorProvider} which provides the {@link Color} of the circle.
	 */
	public CircleTool(DrawingModel model, IColorProvider colorProvider) {
		this.model = model;
		this.colorProvider = colorProvider;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(center == null) {
			center = e.getPoint();
		} else {
			model.remove(circle);
			radius = (int) Math.round(center.distance(e.getPoint()));
			circle = new Circle(center, radius, colorProvider.getCurrentColor());
			model.add(circle);
			circle = null;
			center = null;
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(center != null) {
			model.remove(circle);
			circle = new Circle(center, (int) Math.round(center.distance(e.getPoint())), colorProvider.getCurrentColor());
			model.add(circle);
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		if(circle != null) {
			circle.accept(new GeometricalObjectPainter(g2d));
		}
	}
	
}
