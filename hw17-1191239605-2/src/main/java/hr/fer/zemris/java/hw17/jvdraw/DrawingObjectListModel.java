package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	ObjectList TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener{

	private static final long serialVersionUID = 1L;
	
	/**{@link DrawingModel} for which this class is an adapter.*/
	private DrawingModel model;
	
	/**
	 * 	Constructs a new {@link DrawingObjectListModel} for the given
	 * 	{@link DrawingModel} and registers itself as an listener to it.
	 * 	@param model {@link DrawingModel} for which this is a list model.
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int getSize() {
		return model.getSize();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}
	
}
