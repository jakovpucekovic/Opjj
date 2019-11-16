package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * 	Class which represents a simple dictionary
 * 	which enables the storage of pairs of keys and values.
 * 	Each key must be unique and not-null.
 * 	
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Dictionary<K,V> {
	
	/** The pairs of values and keys are stored here.*/
	private ArrayIndexedCollection<Pair<K, V>>  array;
	
	/**
	 *	Class which represents a {@link Pair} of a unique key and value. 
	 */
	private class Pair<P, Q>{
		
		/**Key of the {@link Pair}.*/
		private P key;
		/**Value stored in the {@link Pair}*/
		private Q value;
		
		/**
		 * 	Constructor which creates a new {@link Pair} with the given key and value.
		 * 	@param key The key of of the {@link Pair}.
		 * 	@param value The value of the {@link Pair}.
		 * 	@throws NullPointerException Key can't be <code>null</code>.
		 */
		public Pair(P key, Q value){
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * 	Constructs a new {@link Dictionary}. 
	 */
	public Dictionary() {
		array = new ArrayIndexedCollection<Pair<K,V>>();
	}
	
	/**
	 * 	Checks if the {@link Dictionary} is empty.	
	 * 	@return <code>true</code> if yes, <code>false</code> if not.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * 	Returns the number of pairs stored in this {@link Dictionary}.
	 * 	@return The number of pairs stored in this {@link Dictionary}.
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * 	Removes all stored elements from this {@link Dictionary}.
	 */
	public void clear() {
		array.clear();
	}

	/**
	 * 	Creates a new entry in the {@link Dictionary} with the given key
	 * 	and value. If an entry with the given key exists, changes the value 
	 * 	in that entry to the new given value.
	 * 	@param key The key.
	 * 	@param value The value.
	 * 	@throws {@link NullPointerException} if the given key is <code>null</code>.
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		ElementsGetter<Pair<K, V>> elGetter = array.createElementsGetter();
		while(elGetter.hasNextElement()) {
			Pair<K, V> currentElement = elGetter.getNextElement(); 
			if(currentElement.key == key) {
				currentElement.value = value;
				return;
			}
		}
		array.add(new Pair<K, V>(key, value));
	}
	
	/**
	 * 	Returns the value saved with the given key.
	 * 	@param The key whose value should be returned.
	 * 	@return The value stored with the given key or <code>null</code> if such a key
	 * 			doesn't exist.
	 */
	V get(Object key) {
		ElementsGetter<Pair<K, V>> elGetter = array.createElementsGetter();
		while(elGetter.hasNextElement()) {
			Pair<K, V> currentElement = elGetter.getNextElement(); 
			if(currentElement.key == key) {
				return currentElement.value;
			}
		}
		return null;
	}
}
