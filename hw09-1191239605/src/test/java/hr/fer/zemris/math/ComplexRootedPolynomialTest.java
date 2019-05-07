package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void test() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE_NEG, Complex.ONE);
		ComplexPolynomial exp = new ComplexPolynomial(Complex.ONE_NEG, Complex.ZERO, Complex.ONE);
		assertEquals(exp, crp.toComplexPolynom());
	}
	
}
