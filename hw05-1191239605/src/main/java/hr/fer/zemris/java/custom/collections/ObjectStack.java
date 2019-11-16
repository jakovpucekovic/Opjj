package hr.fer.zemris.java.custom.collections;

/**
 * 	A stack-like collection which allows users to store objects.
 * 	It's a LIFO (last in first out) structure.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 3.0
 */
public class ObjectStack<T> {
	
	/**
	 * 	The array which is used to store the values.
	 */
	private ArrayIndexedCollection<T> array;
	
	/**
	 * 	Creates an empty {@link ObjectStack}.
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection<>();
	}
	
	/**
	 * 	Checks whether the {@link ObjectStack} is empty.
	 * 	@return true if empty, false if not.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * 	Returns the number of elements in this {@link ObjectStack}.
	 * 	@return Size of the {@link ObjectStack}.
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * 	Pushes the given value on this {@link ObjectStack}. 
	 * 	@param value The value to be placed on this {@link ObjectStack}. 
	 * 				 Cannot be null.
	 * 	@throws NullPointerException If the given value is null.
	 */
	public void push(T value) {
		array.add(value);
	}
	
	/**
	 * 	Removes last value pushed on this {@link ObjectStack} from
	 * 	this {@link ObjectStack} and returns it.
	 * 	@return The last value placed on this {@link ObjectStack}.
	 * 	@throws EmptyStackException If this method is called upon an empty {@link ObjectStack}.
	 */
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		T value = array.get(array.size() - 1);
		array.remove(array.size() - 1);
		return value;
	}
	
	/**
	 * 	Returns the last value places on this {@link ObjectStack}, but
	 * 	does NOT remove it from this {@link ObjectStack}.
	 * 	@return The last value placed on this {@link ObjectStack}.
	 * 	@throws EmptyStackException If this method is called upon an empty {@link ObjectStack}.
	 */
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		return array.get(array.size() - 1);
	}
	
	/**
	 * 	Removes all elements from this {@link ObjectStack}.
	 */
	public void clear() {
		array.clear();
	}
}
