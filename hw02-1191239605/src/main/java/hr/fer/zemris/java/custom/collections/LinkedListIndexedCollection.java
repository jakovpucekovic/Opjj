package hr.fer.zemris.java.custom.collections;

public class LinkedListIndexedCollection extends Collection {

	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object value;
		
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
		public ListNode() {
			this(null, null, null);
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;

	
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	
	public LinkedListIndexedCollection(Collection collection) {
		this();
		first = new ListNode();
		last = first;
		this.addAll(collection);
		first = first.next;
		first.previous = null;
	}
	
	@Override
	public int size() {
		return this.size; 
	}
	
	@Override
	public boolean contains(Object value) {
		ListNode iterator = first;
		while(iterator != last) {
			if(iterator.value.equals(value)) {
				return true;
			}
			iterator = iterator.next;
		}
		if(last.value.equals(value)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		if(!this.contains(value)) {
			return false;
		}
		ListNode iterator = first;
		while(iterator != last) {
			if(iterator.value.equals(value)) {
				iterator.previous.next = iterator.next;
				iterator.next.previous = iterator.previous;
				return true;
			}
			iterator = iterator.next;
		}
		//ako nije niti jedan prije ocito je zadnji;
		last = last.previous;
		last.next = null;
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] objectArray = new Object[this.size];
		ListNode iterator = first;
		for(int i = 0, s = this.size; i < s; i++) {
			objectArray[i] = iterator.value;
			iterator = iterator.next;
		}
		return objectArray;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		for(int i = 0, s = size; i < s; i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	
	@Override
	public void clear() {
		ListNode node = first, nextNode=first.next;
		while(nextNode != last) {
			node.previous = null;
			node.next = null;
			node = nextNode;
			nextNode = nextNode.next;
		}
		node.previous = null;
		size = 0;
	}
	
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		last.next = new ListNode(last, null, value);
		last = last.next;
		size++;
	}
	
	public Object get(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return listNodeAtPosition(index).value;
	}
	
	public void insert(Object value, int position) {
		if(position < 0 || position > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode node = listNodeAtPosition(position);
		ListNode newNode = new ListNode(node, node.next, value);
		node.next.previous = newNode;
		node.next = newNode;
		size++;
	}
	
	private ListNode listNodeAtPosition(int position) {
		if(position > size/2) {
			ListNode node = last;
			int counter = this.size - 1;
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
	//moze se dodati da ide u oba smjera odjednom
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

	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode node = listNodeAtPosition(index);
		node.previous.next = node.next;
		node.next.previous = node.previous;
		size--;
	}
	
	private void print() {
		ListNode node = first;
		for(int i = 0, s = size; i < s; i++) {
			System.out.println(node.value);
			node = node.next;
		}
	}
	
//	private boolean checkIndex(int index) {
//		if(index < 0 || index > size - 1) {
//			throw new IndexOutOfBoundsException();
//		}
//	}
}
