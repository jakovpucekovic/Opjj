package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

public class ArrayIndexedCollection extends Collection {

	private static final int DEFAULT_SIZE= 16;
	private int size;
	private Object[] elements;
	
	
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
			if(collection == null) {
				throw new NullPointerException();
			}
			if(initialCapacity < 1) {
				throw new IllegalArgumentException();
			}
			if(collection.size() > initialCapacity) {
				elements = new Object[collection.size()];
			}
			else {
				elements = new Object[initialCapacity];
			}
			size = 0;
			this.addAll(collection);
		
	}

	public ArrayIndexedCollection(Collection collection) {
		this(collection, DEFAULT_SIZE);
	}

	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	public ArrayIndexedCollection() {
		this(DEFAULT_SIZE);
	}
	
	@Override
	public void add(Object value) {
		insert(value, size);			
	}
	
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0, s = size; i < s; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if(size == elements.length) {
			reallocate();
		}
		
		for(int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;		
	}
	
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
	
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index, s = size - 1; i < s; i++) {
			elements[i] = elements[i+1];
		}
		elements[size - 1] = null;
		size--;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0, s = size; i < s; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		if(contains(value)) {
			remove(indexOf(value));
			return true;
		}
		return false;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		for(int i = size - 1; i >= 0; i--) {
			array[i] = elements[i];
		}
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0, s = size; i < s; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public String toString() {
		return "ArrayIndexedCollection [size=" + size + ", elements=" + Arrays.toString(elements) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + Objects.hash(size);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ArrayIndexedCollection))
			return false;
		ArrayIndexedCollection other = (ArrayIndexedCollection) obj;
		return Arrays.deepEquals(elements, other.elements) && size == other.size;
	}
	
	public void print() {
		for(int i = 0; i < size; i++) {
			System.out.format("%d%n", elements[i]);
		}
	}
	
	//moja pomocna fja
	private void reallocate() {
		Object[] newArray =  new Object[elements.length * 2];
		for(int i = 0, s = size; i < s; i++) {
			newArray[i] = elements[i];
		}
		elements = newArray;
	}
	
}
