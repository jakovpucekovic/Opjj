package hr.fer.zemris.java.hw17.jvdraw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	An implementation of the {@link DrawingModel}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class DrawingModelImpl implements DrawingModel {

	/**{@link List} of all {@link GeometricalObject}s.*/
	private List<GeometricalObject> geomObjects = new ArrayList<>();
	
	/**{@link List} of all {@link DrawingModelListener}s.*/
	private List<DrawingModelListener> listeners = new ArrayList<>();

	/**Flag which signals if the model has been modified.*/
	private boolean modified = false;
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int getSize() {
		return geomObjects.size();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		return geomObjects.get(index);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		geomObjects.add(object);
		object.addGeometricalObjectListener(this);
		modified = true;
		notifyAdded(geomObjects.size() - 1 , geomObjects.size() - 1);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void remove(GeometricalObject object) {
		int index = geomObjects.indexOf(object);
		if(index != -1) {
			geomObjects.remove(index);
			modified = true;
			notifyRemoved(index, index);
		}
	}

	/**
	 *	{@inheritDoc}
	 *	@throws IllegalArgumentException If there is no element at the given offset.
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = geomObjects.indexOf(object);
		int newIndex = index + offset;
		if(newIndex < 0 || newIndex > geomObjects.size() - 1) {
			throw new IllegalArgumentException("Index out of bounds for offset " + offset);
		}
		Collections.swap(geomObjects, index, newIndex);
		modified = true;
		notifyChanged(index, newIndex);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int indexOf(GeometricalObject object) {
		return geomObjects.indexOf(object);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void clear() {
		geomObjects.clear();
		modified = true;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
	
	/**
	 *  Notifies all registered listeners that an array of {@link GeometricalObject}s has been added.
	 *  @param index0 Index of the first added {@link GeometricalObject}.
	 *  @param index1 Index of the last added {@link GeometricalObject}.
	 */
	public void notifyAdded(int index0, int index1) {
		for(var l : listeners) {
			l.objectsAdded(this, index0, index1);
		}
	}
	
	/**
	 *  Notifies all registered listeners that an array of {@link GeometricalObject}s has been removed.
	 *  @param index0 Index of the first removed {@link GeometricalObject}.
	 *  @param index1 Index of the last removed {@link GeometricalObject}.
	 */
	public void notifyRemoved(int index0, int index1) {
		for(var l : listeners) {
			l.objectsRemoved(this, index0, index1);
		}
	}
	
	/**
	 *  Notifies all registered listeners that an array of {@link GeometricalObject}s has been changed.
	 *  @param index0 Index of the first changed {@link GeometricalObject}.
	 *  @param index1 Index of the last changed {@link GeometricalObject}.
	 */
	public void notifyChanged(int index0, int index1) {
		for(var l : listeners) {
			l.objectsChanged(this, index0, index1);
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		modified = true;
		int index = geomObjects.indexOf(o);
		notifyChanged(index, index);		
	}

}
