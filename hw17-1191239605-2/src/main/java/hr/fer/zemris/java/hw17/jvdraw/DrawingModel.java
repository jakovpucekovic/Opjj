package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	DrawingModel TODO javadoc
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
	 * TODO
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
	 *  TODO
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
