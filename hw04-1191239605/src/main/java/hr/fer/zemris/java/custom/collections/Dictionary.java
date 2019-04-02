package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

public class Dictionary<K,V> {
	
	
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
	
	public Dictionary() {
		array = new ArrayIndexedCollection<Pair<K,V>>();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	public int size() {
		return array.size();
	}
	
	public void clear() {
		array.clear();
	}

	public void put(K key, V value) {
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
