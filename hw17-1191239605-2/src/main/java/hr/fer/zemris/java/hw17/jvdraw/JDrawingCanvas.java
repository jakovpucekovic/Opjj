package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 *	JDrawingCanvas TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	private Supplier<Tool> toolGetter;
	private DrawingModel model;
	
	/**
	 * 	Constructs a new JDrawingCanvas.
	 * 	TODO javadoc
	 */
	public JDrawingCanvas(DrawingModel model, Supplier<Tool> toolGetter) {
		this.model = model;
		this.toolGetter = toolGetter;
		model.addDrawingModelListener(this);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		for(int i = 0; i < model.getSize(); ++i) {
			model.getObject(i).accept(new GeometricalObjectPainter((Graphics2D) getGraphics()));
		}
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		toolGetter.get().paint((Graphics2D) getGraphics());
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		toolGetter.get().paint((Graphics2D) getGraphics());
	}

}
