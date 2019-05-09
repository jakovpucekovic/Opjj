package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;

public class ComplexTest {
	
	@Test 
	public void parseNull() {
		assertThrows(NullPointerException.class, ()->Complex.parse(null));
	}
	
	@Test
	public void parseNotNumber() {
		assertThrows(IllegalArgumentException.class, ()->Complex.parse("pero"));
		assertThrows(IllegalArgumentException.class, ()->Complex.parse("1,2"));
		assertThrows(IllegalArgumentException.class, ()->Complex.parse("+-3"));
		assertThrows(IllegalArgumentException.class, ()->Complex.parse("-+i"));
		assertThrows(IllegalArgumentException.class, ()->Complex.parse("++3--2i"));
	}
	
	@Test
	public void parseOne() {
		Complex expected = new Complex(1, 0);
		
		assertEquals(expected, Complex.parse("1"));
		assertEquals(expected, Complex.parse("+1"));
		assertEquals(expected, Complex.parse("1.0"));
		assertEquals(expected, Complex.parse("+1.0"));
	}
	
	@Test
	public void parseMinusOne() {
		Complex expected = new Complex(-1, 0);
		
		assertEquals(expected, Complex.parse("-1"));
		assertEquals(expected, Complex.parse("-1.0"));
	}
	
	@Test
	public void parseI() {
		Complex expected = new Complex(0, 1);
		
		assertEquals(expected, Complex.parse("i"));
		assertEquals(expected, Complex.parse("+i"));
		assertEquals(expected, Complex.parse("i1.0"));
		assertEquals(expected, Complex.parse("+i1.0"));
	}
	
	@Test
	public void parseMinusI() {
		Complex expected = new Complex(0, -1);
		
		assertEquals(expected, Complex.parse("-i"));
		assertEquals(expected, Complex.parse("-i1.0"));
	}
	
	@Test
	public void parseOnePlusI() {
		Complex expected = new Complex(1, 1);
		
		assertEquals(expected, Complex.parse("1+i"));
		assertEquals(expected, Complex.parse("1+i1"));
		assertEquals(expected, Complex.parse("1+i1.0"));

		assertEquals(expected, Complex.parse("1.0+i"));
		assertEquals(expected, Complex.parse("1.0+i1"));
		assertEquals(expected, Complex.parse("1.0+i1.0"));
	}
	
	@Test
	public void parseOneMinusI() {
		Complex expected = new Complex(1, -1);
		assertEquals(expected, Complex.parse("1-i"));
		assertEquals(expected, Complex.parse("1-i1"));
		assertEquals(expected, Complex.parse("1-i1.0"));
		
		assertEquals(expected, Complex.parse("+1-i"));
		assertEquals(expected, Complex.parse("+1-i1"));
		assertEquals(expected, Complex.parse("+1-i1.0"));

		assertEquals(expected, Complex.parse("1.0-i"));
		assertEquals(expected, Complex.parse("1.0-i1"));
		assertEquals(expected, Complex.parse("1.0-i1.0"));
		
		assertEquals(expected, Complex.parse("+1.0-i"));
		assertEquals(expected, Complex.parse("+1.0-i1"));
		assertEquals(expected, Complex.parse("+1.0-i1.0"));
	}
	
	@Test
	public void parsePlusOnePlusI() {
		Complex expected = new Complex(1, 1);
		
		assertEquals(expected, Complex.parse("+1+i"));
		assertEquals(expected, Complex.parse("+1+i1"));
		assertEquals(expected, Complex.parse("+1+i1.0"));
		
		assertEquals(expected, Complex.parse("+1.0+i"));
		assertEquals(expected, Complex.parse("+1.0+i1"));
		assertEquals(expected, Complex.parse("+1.0+i1.0"));
	}
	
	@Test
	public void parsePlusOneMinusI() {
		Complex expected = new Complex(1, -1);;
		
		assertEquals(expected, Complex.parse("+1-i"));
		assertEquals(expected, Complex.parse("+1-i1"));
		assertEquals(expected, Complex.parse("+1-i1.0"));
		
		assertEquals(expected, Complex.parse("+1.0-i"));
		assertEquals(expected, Complex.parse("+1.0-i1"));
		assertEquals(expected, Complex.parse("+1.0-i1.0"));
	}
	
	@Test
	public void parseMinusOnePlusI() {
		Complex expected = new Complex(-1, 1);
		
		assertEquals(expected, Complex.parse("-1+i"));
		assertEquals(expected, Complex.parse("-1+i1"));
		assertEquals(expected, Complex.parse("-1+i1.0"));
		
		assertEquals(expected, Complex.parse("-1+i"));
		assertEquals(expected, Complex.parse("-1+i1"));
		assertEquals(expected, Complex.parse("-1+i1.0"));

		assertEquals(expected, Complex.parse("-1.0+i"));
		assertEquals(expected, Complex.parse("-1.0+i1"));
		assertEquals(expected, Complex.parse("-1.0+i1.0"));
	}
	
	@Test
	public void parseMinusOneMinusI() {
		Complex expected = new Complex(-1, -1);
		
		assertEquals(expected, Complex.parse("-1-i"));
		assertEquals(expected, Complex.parse("-1-i1"));
		assertEquals(expected, Complex.parse("-1-i1.0"));
		
		assertEquals(expected, Complex.parse("-1-i"));
		assertEquals(expected, Complex.parse("-1-i1"));
		assertEquals(expected, Complex.parse("-1-i1.0"));

		assertEquals(expected, Complex.parse("-1.0-i"));
		assertEquals(expected, Complex.parse("-1.0-i1"));
		assertEquals(expected, Complex.parse("-1.0-i1.0"));
	}
	
	
	@Test
	public void addTest() {
		Complex expected = new Complex(Math.E, -3.14);
		assertEquals(expected, new Complex(Math.E,0).add(new Complex(0, -3.14)));
	}
	
	@Test
	public void subTest() {
		Complex expected = new Complex(0, 0.1);
		assertEquals(expected, new Complex(378,0).sub(new Complex(378,-0.1)));
	}
	
	@Test
	public void mulTest() {
		Complex expected = new Complex(-5, 5 );
		assertEquals(expected, new Complex(1,2).multiply(new Complex(1,3)));
	}
	
	@Test
	public void divTest() {
		Complex expected = new Complex(0.7, -0.1 );
		assertEquals(expected, new Complex(1,2).divide(new Complex(1,3)));
	}
	
	@Test
	public void div2Test() {
		Complex expected = new Complex(43./65, -6./65 );
		assertEquals(expected, new Complex(5,2).divide(new Complex(7,4)));
	}

	@Test
	public void powerTest() {
		assertEquals(Complex.ONE, new Complex(1,2).power(0));
		assertEquals(new Complex(-8839,33802), new Complex(1,2).power(13));
	}
	
	@Test
	public void rootTest() {
		List<Complex> ex = new ArrayList<>();
		ex.add(new Complex(0.5, 0.86602540378));
		ex.add(Complex.ONE_NEG);
		ex.add(new Complex(0.5, -0.86602540378));
		
		List<Complex> calc = Complex.ONE_NEG.root(3);
		assertEquals(ex.get(0), calc.get(0));
		assertEquals(ex.get(1), calc.get(1));
		assertEquals(ex.get(2), calc.get(2));
	} 
}