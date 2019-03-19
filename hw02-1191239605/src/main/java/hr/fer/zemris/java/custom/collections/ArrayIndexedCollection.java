package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

public class ArrayIndexedCollection extends Collection {

	private static final int defaultSize = 16;
	private static int capacity;
	private int size;
	private Object[] elements;
	
	
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		{
			if(collection == null) {
				throw new NullPointerException();
			}
			if(initialCapacity < 1) {
				throw new IllegalArgumentException();
			}
			if(collection.size() > initialCapacity) {
				capacity = collection.size();
			}
			else {
				capacity = initialCapacity;
			}
			elements = new Object[capacity];
			size = 0;
			this.addAll(collection);
		}
	}

	public ArrayIndexedCollection(Collection collection) {
		this(collection, defaultSize);
	}
	//kako bolje napisati donji konstruktor
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		capacity = initialCapacity;
		elements = new Object[capacity];
		size = 0;
	}
	
	public ArrayIndexedCollection() {
		this(defaultSize);
	}
	
	@Override
	public void add(Object value) {
		insert(value, this.size);
//		if(value == null) {
//			throw new NullPointerException();
//		}
//		if(this.size == capacity) {
//			capacity *= 2;
//			this.reallocate();
//		}
//		this.elements[size] = value;
//		this.size++;			
	}
	//moja pomocna fja
	private void reallocate() {
		Object[] newArray =  new Object[this.size*2];
		for(int i = 0, s = this.size; i < s; i++) {
			newArray[i] = this.elements[i];
		}
		this.elements = newArray;
	}
	
	public Object get(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0, s = this.size; i < s; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		if(this.size == capacity) {
			capacity *= 2;
			this.reallocate();
		}
		
		for(int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i-1];
		}
		this.elements[position] = value;
		this.size++;		
	}
	
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		for(int i = 0, s = this.size; i< s; i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index, s = this.size - 1; i < s; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.elements[this.size - 1] = null;
		this.size--;
	}
	
	//potencijalno netreba jer isEmpty ima definiciju u Collection
	@Override
	public boolean isEmpty() {
		return this.size > 0 ? false : true;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0, s = this.size; i < s; i++) {
			if(this.elements[i].equals(value)) {
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
		Object[] array = new Object[this.size];
		for(int i = this.size - 1; i >= 0; i--) {
			array[i] = this.elements[i];
		}
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0, s = this.size; i < s; i++) {
			processor.process(this.elements[i]);
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
		for(int i = 0; i < this.size; i++) {
			System.out.format("%d%n", this.elements[i]);
		}
	}
	
}
