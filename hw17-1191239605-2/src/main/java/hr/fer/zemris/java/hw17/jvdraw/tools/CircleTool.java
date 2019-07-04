package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;

/**
 *	CircleTool TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class CircleTool implements Tool {

	private Point center = null;
	private int radius;
	private Circle circle;
	
	private DrawingModel model;
	private JDrawingCanvas canvas;
	private IColorProvider colorProvider;
	
	/**
	 * 	Constructs a new CircleTool.
	 * 	TODO javadoc
	 */
	public CircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider colorProvider) {
		this.model = model;
		this.canvas = canvas;
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
			circle = new Circle(center, radius, colorProvider.getCurrentColor());
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
		// TODO 
	}

	
}
