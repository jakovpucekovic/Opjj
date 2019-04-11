package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IFieldValueGettersTest {
	
	@Test
	public void getJmbag() {
		StudentRecord sr = new StudentRecord("1191239605", "Peric", "Pero", 3);
		
		assertEquals("1191239605", FieldValueGetters.JMBAG.get(sr));
	}
	
	@Test
	public void getLastNameEmpty() {
		StudentRecord sr = new StudentRecord("1191239605", "", "Pero", 3);
		
		assertEquals("", FieldValueGetters.LAST_NAME.get(sr));
	}
	
	@Test
	public void getLastName() {
		StudentRecord sr = new StudentRecord("1191239605", "Peric", "Pero", 3);
		
		assertEquals("Peric", FieldValueGetters.LAST_NAME.get(sr));
	}
	
	@Test
	public void getFirstNameEmpty() {
		StudentRecord sr = new StudentRecord("1191239605", "Peric", "", 3);
		
		assertEquals("", FieldValueGetters.FIRST_NAME.get(sr));
	}
	
	@Test
	public void getFirstName() {
		StudentRecord sr = new StudentRecord("1191239605", "Peric", "Pero", 3);
		
		assertEquals("Pero", FieldValueGetters.FIRST_NAME.get(sr));
	}
	
	
}
