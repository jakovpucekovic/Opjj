package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

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
