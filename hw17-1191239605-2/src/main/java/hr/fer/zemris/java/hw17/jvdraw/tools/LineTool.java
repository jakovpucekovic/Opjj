package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 *	{@link Tool} which enables the creation of a {@link Line}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LineTool implements Tool {

	/**Starting point of the line.*/
	private Point start = null;
	
	/**End point of the line.*/
	private Point end = null;
	
	/**{@link Line} which is being created.*/
	private Line line = null;
	
	/**{@link DrawingModel} in which the constructed {@link Line} should be added*/
	private DrawingModel model;
	
	/**{@link IColorProvider} which provides the {@link Color} of the {@link Line}.*/
	private IColorProvider colorProvider;
	
	/**
	 * 	Constructs a new {@link LineTool}.
	 * 	@param model {@link DrawingModel} in which the constructed {@link Line} should be added.
	 * 	@param colorProvider {@link IColorProvider} which provides the {@link Color} of the line.
	 */
	public LineTool(DrawingModel model, IColorProvider colorProvider) {
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
		if(line != null) {
			line.accept(new GeometricalObjectPainter(g2d));
		}
	}

}
