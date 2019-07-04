package hr.fer.zemris.java.hw17.jvdraw.graphicalObjects;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	GeometricalObject TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public abstract class GeometricalObject {

	/**List of {@link GeometricalObjectListener}s that need to be notified if the object changes.*/
	List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * 	Method which accepts the given {@link GeometricalObjectVisitor}.
	 * 	@param v {@link GeometricalObjectVisitor} to accept.
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 *  TODO 
	 */	
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * 	Returns the bounding box of the {@link GeometricalObject}.
	 * 	@return The bounding box of the {@link GeometricalObject}.
	 */
	public abstract Rectangle getBoundingBox();
	
	/**
	 *  Registers the given {@link GeometricalObjectListener}.
	 *  @param l {@link GeometricalObjectListener} to register.
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 *  Removes the given {@link GeometricalObjectListener}.
	 *  @param l {@link GeometricalObjectListener} to remove.
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 *  Notifies all registered {@link GeometricalObjectListener}s and calls
	 *  their <code>geometricalObjectChanged()</code> method.
	 */
	public void notifyListeners() {
		for(var l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	
}
