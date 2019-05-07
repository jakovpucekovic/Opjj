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
}
