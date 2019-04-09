package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors; 

/**
 *	Class which represents a database of students.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class StudentDatabase {

	/**List used for storage of student records.*/
	private List<StudentRecord> list;
	/**Map used to generate an index for fast retrieval of records.*/
	private Map<String, StudentRecord> index;
	
	/**
	 * 	Constructs a new {@link StudentDatabase} from the given
	 * 	{@link List} of {@link String} where each element of the
	 * 	{@link List} represents a {@link StudentRecord}.
	 * 	@param inputList {@link List} of {@link StudentRecord} in {@link String} format.
	 * 	@throws IllegalArgumentException If there are duplicate jmbag's or grade is not 
	 * 									 between 1 and 5.
	 */
	public StudentDatabase(List<String> inputList) {
		list = new ArrayList<>();
		index = new HashMap<>();
		
		for(var x : inputList) {
			if( x.equals("") ) {
				continue;
			}
			StudentRecord rec = new StudentRecord(x);
			if(index.containsKey(rec.getJmbag())) {
				throw new IllegalArgumentException("Jmbag is already contained.");
			}
			list.add(rec);
			index.put(rec.getJmbag(), rec);
		}	
	}
	
	/**
	 *  Returns the {@link StudentRecord} with the given jmbag.
	 *  Complexity is O(1).
	 * 	@param jmbag The jmbag of the student.
	 * 	@return {@link StudentRecord} with the given jmbag, <code>null</code>
	 * 			if the {@link StudentRecord} doesn't exist.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * 	Filters the {@link StudentRecord}s currently in the {@link StudentDatabase}
	 * 	by calling accept() method from the given {@link IFilter}.
	 * 	@param filter {@link IFilter} used to filter the {@link StudentDatabase}.
	 * 	@return {@link List} of {@link StudentRecord}s which satisfy the filter.
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> returnList = new ArrayList<>();
		for(var i : list) {
			if(filter.accepts(i)) {
				returnList.add(i);
			}
		}
		return returnList;
	}

}
