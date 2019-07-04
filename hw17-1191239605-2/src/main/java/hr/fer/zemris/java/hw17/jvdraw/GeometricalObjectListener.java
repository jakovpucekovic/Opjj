package hr.fer.zemris.java.hw17.jvdraw;

/**
 *	GeometricalObjectListener TODO javadoc
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
