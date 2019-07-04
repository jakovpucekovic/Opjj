package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;

/**
 *	FilledCircleTool TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FilledCircleTool implements Tool {

	private Point center = null;
	private int radius;
	private FilledCircle filledCircle;
	
	private DrawingModel model;
	private JDrawingCanvas canvas;
	private IColorProvider borderColorProvider;
	private IColorProvider fillColorProvider;
	
	/**
	 * 	Constructs a new FilledCircleTool.
	 * 	TODO javadoc
	 */
	public FilledCircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider borderColorProvider, IColorProvider fillColorProvider) {
		this.model = model;
		this.canvas = canvas;
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
			filledCircle = new FilledCircle(center, radius, borderColorProvider.getCurrentColor(), fillColorProvider.getCurrentColor());
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
		// TODO 
	}

}
