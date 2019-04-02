package hr.fer.zemris.java.custom.collections.copy;
/**
 *	A class that represents some general collection of objects.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Collection {
	
	/**
	 *	Constructs a new {@link Collection}. Does nothing here.
	 */
	protected Collection() {
	}
	
	/**
	 * 	Checks if this {@link Collection} contains objects.
	 * 	@return True if the collection is empty, false if not.
	 */
	public boolean isEmpty() {
		return !(size() > 0);
	}
	
	/**
	 * 	Returns the number of objects currently stored in this {@link Collection}.
	 * 	@return Always return 0.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 *	Adds the given object into this {@link Collection}. 
	 *	Does nothing here.
	 *	@param value Object which needs to be added to this {@link Collection}.
	 */
	public void add(Object value) {
	}
	
	/**
	 * 	Checks if this {@link Collection} contains a given value.
	 * 	@param	value Object for which we want to know if it's contained
	 * 				  in this {@link Collection}.
	 * 	@return	True if this {@link Collection} contains the value,
	 * 			false otherwise. Always false here.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * 	Checks if this {@link Collection} contains the value as 
	 * 	determined by equals method and removes one occurrence
	 *  of it(not specified which one).
	 *  @param value The value to remove from this {@link Collection}.
	 * 	@return True if the value is found and removed, false 
	 * 			if the value is not found. Always false here.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * 	Allocates a new array with size equal to size of this {@link Collection}
	 * 	and fills it with collection content.
	 * 	@return The newly allocated array. Never returns null.
	 * 	@throws UnsupportedOperationException Always throws UnsupportedOperationException here. 
	 */
	public Object[] toArray() {
		throw  new UnsupportedOperationException();
	}
	
	/**
	 *	Calls processor.process() for each element of this {@link Collection}.
	 *	Does nothing here.
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * 	Method adds all elements from the given {@link Collection}
	 * 	into this {@link Collection}. The other {@link Collection} 
	 *  remains unchanged.
	 *  @param other The {@link Collection} whose elements need to 
	 *  			 be added.
	 */
	public void addAll(Collection other) {
		class MyProcessor extends Processor{
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new MyProcessor());
	}
	
	/**
	 * 	Removes all elements from this {@link Collection}. 
	 * 	Does nothing here.
	 */
	public void clear() {
	}
}
