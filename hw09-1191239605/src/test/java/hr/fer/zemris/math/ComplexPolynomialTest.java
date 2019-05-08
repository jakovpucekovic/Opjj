package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {

	@Test
	public void testMulSimple() {
		ComplexPolynomial cp1 = new ComplexPolynomial(Complex.ONE);
		ComplexPolynomial cp2 = new ComplexPolynomial(Complex.ONE_NEG, Complex.ONE);
		ComplexPolynomial exp = new ComplexPolynomial(Complex.ONE_NEG, Complex.ONE);
		
		assertEquals(exp, cp1.multiply(cp2));
	
	}
	
	@Test
	public void testMul() {
		ComplexPolynomial cp1 = new ComplexPolynomial(Complex.ONE, Complex.ONE);
		ComplexPolynomial cp2 = new ComplexPolynomial(Complex.ONE_NEG, Complex.ONE);
		ComplexPolynomial exp = new ComplexPolynomial(Complex.ONE_NEG, Complex.ZERO, Complex.ONE);
		
		assertEquals(exp, cp1.multiply(cp2));
	
	}

	@Test
	public void testOrder() {
		ComplexPolynomial cp1 = new ComplexPolynomial(Complex.ONE, Complex.ONE);
		ComplexPolynomial cp2 = new ComplexPolynomial(Complex.ONE_NEG, Complex.ZERO, Complex.ONE);
		
		assertEquals(1, cp1.order());
		assertEquals(2, cp2.order());
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE.add(Complex.IM));
		ComplexPolynomial exp = new ComplexPolynomial(Complex.ONE, new Complex(2,2));
		assertEquals(exp, cp.derive());
	}
	
	@Test
	public void testDerive2() {
		ComplexPolynomial cp = new ComplexPolynomial(new Complex(2,3), new Complex(2,3), new Complex(2,3));
		ComplexPolynomial exp = new ComplexPolynomial(new Complex(2,3), new Complex(4, 6));
		assertEquals(exp, cp.derive());
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE);
		assertEquals(new Complex(3,0), cp.apply(Complex.ONE));
	}	
	
}
