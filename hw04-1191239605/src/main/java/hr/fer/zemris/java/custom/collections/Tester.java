package hr.fer.zemris.java.custom.collections;

/**
 * 	Interface which represents an object capable of
 * 	testing objects.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 2.0
 */
public interface Tester<T> {

	/**
	 *	Perform some kind of test on the given object.
	 *	@param obj The object on which the test is performed.
	 *	@return <code>true</code> if test was successful, <code>false</code> if not. 
	 */
	boolean test(T obj);
}
