package hr.fer.zemris.java.hw05.db;

/**
 * 	Class used to get jmbag, first and last names from a {@link StudentRecord}.
 * 	
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class FieldValueGetters {

	/**{@link IFieldValueGetter} used to get firstName from a {@link StudentRecord}.*/
	public static final IFieldValueGetter FIRST_NAME = (StudentRecord::getFirstName);
	/**{@link IFieldValueGetter} used to get lastName from a {@link StudentRecord}.*/
	public static final IFieldValueGetter LAST_NAME = (StudentRecord::getLastName);
	/**{@link IFieldValueGetter} used to get jmbag from a {@link StudentRecord}.*/
	public static final IFieldValueGetter JMBAG = (StudentRecord::getJmbag);
	
}
