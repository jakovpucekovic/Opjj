package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 	A class that functions as an double linked list. It can store and
 *	manipulate stored objects.
 *
 *	@author Jakov Pucekovic
 *	@version 1.1
 */
public class LinkedListIndexedCollection implements List {

	/**
	 * 	A class that simulates a node in the double linked list.
	 * 	It holds the value and the references to the previous and
	 * 	next node.
	 */
	private static class ListNode{
		ListNode previous;
		ListNode next;
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
		
		/**
		 * 	Constructs an empty {@link ListNode} and assigns null to all
		 * 	values.
		 */
		public ListNode() {
			this(null, null, null);
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	private long modificationCount = 0;
	
	/**
	 * 	Constructs an empty {@link LinkedListIndexedCollection}.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
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
		ListNode node = first;
		while(node != last) {
			if(node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}
		if(last.value.equals(value)) {
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
		if(!contains(value)) {
			return false;
		}
		if(first == last) {
			first.next = null;
			first = null;
			last.previous = null;
			last = null;
			size--;
			return true;
		}
		if(first.value.equals(value)) {
			first = first.next;
			first.previous = null;
			size--;
			return true;
		}
		ListNode node = first.next;
		while(node != last) {
			if(node.value.equals(value)) {
				node.previous.next = node.next;
				node.next.previous = node.previous;
				size--;
				return true;
			}
			node = node.next;
		}
		last = last.previous;
		last.next = null;
		size--;
		modificationCount++;
		return true;
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
		modificationCount++;
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
		modificationCount++;
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
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
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
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if(position == size) {
			add(value);
		} else if(position == 0) {
			first = new ListNode(null, first, value);
			first.next.previous = first;
			size++;
			modificationCount++;
		} else {
			ListNode node = listNodeAtPosition(position);
			ListNode newNode = new ListNode(node.previous, node, value);
			node.previous.next = newNode;
			node.previous = newNode;
			size++;
			modificationCount++;
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
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode node = listNodeAtPosition(index);
		node.previous.next = node.next;
		node.next.previous = node.previous;
		size--;
		modificationCount++;
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
		if(position < 0 || position > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if(position > size/2) {
			ListNode node = last;
			int counter = size - 1;
			while(counter != position) {
				node = node.previous;
				counter--;
			}
			return node;
		} else {
			ListNode node = first;
			int counter = 0;
			while(counter != position) {
				node = node.next;
				counter++;
			}
			return node;
		}
	}
	
	/**
	 * 	Implementation of {@link ElementsGetter} for this Collection.
	 */
	private static class LinkedListElementsGetter implements ElementsGetter{
		
		/**Next element to get*/
		private ListNode current = null;
		/**Number of modifications made to the {@link LinkedListIndexedCollection}*/
		private long savedModificationCount;
		/**Reference to {@link LinkedListIndexedCollection} which made this {@link ElementsGetter}.*/
		LinkedListIndexedCollection thisCollection;
		
		/**
		 *  Constructs a new {@link LinkedListElementsGetter} for this {@link LinkedListIndexedCollection}.
		 *  @param thisCollection {@link LinkedListIndexedCollection} on which this {@link ElementsGetter} gets elements.
		 */
		public LinkedListElementsGetter(LinkedListIndexedCollection thisCollection) {
			current = thisCollection.first;
			this.savedModificationCount = thisCollection.modificationCount;
			this.thisCollection = thisCollection;
		}
		
		/**
		 * 	Answers whether there is another element.
		 * 	@return <code>true</code> if yes, <code>false</code> if no.
		 * 	@throws ConcurrentModificationException If the collection has been modified in the meanwhile.
		 */
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != thisCollection.modificationCount ) {
				throw new ConcurrentModificationException();
			}
			return current != null;
		}
		
		/**
		 * 	Returns the next element.
		 * 	@return Next element.
		 * 	@throws ConcurrentModificationException If the collection has been modified in the meanwhile.
		 * 	@throws NoSuchElementException If there are no more elements.
		 */
		@Override
		public Object getNextElement() {
			if(savedModificationCount != thisCollection.modificationCount ) {
				throw new ConcurrentModificationException();
			}
			if(current == null) {
				throw new NoSuchElementException();
			}
			Object returnValue = current.value;
			current = current.next;
			return returnValue;
		}
	
	}
	
	/**
	 * 	Creates an {@link ElementsGetter} for this {@link Collection}.
	 * 	@return {@link ElementsGetter} for this {@link Collection}.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}
	
}
