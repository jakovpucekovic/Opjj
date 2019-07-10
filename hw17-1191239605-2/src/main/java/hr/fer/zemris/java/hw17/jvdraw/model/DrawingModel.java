package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	Interface which models an object capable of tracking different
 *	{@link GeometricalObject}s.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface DrawingModel extends GeometricalObjectListener {

	/**
	 *  Returns the size of the {@link DrawingModel}.
	 *  @return The size of the {@link DrawingModel}.
	 */
	public int getSize();
	
	/**
	 *  Returns the {@link GeometricalObject} at the given index.
	 *  @param index Index of the {@link GeometricalObject} to return.
	 *  @return The {@link GeometricalObject} at the given index.
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * 	Adds the given {@link GeometricalObject} to the {@link DrawingModel}.
	 * 	@param object {@link GeometricalObject} to add.
	 */
	public void add(GeometricalObject object);
	
	/**
	 *  Removes the given {@link GeometricalObject} from the {@link DrawingModel}.
	 *  @param object {@link GeometricalObject} to remove.
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * 	Changes the order of the {@link GeometricalObject}s by moving the given object
	 * 	for the given offset.
	 * 	@param object {@link GeometricalObject} to move.
	 * 	@param offset Offset for which to move the {@link GeometricalObject}.
	 */
	public void changeOrder(GeometricalObject object, int offset);
	
	/**
	 *  Returns the index of the given {@link GeometricalObject}.
	 *  @param object {@link GeometricalObject} of which the index should be returned.
	 */
	public int indexOf(GeometricalObject object);
	
	/**
	 * 	Clears the {@link DrawingModel}.
	 */
	public void clear();
	
	/**
	 *  Sets the modified flag back to <code>false</code>.
	 */
	public void clearModifiedFlag();
	
	/**
	 * 	Checks whether the {@link DrawingModel} is modified.
	 * 	@return <code>true</code> if yes, <code>false</code> if not.
	 */
	public boolean isModified();
	
	/**
	 * 	Registers the given {@link DrawingModelListener} to the {@link DrawingModel}.
	 * 	@param l {@link DrawingModelListener} to register.
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 *  Removes the given {@link DrawingModelListener} from the {@link DrawingModel}.
	 *  @param l {@link DrawingModelListener} to remove.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
}
