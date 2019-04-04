package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 	Class that represents a hash table. Each value is stored with 
 * 	a unique non-null key.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	/**
	 * 	Class which represents an entry of a key 
	 * 	and value in {@link SimpleHashtable}.
	 */
	public static class TableEntry<K,V>{
		
		/**The key is stored here.*/
		private K key;
		/**The value is stored here.*/
		private V value;
		/**Next {@link TableEntry}.*/
		TableEntry<K,V> next;
	
		/**
		 * 	Constructs a new {@link TableEntry} with the given values.
		 * 	@param key The key.
		 * 	@param value The value.
		 * 	@param next Next {@link TableEntry} in linked list.
		 */
		public TableEntry(K key, V value, TableEntry<K,V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * 	Returns the value of the {@link TableEntry}.
		 * 	@return The value of the {@link TableEntry}.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * 	Sets the value of the {@link TableEntry} to the given value.
		 * 	@param value The value to set the value in {@link TableEntry} to.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * 	Returns the key of the {@link TableEntry}.
		 * 	@return The key of the {@link TableEntry}.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * 	Returns a {@link String} representation of the {@link TableEntry}
		 * 	in format "key=value".
		 * 	@return {@link String} representation of the {@link TableEntry}.
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}
	
	/**
	 *	The default size of the {@link SimpleHashtable}
	 *	if there isn't a capacity given when a new
	 *	{@link SimpleHashtable} is constructed. 
	 */
	private static final int DEFAULT_SIZE = 16;
	/**Actual storage of {@link TableEntry}s.*/
	private TableEntry<K,V>[] table;
	/**Number of stored elements. */
	private int size;
	/**The number of modifications(insertions, deletions,...) made.*/
	private int modificationCount;
	
	/**
	 * 	Constructs a new {@link SimpleHashtable} with
	 * 	the default size.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K,V>[])new TableEntry[DEFAULT_SIZE];
	}
	
	/**
	 * 	Constructs a new {@link SimpleHashtable} with the size being
	 * 	the smallest power of 2 which is bigger than or equal to the given number.
	 * 	@param capacity The size of the {@link SimpleHashtable}.
	 * 	@throws IllegalArgumentException If given capacity is < 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be smaller that 1.");
		}
		int actualSize = 1;
		while(actualSize < capacity) {
			actualSize *=2;
		}
		
		table = (TableEntry<K,V>[]) new TableEntry[actualSize];
	}
	
	/**
	 * 	Returns the number of elements currently stored.
	 * 	@return The number of elements currently stored.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * 	Checks whether the {@link SimpleHashtable} has any elements
	 * 	stored.	
	 * 	@return <code>true</code> if there are no elements, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 	Removes all elements from the {@link SimpleHashtable}.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * 	Checks whether the {@link SimpleHashtable} contains
	 * 	the given key.
	 * 	@param key The key to check.
	 * 	@return <code>true</code> if yes, <code>false</code> if no.
	 *  @throws NullPointerException If the given key is <code>null</code>.
	 */
	public boolean containsKey(Object key) {
		Objects.requireNonNull(key);
		return findEntryFromKey(key) != null;
	}

	/**
	 * 	Checks whether the {@link SimpleHashtable} contains
	 *	the given value.
	 *	@param value The value to check.
	 *	@return <code>true</code> if yes, <code>false</code> if no.
	 */
	public boolean containsValue(Object value) {
		IteratorImpl iterator = new IteratorImpl();
		while(iterator.hasNext()) {
			if(iterator.next().value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 	If there is no {@link TableEntry} with the given key adds
	 * 	a new {@link TableEntry} with the given key and value into
	 * 	the {@link SimpleHashtable}. If there is a {@link TableEntry}
	 * 	with the given key, only changes the value of that {@link TableEntry}
	 * 	to match the given value.
	 * 	If the {@link SimpleHashtable} has more elements that 75% of its 
	 * 	capacity, doubles the capacity of the {@link SimpleHashtable}.
	 * 	@param key	The key.
	 * 	@param value The value.
	 */
	public void put(K key, V value) {
		if((double)size / table.length >= 0.75 ) {
			reallocate();
		}
		if(addToTable(key, value, table)) {
			size++;
			modificationCount++;			
		}
	}

	/**
	 * 	Return the value stored with the given key or <code>null</code>
	 * 	if there is no {@link TableEntry} with the given key.
	 * 	@param key The key whose value should be returned.
	 * 	@return The value stored with the given key or <code>null</code>
	 * 			if key non existent.
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		TableEntry<K,V> temp = findEntryFromKey(key);
		if(temp != null) {
			return temp.value;
		} else {
			return null;
		}
	}

	/**
	 * 	Removes the {@link TableEntry} with the given key from 	
	 * 	the {@link SimpleHashtable}. Does nothing if there is
	 * 	no {@link TableEntry} with the given key.	
	 * 	@param key The key of the {@link TableEntry} to remove.
	 * 	@throws NullPointerException If the given key is <code>null</code>.
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		int index = hash(key);
		if(table[index] == null) {
			return;
		}
		/*Entry is first in this slot*/
		if(table[index].key.equals(key)) {
			/*Entry is the only entry in this slot*/
			if(table[index].next == null) {
				table[index] = null;
			/*Entry isn't the only entry in this slot.*/
			}else {
				table[index] = table[index].next;
			}
			size--;
			modificationCount++;
		/*Entry isn't first in this slot.*/
		} else {
			TableEntry<K,V> entry = table[index];
			/*Find entry in this slot.*/
			while(entry.next != null && !entry.next.key.equals(key)) {
				entry = entry.next;
			}
			/*Entry is last.*/
			if(entry.next == null) {
				return;
			}
			/*Entry isn't last.*/
			if(entry.next.key.equals(key)) {
				entry.next = entry.next.next;
				size--;
				modificationCount++;
			}
		}
	}
	
	/**
	 * 	Returns a {@link String} representation of the {@link SimpleHashtable}
	 * 	in the format [key1=value1, key2=value2, ...].
	 * 	@return The string representation.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append('[');
		for(int i = 0; i < table.length ; i++) {
			TableEntry<K,V> entry = table[i];
			while(entry != null) {
				str.append(entry.toString()).append(", ");
				entry = entry.next;
			}
		}
		str.delete(str.length()-2, str.length());
		str.append(']');
		return str.toString();
	}
	
	/**
	 * 	Returns an iterator which can iterate over the {@link SimpleHashtable}.
	 * 	@return An iterator which can iterate over the {@link SimpleHashtable}.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * 	Performs the hashing of the keys. Does so by taking the
	 * 	remainder of the division of the hashCode of the key by the
	 * 	capacity of the {@link SimpleHashtable}.
	 * 	@param key The key which needs to be hashed.
	 * 	@return The hash of the key.
	 */
	private int hash(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 *  Method which doubles the size of the {@link SimpleHashtable}
	 *  and moves all elements from the old one to the new one.
	 */
	@SuppressWarnings("unchecked")
	private void reallocate() {
		TableEntry<K, V>[] newTable = (TableEntry<K,V>[]) new TableEntry[table.length * 2];

		Iterator<TableEntry<K,V>> tableIterator = iterator();
		
		while(tableIterator.hasNext()) {
			TableEntry<K,V> toAdd = tableIterator.next();
			addToTable(toAdd.key, toAdd.value, newTable);
		}
		
		table = newTable;
	}

	/**
	 * 	Finds the {@link TableEntry} with the given key 
	 * 	in this {@link SimpleHashtable}.
	 * 	@param key The key of the {@link TableEntry} to find.
	 * 	@return The {@link TableEntry} with the given key or <code>null</code>
	 * 			if such {@link TableEntry} doesn't exist.
	 */
	private TableEntry<K,V> findEntryFromKey(Object key){
		if(key == null) {
			return null;
		}
		int index = hash(key);
		if(table[index] == null) {
			return null;
		}
		/*Entry is first in the slot.*/
		if(table[index].key.equals(key)) {
			return table[index];
		/*Find the entry in the slot.*/
		} else {
			TableEntry<K,V> find = table[index].next;
			while(find != null && !find.key.equals(key)) {
				find = find.next;
			}
			if(find == null) {
				return null;
			} else {
				return find;
			}
		}
	}
	
	/**
	 * 	Method which actually adds the given {@link TableEntry} to the given table.
	 *	@param key The key to add.
	 *	@param value The value to add.
	 *	@param tableWhereToAdd The internal array where {@link TableEntry}s are stored.
	 *	@return <code>true</code> if a new {@link TableEntry} was added, <code>false</code>
	 *			if the value of a existing {@link TableEntry} was modified.
	 *	@throws NullPointerException If the key in the {@link TableEntry} is <code>null</code>.
	 */
	private boolean addToTable(K key, V value, TableEntry<K,V>[] tableWhereToAdd ) {
		Objects.requireNonNull(key);
		int index = Math.abs(key.hashCode()) % tableWhereToAdd.length;
		
		if(tableWhereToAdd[index] == null ) {
			tableWhereToAdd[index] = new TableEntry<>(key, value, null);
			return true;
		} else {
			TableEntry<K,V> entry = tableWhereToAdd[index];
			if(entry.key.equals(key)) {
				entry.value = value;
				return false;
			}
			while(entry.next != null) {
				if(entry.next.key.equals(key)) {
					entry.value = value;
					return false;
				}
				entry = entry.next;
			}
			entry.next = new TableEntry<>(key, value, null);
			return true;
		}
	}
	
	/**
	 * 	Class which allows the iteration through the {@link SimpleHashtable}.
	 */	
	public class IteratorImpl implements Iterator<TableEntry<K,V>>{

		/**The current index in the array of {@link TableEntry}s.*/
		private int currentIndex;
		/**The last returned {@link TableEntry}.*/
		private TableEntry<K,V> currentEntry;
		/**Modification count of the {@link SimpleHashtable} at the moment
		 * this {@link IteratorImpl} was created.*/
		private int initialModificationCount;
		
		/**
		 * 	Constructs a new {@link IteratorImpl} with the modification count
		 * 	of the {@link SimpleHashtable}.
		 */
		public IteratorImpl() {
			initialModificationCount = modificationCount;
		}
		
		/**
		 * 	Checks if there is another {@link TableEntry} if the {@link SimpleHashtable}.
		 * 	@return <code>true</code> if yes, <code>false</code> otherwise.
		 * 	@throws ConcurrentModificationException If the {@link SimpleHashtable} has been changed outside of this {@link IteratorImpl}.
		 */
		@Override
		public boolean hasNext() {
			if(initialModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			/*Only runs the first time hasNext() is called.*/
			if(currentEntry == null) {
				int ind = currentIndex;
				while(ind < table.length) {
					if(table[ind] != null) {
						return true;
					}
					ind++;
				}
				return false;
			}
			
			if(currentEntry.next != null) {
				return true;
			}
			
			/*Find next non empty slot.*/
			int ind = currentIndex;
			while(ind + 1 < table.length) {
				ind++;
				if(table[ind] != null) {
					return true;
				}
			}
			return false;
		}	
		
		/**
		 * 	Returns the next {@link TableEntry}.
		 * 	@return The next {@link TableEntry}.
		 * 	@throws NoSuchElementException When there are no more elements.
		 */
		@Override
		public TableEntry<K,V> next() {
			if(initialModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			/*Only runs the first time next() is called.*/
			if(currentEntry == null) {
				while(currentIndex < table.length) {
					if(table[currentIndex] != null) {
						currentEntry = table[currentIndex];
						return currentEntry;
					}
					currentIndex++;
				}
				throw new NoSuchElementException();
			}
			/*Find next non empty slot.*/
			if(currentEntry.next == null ) {
				while(currentIndex + 1 < table.length) {
					currentIndex++;
					if(table[currentIndex] != null) {
						currentEntry = table[currentIndex];
						return currentEntry;
					}
				}
				throw new NoSuchElementException();
			} else {
				currentEntry = currentEntry.next;
				return currentEntry;
			}
		}
	
		/**
		 * 	Deletes the last {@link TableEntry} given with the
		 * 	method next() from the {@link SimpleHashtable}.
		 * 	@throws IllegalStateException If there is no {@link TableEntry} to delete, e.g. it was already deleted.
		 * 	@throws ConcurrentModificationException If the {@link SimpleHashtable} has been changed outside of this {@link IteratorImpl}.
		 */
		@Override
		public void remove() {
			if(initialModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if(currentEntry == null) {
				throw new IllegalStateException();
			}
			SimpleHashtable.this.remove(currentEntry.key);
			currentEntry = null;
			initialModificationCount++;
//			if(initialModificationCount != modificationCount) {
//				throw new ConcurrentModificationException();
//			}
//			if(currentEntry == null) {
//				throw new IllegalStateException();
//			}
//			int ind = hash(currentEntry.key);
//			if(table[ind].equals(currentEntry)) {
//				if(currentEntry.next == null) {
//					table[ind] = null;
////					currentEntry = null;
//				} else {
//					table[ind] = currentEntry.next;
//				}
//				
//			} else {
//				TableEntry<K,V> entry = table[ind];
//				//finds the entry before the currentEntry
//				while(!entry.next.equals(currentEntry)) {
//					entry = entry.next;
//				}
//				if(currentEntry.next != null) {
//					entry.next = currentEntry.next;
//				} else {
//					entry.next = null;
//				}
//			}
//			currentEntry = null;
//			size--;
//			modificationCount++;
//			initialModificationCount++;
		}
		
	}
	
}
