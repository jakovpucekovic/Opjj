package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;

public class ComplexTest {
	

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