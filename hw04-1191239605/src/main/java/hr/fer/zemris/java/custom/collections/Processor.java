package hr.fer.zemris.java.custom.collections;

/**
 * 	Interface which represents an object capable of
 * 	performing some operation on the passed object.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 2.0
 */
public interface Processor<T> {

	/**
	 *	Method which processes the given value.
	 *	@param value The value to process. 
	 */
	abstract void process(T value);
	
}
