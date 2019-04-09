package hr.fer.zemris.java.hw05.db;

/**
 *  Interface which gets certain values from the {@link StudentRecord}.
 *  
 *  @author Jakov Pucekovic
 *  @version 1.0
 */
public interface IFieldValueGetter {

	/**
	 * 	Method which returns some kind of value from the given {@link StudentRecord}.
	 * 	@param record The {@link StudentRecord} from which to get the value.
	 * 	@return Some value from the given {@link StudentRecord}.
	 */
	String get(StudentRecord record);
	
}
