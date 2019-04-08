package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;

	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if(jmbag.length() != 10 || finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Data isn't valid.");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

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
	
	
	
	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

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

	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}
	
	
	
}
