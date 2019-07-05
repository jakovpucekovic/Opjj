package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 *	{@link Tool} which enables creation of a {@link FilledCircle}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class FilledCircleTool implements Tool {
	
	/**Center point of the circle.*/
	private Point center = null;

	/**Radius of the circle.*/
	private int radius;
	
	/**{@link FilledCircle} which is being created.*/
	private FilledCircle filledCircle;
	
	/**{@link DrawingModel} in which the constructed {@link Circle} should be added*/
	private DrawingModel model;
	
	/**{@link IColorProvider} which provides the border {@link Color} of the {@link Circle}.*/
	private IColorProvider borderColorProvider;
	
	/**{@link IColorProvider} which provides the fill {@link Color} of the {@link Circle}.*/
	private IColorProvider fillColorProvider;
	
	/**
	 * 	Constructs a new {@link FilledCircleTool}.
	 * 	@param model {@link DrawingModel} in which the constructed {@link Circle} should be added.
	 * 	@param borderColorProvider {@link IColorProvider} which provides the border {@link Color} of the circle.
	 * 	@param fillColorProvider {@link IColorProvider} which provides the fill {@link Color} of the circle.
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider borderColorProvider, IColorProvider fillColorProvider) {
		this.model = model;
		this.borderColorProvider = borderColorProvider;
		this.fillColorProvider = fillColorProvider;
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
			model.remove(filledCircle);
			radius = (int) Math.round(center.distance(e.getPoint()));
			filledCircle = new FilledCircle(center, radius, borderColorProvider.getCurrentColor(), fillColorProvider.getCurrentColor());
			model.add(filledCircle);
			filledCircle = null;
			center = null;
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(center != null) {
			model.remove(filledCircle);
			filledCircle = new FilledCircle(center, (int) Math.round(center.distance(e.getPoint())), borderColorProvider.getCurrentColor(), fillColorProvider.getCurrentColor());
			model.add(filledCircle);
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
		if(filledCircle != null) {
			filledCircle.accept(new GeometricalObjectPainter(g2d));
		}
	}

}
