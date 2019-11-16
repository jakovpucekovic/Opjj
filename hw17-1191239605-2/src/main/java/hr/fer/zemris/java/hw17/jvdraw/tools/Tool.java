package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	Interface which models tools used for creation of various {@link GeometricalObject}s.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface Tool {

	/**
	 * 	Action which needs to be done when the mouse is pressed.
	 * 	@param e {@link MouseEvent} when the mouse is pressed.
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * 	Action which needs to be done when the mouse is released.
	 * 	@param e {@link MouseEvent} when the mouse is released.
	 */
	public void mouseReleased(MouseEvent e);
	
	/**
	 * 	Action which needs to be done when the mouse is clicked.
	 * 	@param e {@link MouseEvent} when the mouse is clicked.
	 */
	public void mouseClicked(MouseEvent e);
	
	/**
	 * 	Action which needs to be done when the mouse is moved.
	 * 	@param e {@link MouseEvent} when the mouse is moved.
	 */
	public void mouseMoved(MouseEvent e);
	
	/**
	 * 	Action which needs to be done when the mouse is dragged.
	 * 	@param e {@link MouseEvent} when the mouse is dragged.
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * 	Paints whatever needs to be painted on the given {@link Graphics2D}.
	 * 	@param g2d {@link Graphics2D} on which to paint on.
	 */
	public void paint(Graphics2D g2d);
	
}
