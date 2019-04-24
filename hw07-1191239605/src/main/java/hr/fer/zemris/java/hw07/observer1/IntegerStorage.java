package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 *	Class which stores an int value and notifies it's observers
 *	when the stored value changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class IntegerStorage {
	
	/**Stored value*/
	private int value;
	/**{@link List} of observers.*/
	private List<IntegerStorageObserver> observers;
	/**Copy of the {@link List} of observers used for iterating.*/
	private List<IntegerStorageObserver> copyOfObservers;	
	
	/**
	 * 	Creates a new IntegerStorage and stores the given value in it.
	 * 	@param initialValue Value to store.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
		copyOfObservers = new ArrayList<>(observers);
	}

	/**
	 * 	Adds the given observer to the observers list if he's not
	 * 	already present.
	 * 	@param observer Observer to add.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)) {
			observers.add(observer);
			copyOfObservers = new ArrayList<>(observers);
		}
	}

	/**
	 * 	Removes the given observer from the observers list if
	 * 	he's present.
	 * 	@param observer Observer to remove.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
		copyOfObservers = new ArrayList<>(observers);
	}

	/**
	 *	Removes all observers from the observers list. 
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * 	Returns the currently stored value.
	 * 	@return The currently stored value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 	Sets the new value and notifies all the observers that the
	 * 	value has been changed.
	 * 	@param value New value.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				for(IntegerStorageObserver observer : copyOfObservers) {
					observer.valueChanged(this);
				}
			}
		}
	}
	
}