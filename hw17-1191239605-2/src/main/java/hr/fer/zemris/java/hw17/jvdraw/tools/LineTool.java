package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

/**
 *	LineTool TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class LineTool implements Tool {

	private Point start = null;
	private Point end = null;
	private Line line = null;
	
	private DrawingModel model;
	private JDrawingCanvas canvas;
	private IColorProvider colorProvider;
	
	/**
	 * 	Constructs a new LineTool.
	 * 	TODO javadoc
	 */
	public LineTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider colorProvider) {
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
		if(start == null) {
			start = e.getPoint();
		} else {
			end = e.getPoint();
			model.remove(line);
			line = new Line(start, end, colorProvider.getCurrentColor());
			model.add(line);
			line = null;
			start = null;
			end = null;
			
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(start != null) {
			model.remove(line);
			line = new Line(start, e.getPoint(), colorProvider.getCurrentColor());
			model.add(line);
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
//TODO
	}

}
