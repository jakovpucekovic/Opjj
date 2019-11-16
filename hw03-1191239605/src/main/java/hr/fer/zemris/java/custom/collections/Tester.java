package hr.fer.zemris.java.custom.collections;

/**
 * 	Interface which represents an object capable of
 * 	testing objects.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface Tester {

	/**
	 *	Perform some kind of test on the given {@link Object}.
	 *	@param obj The {@link Object} on which the test is performed.
	 *	@return <code>true</code> if test was successful, <code>false</code> if not. 
	 */
	boolean test(Object obj);
}
