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

	@Test
	public void testClosestRoot1() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		int index = crp.indexOfClosestRootFor(new Complex(1,0), 1e-3);;
		assertEquals(0, index);
		
	}
	
	@Test
	public void testClosestRooti() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		int index = crp.indexOfClosestRootFor(new Complex(0,-1), 1e-3);;
		assertEquals(3, index);
		
	}
	
	@Test
	public void testClosestRootClose() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		int index = crp.indexOfClosestRootFor(new Complex(-0.9991,0), 1e-3);;
		assertEquals(1, index);
		
	}
	
	@Test
	public void testClosestRoot() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		int index = crp.indexOfClosestRootFor(new Complex(-0.9995,0), 1e-3);;
		assertEquals(1, index);
		
	}
	
}
