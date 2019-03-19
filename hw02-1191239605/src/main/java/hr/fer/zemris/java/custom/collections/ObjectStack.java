package hr.fer.zemris.java.custom.collections;

public class ObjectStack {

	private ArrayIndexedCollection array;
	
	public ObjectStack() {
		array = new ArrayIndexedCollection();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	public int size() {
		return array.size();
	}
	
	public void push(Object value) {
		array.add(value);
	}
	
	//treba dodati empty stack exception kad se poziva na praznom stacku
	public Object pop() {
		Object value = array.get(array.size() - 1);
		array.remove(array.size() - 1);
		return value;
	}
	
	public Object peek() {
		return array.get(array.size() - 1);
	}
	
	public void clear() {
		array.clear();
	}
	
	
}
