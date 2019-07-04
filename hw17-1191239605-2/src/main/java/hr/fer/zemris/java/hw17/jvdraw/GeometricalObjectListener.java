package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	A listener which listens for changes on {@link GeometricalObject}s and 
 *	whose method <code>geometricalObjectChanged()</code> should be called when
 *	the observed {@link GeometricalObject} changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface GeometricalObjectListener {

	/**
	 *  Method which should be called when the {@link GeometricalObject}
	 *  has changed.
	 *  @param o {@link GeometricalObject} that has changed.
	 */
	public void geometricalObjectChanged(GeometricalObject o);
	
}
