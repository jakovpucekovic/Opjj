package hr.fer.zemris.java.hw17.jvdraw.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	Class on which are all {@link GeometricalObject}s from the provided 
 *	{@link DrawingModel} drawn.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;
	
	/**{@link Supplier} which provides the currently used {@link Tool}.*/
	private Supplier<Tool> toolGetter;

	/**{@link DrawingModel} which keeps track of {@link GeometricalObject}s.*/
	private DrawingModel model;
	
	/**
	 * 	Constructs a new {@link JDrawingCanvas} with the given {@link DrawingModel}
	 * 	and {@link Tool} {@link Supplier}.
	 * 	@param model {@link DrawingModel} used for keeping track of {@link GeometricalObject}s.
	 * 	@param toolGetter {@link Supplier} of {@link Tool} which provides the current {@link Tool}.
	 */
	public JDrawingCanvas(DrawingModel model, Supplier<Tool> toolGetter) {
		this.model = model;
		this.toolGetter = toolGetter;
		model.addDrawingModelListener(this);
		
		addMouseListener(new MouseAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				toolGetter.get().mouseReleased(e);
			}
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				toolGetter.get().mousePressed(e);
			}
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				toolGetter.get().mouseClicked(e);
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				toolGetter.get().mouseDragged(e);
			}
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				toolGetter.get().mouseMoved(e);
			}
		});
		
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d); 
        for(int i = 0; i < model.getSize(); ++i) {
        	model.getObject(i).accept(painter);
        }

        toolGetter.get().paint(g2d);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
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
	}

}
