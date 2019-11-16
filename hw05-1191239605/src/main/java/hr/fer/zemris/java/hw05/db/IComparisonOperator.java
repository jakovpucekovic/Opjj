package hr.fer.zemris.java.hw05.db;

/**
 *  Interface which represents comparison operators
 *  for {@link String}s.
 *  
 *  @author Jakov Pucekovic
 *  @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * 	Method which decides if the given {@link String}s satisfy
	 * 	some kind of condition.
	 * 	@param value1 First {@link String}.
	 * 	@param value2 Second {@link String}.
	 * 	@return <code>true</code> if yes, <code>false</code> if not.
	 */
	boolean satisfied(String value1, String value2);
	
}
