package hr.fer.zemris.java.hw07.demo4;

/**
 *	Class StudentRecord which represents the data for one student in one 
 *	class. The data that is saved is jmbag, surname, name, grade and points the
 *	student scored in three different categories through the term.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class StudentRecord {
	
	/**Jmbag of the student.*/
	private String jmbag;
	
	/**Surname of the student.*/
	private String surname;
	
	/**Name of the student.*/
	private String name;
	
	/**Points the student scored on the midterm exam.*/
	private float midtermPoints;
	
	/**Points the student scored on the final exam.*/
	private float finaltermPoints;
	
	/**Points the student scored in lab exercises.*/
	private float excercisePoints;
	
	/**Grade the student has earned.*/
	private int	grade;
	
	
	/**
	 * 	Constructs a new {@link StudentRecord}.
	 * 	@param line {@link String} containing valid data from which
	 * 			   a {@link StudentRecord} can be created.
	 * 	@throws IllegalArgumentException If invalid data is given.
	 * 
	 */
	public StudentRecord(String line) {
		String[] splitted = line.strip().split("\t");
		if(splitted.length != 7) {
			throw new IllegalArgumentException("Lines doesn't contain all the needed info.");
		}
		jmbag = splitted[0];
		surname = splitted[1];
		name = splitted[2];
		try {
			midtermPoints = Float.parseFloat(splitted[3]);
			finaltermPoints = Float.parseFloat(splitted[4]);
			excercisePoints = Float.parseFloat(splitted[5]);
			grade = Integer.parseInt(splitted[6]);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Arguments cannot be processed as numbers.");
		} 
	}
	
	/**
	 * Returns the jmbag of the StudentRecord.
	 * @return the jmbag of the StudentRecord.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns the surname of the StudentRecord.
	 * @return the surname of the StudentRecord.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Returns the name of the StudentRecord.
	 * @return the name of the StudentRecord.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the midtermPoints of the StudentRecord.
	 * @return the midtermPoints of the StudentRecord.
	 */
	public float getMidtermPoints() {
		return midtermPoints;
	}

	/**
	 * Returns the finaltermPoints of the StudentRecord.
	 * @return the finaltermPoints of the StudentRecord.
	 */
	public float getFinaltermPoints() {
		return finaltermPoints;
	}

	/**
	 * Returns the excercisePoints of the StudentRecord.
	 * @return the excercisePoints of the StudentRecord.
	 */
	public float getExcercisePoints() {
		return excercisePoints;
	}

	/**
	 * Returns the grade of the StudentRecord.
	 * @return the grade of the StudentRecord.
	 */
	public int getGrade() {
		return grade;
	}	
	
	/**
	 *	Method which returns the sum of the points of a student.
	 *	@param sr The {@link StudentRecord} of the student.
	 *	@return Sum of the points.
	 */
	public float getPointsSum() {
		return midtermPoints + finaltermPoints + excercisePoints;
	}
	
	/**
	 *	Returns a {@link String} representation of the {@link StudentRecord}
	 *	which has all data separated by "\\t".
	 *	@return {@link String} representation of {@link StudentRecord}.
	 */
	@Override
	public String toString() {
		return jmbag + "\t" + surname + "\t" + name + "\t" + midtermPoints 
				+ "\t" + finaltermPoints + "\t" + excercisePoints + "\t" + grade;
	}
	
}
