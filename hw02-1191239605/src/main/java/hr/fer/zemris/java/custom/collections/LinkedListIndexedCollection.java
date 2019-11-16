package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * 	A class that functions as an double linked list. It can store and
 *	manipulate stored objects.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * 	A class that simulates a node in the double linked list.
	 * 	It holds the value and the references to the previous and
	 * 	next node.
	 */
	private static class ListNode{
		/**Previous node in linked list.*/
		ListNode previous;
		/**Next node in linked list.*/
		ListNode next;
		/**Value stored in this node.*/
		Object value;
		
		/**
		 * 	Constructs a {@link ListNode} and assigns it the given values.
		 * 	@param previous A reference to the previous node.
		 * 	@param next A reference to the next node.
		 * 	@param value The value to be assigned to this node.
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
	}
	
	/**Size of this {@link LinkedListIndexedCollection}.*/
	private int size;
	/**First node in this {@link LinkedListIndexedCollection}.*/
	private ListNode first;
	/**Last node in this {@link LinkedListIndexedCollection}.*/
	private ListNode last;

	/**
	 * 	Constructs an empty {@link LinkedListIndexedCollection}.
	 */
	public LinkedListIndexedCollection() {
	}
	
	/**
	 * 	Constructs a new {@link LinkedListIndexedCollection} from and
	 * 	stores the elements of the given collection in this 
	 * 	{@link LinkedListIndexedCollection}. The given collection remains
	 * 	unchanged.
	 * 	@param collection The collection whose values should be stored.
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this();
		addAll(collection);
	}
	
	/**
	 * 	Returns the number of elements currently stored in this {@link LinkedListIndexedCollection}.
	 * 	@return The number of elements currently stored.
	 */
	@Override
	public int size() {
		return size; 
	}
	
	/**
	 * 	Checks if this {@link LinkedListIndexedCollection} contains
	 * 	the given value.
	 * 	@param value The value which should be checked.
	 * 	@return True if the value is found, false otherwise.	
	 */
	@Override
	public boolean contains(Object value) {
		if(isEmpty()) {
			return false;
		}
		if(indexOf(value) != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 	Checks if this {@link LinkedListIndexedCollection} contains
	 * 	the value as determined by equals method and removes the first
	 * 	occurrence of it.
	 *  @param value The value to remove from this {@link LinkedListIndexedCollection}.
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
	 * 	{@link LinkedListIndexedCollection} and fills it with the 
	 * 	content of this {@link LinkedListIndexedCollection}.
	 * 	@return The newly allocated array. Never returns null.
	 */
	@Override
	public Object[] toArray() {
		Object[] objectArray = new Object[size];
		ListNode node = first;
		for(int i = 0, s = size; i < s; i++) {
			objectArray[i] = node.value;
			node = node.next;
		}
		return objectArray;
	}
	
	/**
	 * 	Calls processor.process() for each element of this {@link LinkedListIndexedCollection}.
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		for(int i = 0, s = size; i < s; i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	
	/**
	 * 	Removes all elements from this {@link LinkedListIndexedCollection}.
	 */
	@Override
	public void clear() {
		ListNode node = first, nextNode = first.next;
		while(nextNode != last) {
			node.previous = null;
			node.next = null;
			node = nextNode;
			nextNode = nextNode.next;
		}
		node.previous = null;
		size = 0;
	}
	
	/**
	 * 	Adds the given object into this {@link LinkedListIndexedCollection}
	 * 	at the end of collection. The newly added element becomes the
	 * 	element at the biggest index.
	 * 	The method has the complexity O(1).
	 * 	@param value The value to be added into this {@link LinkedListIndexedCollection}.
	 * 	@throws NullPointerException If the given value is null.
	 */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value);
		if(first == null) {
			first = new ListNode(null, null, value);
			last = first;
		} else {
			last.next = new ListNode(last, null, value);
			last = last.next;
		}
		size++;
	}
	
	/**
	 * 	Returns the object that is stored in this {@link LinkedListIndexedCollection}
	 * 	at position index. Valid indexes are 0 to size - 1. 
	 * 	Average complexity is O(n/2 + 1).
	 * 	@param index The index of the object.
	 * 	@throws IndexOutOfBoundsException If the given index is not in the valid range
	 * 									  from 0 to size - 1.
	 */
	public Object get(int index) {
		Objects.checkIndex(index, size);
		return listNodeAtPosition(index).value;
	}
	
	/**
	 * 	Inserts the given value at the given position in this 
	 * 	{@link LinkedListIndexedCollection}. Elements starting from
	 * 	this position are shifted one position. The legal positions 
	 * 	are from 0 to size.
	 * 	The average complexity is O(n/2 + 1).
	 * 	@param value The value to be inserted.
	 * 	@param position The position at which the value should be inserted.
	 * 	@throws NullPointerException If the given value is null.
	 * 	@throws IndexOutOfBoundsException If the given index is not in the valid range
	 * 									  from 0 to size.
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);
		Objects.checkIndex(position, size + 1);
		if(position == size) {
			add(value);
		} else if(position == 0) {
			first = new ListNode(null, first, value);
			first.next.previous = first;
			size++;
		} else {
			ListNode node = listNodeAtPosition(position);
			ListNode newNode = new ListNode(node.previous, node, value);
			node.previous.next = newNode;
			node.previous = newNode;
			size++;
		}
	}
	
	/**
	 *	Searches the collection and returns the index of the first
	 *	occurrence of the given value or -1 if the value is not found.
	 *	Average complexity is O(n).
	 *	@param value The value to find the index of.
	 *	@return The index of the first occurrence of the value or -1 if value not found.
	 */
	public int indexOf(Object value) {
		if(isEmpty()) {
			return -1;
		}
		ListNode node = first;
		int index = 0;
		while(node != last) {
			if(node.value.equals(value)) {
				return index;
			}
			index++;
			node = node.next;
		}
		if(node.value.equals(value)) {
			return size - 1;
		}
		return -1;
	}

	/**
	 *	Removes element at given index from this {@link LinkedListIndexedCollection}
	 *	and shifts all the elements to fill the empty place. Legal indexes are
	 *	from 0 to size - 1.
	 *	@param index The index of which the value needs to be removed.
	 *	@throws IndexOutOfBoundsException If the index is out of the valid range
	 *									  from 0 to size - 1.
	 */
	public void remove(int index) {
		Objects.checkIndex(index, size);
		ListNode node = listNodeAtPosition(index);
		if(index == 0) {
			if(first == last) {
				first = null;
				last = null;
			} else {
				first = node.next;
				first.previous = null;
			}
		} else if(index == size - 1) {
			last = node.previous;
			last.next = null;
		} else {
			node.previous.next = node.next;
			node.next.previous = node.previous;
		}
		size--;
	}
	
	
	/**
	 * 	Returns the {@link ListNode} at the given position in this {@link LinkedListIndexedCollection}.
	 * 	Legal positions are from 0 to size - 1.
	 * 	Average complexity is O(n/2 + 1).
	 * 	@param position The position in this {@link LinkedListIndexedCollection}.
	 * 	@return ListNode at the given position.
	 *	@throws IndexOutOfBoundsException If the position is out of the valid range
	 *									  from 0 to size - 1.
	 */
	private ListNode listNodeAtPosition(int position) {
		Objects.checkIndex(position, size);
		if(position > size/2) {
			ListNode node = last;
			for(int counter = size - 1; counter > position; counter--) {
				node = node.previous;
			}
			return node;
		} else {
			ListNode node = first;
			for(int counter = 0; counter < position; counter++) {
				node = node.next;
			}
			return node;
		}
	}
}
