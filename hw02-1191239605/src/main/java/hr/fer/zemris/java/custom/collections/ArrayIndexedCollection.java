package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 *	A class that functions as an array. It can store and
 *	manipulate stored Objects.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 *	The default size of the {@link ArrayIndexedCollection}
	 *	if there isn't a initialCapacity given when a new
	 *	{@link ArrayIndexedCollection} is constructed. 
	 */
	private static final int DEFAULT_SIZE = 16;
	
	private int size;
	private Object[] elements;
	
	/**
	 *	Constructs an {@link ArrayIndexedCollection} with the 
	 *	specified size and adds the elements from the collection
	 *	into this {@link ArrayIndexedCollection}. If the given capacity 
	 *	is smaller that the size of the given collection, the size of the 
	 *	constructed {@link ArrayIndexedCollection} will be the bigger of those
	 *	two.
	 *	@param collection Collection whose elements should be added.
	 *	@param initialCapacity Initial capacity of this {@link ArrayIndexedCollection}.
	 *	@throws NullPointerException If the given collection is null.
	 *	@throws IllegalArgumentException If the given initialCapacity is < 1.
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
			Objects.requireNonNull(collection);
			if(initialCapacity < 1) {
				throw new IllegalArgumentException();
			}
			
			elements = collection.size() > initialCapacity ? new Object[collection.size()] : new Object[initialCapacity];
			size = 0;
			this.addAll(collection);	
	}
	
	/**
	 *	Constructs an {@link ArrayIndexedCollection} with the 
	 *	default size and adds the elements from the collection
	 *	into this {@link ArrayIndexedCollection}. If the default size 
	 *	is smaller that the size of the given collection, the size of the 
	 *	constructed {@link ArrayIndexedCollection} will be the bigger of those
	 *	two.
	 *	@param collection Collection whose elements should be added.
	 *	@throws NullPointerException If the given collection is null.
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, DEFAULT_SIZE);
	}

	/**
	 *	Constructs an {@link ArrayIndexedCollection} with the 
	 *	specified size. 
	 *	@param initialCapacity Initial capacity of this {@link ArrayIndexedCollection}.
	 *	@throws IllegalArgumentException If the given initialCapacity is < 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 *	Constructs an {@link ArrayIndexedCollection} with the 
	 *	default size.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * 	Returns the number of currently stored objects in this ArrayIndexedCollection.
	 * 	@return Number of currently stored objects in this ArrayIndexedCollection.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * 	Ads the given object into the first empty place in this ArrayIndexedCollection.
	 * 	Average complexity is O(1), except when the array needs to be reallocated,
	 * 	in that case it's O(n).
	 * 	@param value The value to be added into the array. Can't be null.
	 * 	@throws NullPointerException If the given value is null.
	 */
	@Override
	public void add(Object value) {
		insert(value, size);			
	}
	
	/**
	 * 	Checks if the ArrayIndexedCollection contains a given value.
	 * 	@return True if the ArrayIndexedCollection contains the value, false otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		if(indexOf(value) == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 	Checks if the ArrayIndexedCollection contains the value as 
	 * 	determined by equals method and removes the first occurrence
	 *  of it.
	 *  @param value The value to remove from the ArrayIndexedCollection.
	 * 	@return True if the value is found and removed, false 
	 * 			if the value is not found.
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index != -1) {
			remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * 	Allocates a new array with size equal to the size of this 
	 * 	ArrayIndexedCollection and fills it with the content of this 
	 * 	ArrayIndexedCollection.
	 * 	@return The newly allocated array. Never returns null.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	/**
	 *	Calls processor.process() for each element of 
	 *	this ArrayIndexedCollection.
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0, s = size; i < s; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * 	Removes all elements from this ArrayIndexedCollection.
	 */
	@Override
	public void clear() {
		for(int i = 0, s = size; i < s; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * 	Returns the object that is stored in this ArrayIndexedCollection
	 * 	at position index.
	 *  Method has O(1) complexity.
	 * 	@param index The index of the object. Valid indexes are 0 to size - 1.
	 * 	@return The object stored at the given index.	
	 * 	@throws IndexOutOfBoundsException If index isn't in the valid range from 0 to size - 1.
	 */
	public Object get(int index) {
		Objects.checkIndex(index, size);
		return elements[index];
	}
	
	/**
	 * 	Inserts the given value at the given position in this 
	 * 	ArrayIndexedCollection and shifts the elements at position 
	 * 	and greater position one place towards the end.
	 * 	The average complexity of this method is O(n).
	 * 	@param value The value to be inserted.
	 * 	@param position The position at witch the value should be inserted. 
	 * 					Valid positions are from 0 to size.
	 * 	@throws NullPointerException If value is null.
	 * 	@throws IndexOutOfBoundsException If index isn't in the valid range from 0 to size.
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);
		Objects.checkIndex(position, size + 1);
		if(size == elements.length) {
			elements = Arrays.copyOf(elements, size * 2);
		}
		for(int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;		
	}
	
	/**
	 *	Searches this ArrayIndexedCollection and returns the 
	 *	index of the first occurrence of the given value or -1 
	 *	if the value is not found. The equality is determined 
	 *	using the equals method. 
	 *	Average complexity is O(n).
	 * 	@param value The value whose index should be found.
	 * 	@return Index of the first occurrence of the value or -1 if
	 * 			value not found or null.
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		for(int i = 0, s = size; i< s; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 	Removes element at specified index from ArrayIndexedCollection.
	 *  Element that was previously at location index + 1 after this
	 *  operation is on location index. Legal indexes are 0 to size - 1.
	 *  @param index The index of the element that should be removed.
	 *  @throws IndexOutOfBoundsException If index isn't in the valid range from 0 to size - 1.
	 */
	public void remove(int index) {
		Objects.checkIndex(index, size);
		for(int i = index, s = size - 1; i < s; i++) {
			elements[i] = elements[i+1];
		}
		elements[size - 1] = null;
		size--;
	}
	
}
