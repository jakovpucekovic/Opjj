package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test 
	public void parseNull() {
		assertThrows(NullPointerException.class, ()->ComplexNumber.parse(null));
	}
	
	@Test
	public void parseNotNumber() {
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("pero"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("1,2"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("+-3"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("-+i"));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("++3--2i"));
	}
	
	@Test
	public void parseOne() {
		ComplexNumber expected = new ComplexNumber(1, 0);
		
		assertEquals(expected, ComplexNumber.parse("1"));
		assertEquals(expected, ComplexNumber.parse("+1"));
		assertEquals(expected, ComplexNumber.parse("1.0"));
		assertEquals(expected, ComplexNumber.parse("+1.0"));
	}
	
	@Test
	public void parseMinusOne() {
		ComplexNumber expected = new ComplexNumber(-1, 0);
		
		assertEquals(expected, ComplexNumber.parse("-1"));
		assertEquals(expected, ComplexNumber.parse("-1.0"));
	}
	
	@Test
	public void parseI() {
		ComplexNumber expected = new ComplexNumber(0, 1);
		
		assertEquals(expected, ComplexNumber.parse("i"));
		assertEquals(expected, ComplexNumber.parse("+i"));
		assertEquals(expected, ComplexNumber.parse("1.0i"));
		assertEquals(expected, ComplexNumber.parse("+1.0i"));
	}
	
	@Test
	public void parseMinusI() {
		ComplexNumber expected = new ComplexNumber(0, -1);
		
		assertEquals(expected, ComplexNumber.parse("-i"));
		assertEquals(expected, ComplexNumber.parse("-1.0i"));
	}
	
	@Test
	public void parseOnePlusI() {
		ComplexNumber expected = new ComplexNumber(1, 1);
		
		assertEquals(expected, ComplexNumber.parse("1+i"));
		assertEquals(expected, ComplexNumber.parse("1+1i"));
		assertEquals(expected, ComplexNumber.parse("1+1.0i"));

		assertEquals(expected, ComplexNumber.parse("1.0+i"));
		assertEquals(expected, ComplexNumber.parse("1.0+1i"));
		assertEquals(expected, ComplexNumber.parse("1.0+1.0i"));
	}
	
	@Test
	public void parseOneMinusI() {
		ComplexNumber expected = new ComplexNumber(1, -1);
		assertEquals(expected, ComplexNumber.parse("1-i"));
		assertEquals(expected, ComplexNumber.parse("1-1i"));
		assertEquals(expected, ComplexNumber.parse("1-1.0i"));
		
		assertEquals(expected, ComplexNumber.parse("+1-i"));
		assertEquals(expected, ComplexNumber.parse("+1-1i"));
		assertEquals(expected, ComplexNumber.parse("+1-1.0i"));

		assertEquals(expected, ComplexNumber.parse("1.0-i"));
		assertEquals(expected, ComplexNumber.parse("1.0-1i"));
		assertEquals(expected, ComplexNumber.parse("1.0-1.0i"));
		
		assertEquals(expected, ComplexNumber.parse("+1.0-i"));
		assertEquals(expected, ComplexNumber.parse("+1.0-1i"));
		assertEquals(expected, ComplexNumber.parse("+1.0-1.0i"));
	}
	
	@Test
	public void parsePlusOnePlusI() {
		ComplexNumber expected = new ComplexNumber(1, 1);
		
		assertEquals(expected, ComplexNumber.parse("+1+i"));
		assertEquals(expected, ComplexNumber.parse("+1+1i"));
		assertEquals(expected, ComplexNumber.parse("+1+1.0i"));
		
		assertEquals(expected, ComplexNumber.parse("+1.0+i"));
		assertEquals(expected, ComplexNumber.parse("+1.0+1i"));
		assertEquals(expected, ComplexNumber.parse("+1.0+1.0i"));
	}
	
	@Test
	public void parsePlusOneMinusI() {
		ComplexNumber expected = new ComplexNumber(1, -1);;
		
		assertEquals(expected, ComplexNumber.parse("+1-i"));
		assertEquals(expected, ComplexNumber.parse("+1-1i"));
		assertEquals(expected, ComplexNumber.parse("+1-1.0i"));
		
		assertEquals(expected, ComplexNumber.parse("+1.0-i"));
		assertEquals(expected, ComplexNumber.parse("+1.0-1i"));
		assertEquals(expected, ComplexNumber.parse("+1.0-1.0i"));
	}
	
	@Test
	public void parseMinusOnePlusI() {
		ComplexNumber expected = new ComplexNumber(-1, 1);
		
		assertEquals(expected, ComplexNumber.parse("-1+i"));
		assertEquals(expected, ComplexNumber.parse("-1+1i"));
		assertEquals(expected, ComplexNumber.parse("-1+1.0i"));
		
		assertEquals(expected, ComplexNumber.parse("-1+i"));
		assertEquals(expected, ComplexNumber.parse("-1+1i"));
		assertEquals(expected, ComplexNumber.parse("-1+1.0i"));

		assertEquals(expected, ComplexNumber.parse("-1.0+i"));
		assertEquals(expected, ComplexNumber.parse("-1.0+1i"));
		assertEquals(expected, ComplexNumber.parse("-1.0+1.0i"));
	}
	
	@Test
	public void parseMinusOneMinusI() {
		ComplexNumber expected = new ComplexNumber(-1, -1);
		
		assertEquals(expected, ComplexNumber.parse("-1-i"));
		assertEquals(expected, ComplexNumber.parse("-1-1i"));
		assertEquals(expected, ComplexNumber.parse("-1-1.0i"));
		
		assertEquals(expected, ComplexNumber.parse("-1-i"));
		assertEquals(expected, ComplexNumber.parse("-1-1i"));
		assertEquals(expected, ComplexNumber.parse("-1-1.0i"));

		assertEquals(expected, ComplexNumber.parse("-1.0-i"));
		assertEquals(expected, ComplexNumber.parse("-1.0-1i"));
		assertEquals(expected, ComplexNumber.parse("-1.0-1.0i"));
	}
	
}
