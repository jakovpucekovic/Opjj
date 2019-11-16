package hr.fer.zemris.java.custom.collections;

/**
 * 	Interface which represents an object that
 * 	behaves like a list.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 2.0
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * 	Returns the element at the given index.
	 * 	@param index Index of element to return.
	 * 	@return Element at the given index.
	 */
	T get(int index);
	
	/**
	 * 	Inserts the given value at the given position.
	 * 	Does not overwrite it.
	 *  @param value Value to insert.
	 *  @param position Position to insert the value to.
	 */
	void insert(T value, int position);
	
	/**
	 * 	Returns the position of the given value in the {@link List}.
	 * 	@return Position of the given element, -1 if the value is not in the {@link List}.
	 */
	int indexOf(Object value);
	
	/**
	 *  Removes the element with the given index from the {@link List}.
	 *  @param index Index of element to remove.
	 */
	void remove(int index);
	
}
