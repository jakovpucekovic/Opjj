package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * 	Class which represents a student record in 
 * 	our {@link StudentDatabase}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class StudentRecord {

	/**The jmbag of the student, 10-digit number stored as {@link String}.*/
	private String jmbag;
	/**The last name of the student.*/
	private String lastName;
	/**The first name of the student.*/
	private String firstName;
	/**The final grade of the student, 1, 2, 3, 4 or 5.*/
	private int finalGrade;

	/**
	 * 	Constructs a new {@link StudentRecord} with the given parameters.	
	 * 	@param jmbag The jmbag of the student.
	 * 	@param lastName The last name of the student.
	 * 	@param firstName The first name of the student.
	 * 	@param finalGrade The grade of the student.
	 * 	@throws IllegalArgumentException If the jmbag or finalGrade aren't valid.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if(jmbag.length() != 10 || "12345".indexOf(finalGrade + '0') == -1) {
			throw new IllegalArgumentException("Data isn't valid.");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 *  Constructs a new {@link StudentRecord} from the given {@link String}.
	 *  @param record The student record in {@link String} format.
	 *  @throws IllegalArgumentException If the given {@link String} isn't a valid
	 *  								 {@link StudentRecord}.
	 */
	public StudentRecord(String record) {
		if(record.equals("")) return;
		String[] entries = record.split("\\t");
		if(entries.length != 4) {
			throw new IllegalArgumentException("Data isn't valid.");
		}
		if(entries[0].length() != 10 || entries[3].length() != 1 || !"12345".contains(entries[3])){
			throw new IllegalArgumentException("Data isn't valid.");			
		}
		this.jmbag = entries[0];
		this.lastName = entries[1];
		this.firstName = entries[2];
		this.finalGrade = Integer.parseInt(entries[3]);
	}
	
	/**
	 * 	Returns the jmbag of the {@link StudentRecord}.
	 * 	@return The jmbag of the {@link StudentRecord}
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * 	Returns the lastName of the {@link StudentRecord}.
	 * 	@return The lastName of the {@link StudentRecord}.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 	Returns the firstName of the {@link StudentRecord}.
	 * 	@return The firstName of the {@link StudentRecord}.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * 	Returns the finalGrade of the {@link StudentRecord}.
	 * 	@return The finalGrade of the {@link StudentRecord}.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	/**
	 * 	Used to compare 2 {@link StudentRecord}. They are 
	 * 	deemed equal if they have the same jmbag.
	 * 	@param obj The object to compare with.
	 * 	@return <code>true</code> if they have the same jmbag, <code>false</code>
	 * 			in any other case.	
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

	/**
	 * 	Converts the {@link StudentRecord} into {@link String} with the 
	 * 	format "jmbag lastName firstName finalGrade".
	 * 	@return {@link String} representation of the {@link StudentRecord}.
	 */
	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}
	
	
	
}
