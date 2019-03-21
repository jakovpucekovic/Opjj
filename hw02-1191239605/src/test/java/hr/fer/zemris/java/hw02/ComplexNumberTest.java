package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void constructorTest() {
		ComplexNumber number = new ComplexNumber(3.15, -7);
		assertEquals(3.15, number.getReal());
		assertEquals(-7, number.getImaginary());
	}
	
	@Test
	public void fromRealTest() {
		assertEquals(new ComplexNumber(-2.7, 0), ComplexNumber.fromReal(-2.7));
	}
	
	@Test
	public void fromImaginaryTest() {
		assertEquals(new ComplexNumber(0, 0.1593), ComplexNumber.fromImaginary(0.1593));
	}
	
	@Test
	public void fromMagnitudeAndAngleTestIllegalMagnitude() {
		assertThrows(IllegalArgumentException.class, ()-> ComplexNumber.fromMagnitudeAndAngle(-2, 0));
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		assertEquals(new ComplexNumber(0, 0), ComplexNumber.fromMagnitudeAndAngle(0, Math.PI));
		assertEquals(new ComplexNumber(1, 1), ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI/4));
	}
	
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
	
	@Test
	public void getRealTest() {
		assertEquals(0, ComplexNumber.fromReal(0).getReal());
		assertEquals(-23.41, ComplexNumber.fromReal(-23.41).getReal());
	}
	
	@Test
	public void getImaginaryTest() {
		assertEquals(0, ComplexNumber.fromImaginary(0).getImaginary());
		assertEquals(-23.41, ComplexNumber.fromImaginary(-23.41).getImaginary());
	}
	
	@Test
	public void getMagnitudeTest() {
		assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getMagnitude());
		assertEquals(23.41, ComplexNumber.fromMagnitudeAndAngle(23.41, 0).getMagnitude());
		assertEquals(5, new ComplexNumber(4, 3).getMagnitude());
	}
	
	@Test
	public void getAngleTest() {	
		assertEquals(Math.PI / 4, ComplexNumber.parse("1+i").getAngle());
		assertEquals(7 * Math.PI / 4, ComplexNumber.parse("1-i").getAngle());
		assertEquals(3 * Math.PI / 4, ComplexNumber.parse("-1+i").getAngle());
		assertEquals(5 * Math.PI / 4, ComplexNumber.parse("-1-i").getAngle());
	}
	
	@Test
	public void getAngleTestRealIsZero() {	
		assertEquals(Math.PI / 2, ComplexNumber.parse("i").getAngle());
		assertEquals(3 * Math.PI / 2, ComplexNumber.parse("-i").getAngle());
	}
	
	@Test
	public void getAngleTestImaginaryIsZero() {	
		assertEquals(0, ComplexNumber.parse("7").getAngle());
		assertEquals(Math.PI, ComplexNumber.parse("-1").getAngle());		
		assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(7, 4 * Math.PI).getAngle());
	}

	@Test
	public void addTest() {
		ComplexNumber expected = new ComplexNumber(Math.E, -3.14);
		assertEquals(expected, ComplexNumber.fromReal(Math.E).add(ComplexNumber.fromImaginary(-3.14)));
	}
	
	@Test
	public void subTest() {
		ComplexNumber expected = new ComplexNumber(0, 0.1);
		assertEquals(expected, ComplexNumber.fromReal(378).sub(ComplexNumber.parse("378-0.1i")));
	}
	
	@Test
	public void mulTest() {
		ComplexNumber expected = new ComplexNumber(-5, 5 );
		assertEquals(expected, ComplexNumber.parse("1+2i").mul(ComplexNumber.parse("1+3i")));
	}
	
	@Test
	public void divTest() {
		ComplexNumber expected = new ComplexNumber(0.7, -0.1 );
		assertEquals(expected, ComplexNumber.parse("1+2i").div(ComplexNumber.parse("1+3i")));
	}

	@Test
	public void powerTest() {
		assertEquals(ComplexNumber.fromReal(1), ComplexNumber.parse("1+2i").power(0));
		assertEquals(ComplexNumber.parse("-8839+33802i"), ComplexNumber.parse("1+2i").power(13));
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("1+2i").power(-1));
	}
	
	@Test
	public void rootTestIllegalArgument() {
		assertThrows(IllegalArgumentException.class, ()->ComplexNumber.parse("1+2i").root(-2));
	}
	
	@Test
	public void rootTest() {
		ComplexNumber[] expected = new ComplexNumber[3];
		expected[0] = ComplexNumber.parse("0.5+0.86602540378i");
		expected[1] = ComplexNumber.parse("-1");
		expected[2] = ComplexNumber.parse("0.5-0.86602540378i");
		
		ComplexNumber[] calculated = ComplexNumber.parse("-1").root(3);
		assertEquals(expected[0], calculated[0]);
		assertEquals(expected[1], calculated[1]);
		assertEquals(expected[2], calculated[2]);
	} 
	
	@Test
	public void toStringTest() {
		assertEquals("1.0+0.0i", ComplexNumber.parse("1").toString());
		assertEquals("0.0-1.0i", ComplexNumber.parse("-i").toString());
		assertEquals("0.1-3.0i", ComplexNumber.parse("0.1-3i").toString());
	}
	
	@Test
	public void equalsTest() {
		ComplexNumber n1 = ComplexNumber.parse("1+i");
		ComplexNumber n2 = ComplexNumber.parse("1-i");
		ComplexNumber n3 = ComplexNumber.parse("+1.0+1.000000000000001i");
		assertTrue(n1.equals(n3));
		assertFalse(n1.equals(n2));
	}
}
