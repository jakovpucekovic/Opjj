package hr.fer.zemris.java.hw05.db;

/**
 * 	Interface used to filter {@link StudentRecord}s.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface IFilter {

	/**
	 * 	Method which decides if a {@link StudentRecord} satisfies
	 * 	some kind of condition or not.
	 * 	@param record The {@link StudentRecord} to evaluate.	
	 * 	@return <code>true</code> if yes, <code>false</code> if not.
	 */
	public boolean accepts(StudentRecord record);
	
}
