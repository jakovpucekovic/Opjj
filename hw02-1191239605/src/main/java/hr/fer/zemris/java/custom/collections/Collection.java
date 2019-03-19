package hr.fer.zemris.java.custom.collections;
/**
 *	A class that represets some general collection of objects.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Collection {
	
	protected Collection() {
		
	}
	
	/**
	 * 	Checks if the collection contains no objects.
	 * 	@return true if the collection is empty, false if not.
	 */
	public boolean isEmpty() {
		return !(size() > 0);
	}
	
	/**
	 * 	Returns the number of objects currently stored in the collection.
	 * 	@return 0 Always.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 *	Adds the given object into this collection. 
	 *	Does nothing.
	 */
	public void add(Object value) {}
	
	/**
	 * 	Checks if the collection contains a given value.
	 * 	@return false Always.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * 	Removes the objects from the collection.
	 * 	@return false Always.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * 	Allocates a new array with size equal to size of this collection
	 * 	and fills it with collection content.
	 * 	@return The array.
	 * 	@throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw  new UnsupportedOperationException();
	}
	
	/**
	 *	Calls processor.process(). Does nothing here.
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * 	Adds all elements from the given collection into this collection.
	 */
	public void addAll(Collection other) {
		class MyProcessor extends Processor{
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		MyProcessor proc = new MyProcessor();
		
		other.forEach(proc);
		
	}
	
	/**
	 * 	Removes all elements from this collection. Does nothing here.
	 */
	public void clear() {
		
	}
	
	
	//jel treba i kako napraviti toString(), equals() i hashCode() NE.

}
