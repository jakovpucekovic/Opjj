package hr.fer.zemris.java.hw17.jvdraw.model;

/**
 *	A listener which observes for changes in a {@link DrawingModel} and should be
 *	notified if a change in the observed {@link DrawingModel} happens by calling its
 *	appropriate method.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface DrawingModelListener {

	/**
	 * 	Called when an array of {@link GeometricalObject}s is added to the observed {@link DrawingModel}.
	 * 	@param source The observed {@link DrawingModel}.
	 * 	@param index0 The index of the first added {@link GeometricalObject}.
	 * 	@param index1 The index of the last added {@link GeometricalObject}
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * 	Called when an array of {@link GeometricalObject}s is removed from the observed {@link DrawingModel}.
	 * 	@param source The observed {@link DrawingModel}.
	 * 	@param index0 The index of the first removed {@link GeometricalObject}.
	 * 	@param index1 The index of the last removed {@link GeometricalObject}
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * 	Called when an array of {@link GeometricalObject}s is changed in the observed {@link DrawingModel}.
	 * 	@param source The observed {@link DrawingModel}.
	 * 	@param index0 The index of the first changed {@link GeometricalObject}.
	 * 	@param index1 The index of the last changed {@link GeometricalObject}
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
	
}
