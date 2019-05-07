package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 *	Class which represents a polynomial in the rooted format,
 * 	e.g. f(x) = c(x-a0)(x-a1)...(x-an) where the c is the constant
 * 	and a0,a1,...,an the roots of the polynomial.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ComplexRootedPolynomial {

	/**Stores the roots of the polynomial.*/
	private List<Complex> roots;

	/**Stores the constant of the polynomial.*/
	private Complex constant;
	
	/**
	 * 	Constructs a new {@link ComplexRootedPolynomial} which is
	 * 	the polynomial f(z)=0.
	 */
	public ComplexRootedPolynomial() {
		roots = new ArrayList<>();
		constant = Complex.ZERO;
	}
	
	/**
	 * 	Constructs a new {@link ComplexRootedPolynomial} with the given
	 * 	constant and roots.
	 * 	@param constant The constant of the {@link ComplexRootedPolynomial}.
	 * 	@param roots The roots of the {@link ComplexRootedPolynomial}.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = new ArrayList<>();
		for(var c : roots) {
			this.roots.add(c);
		}
	}
	
	/**
	 * 	Computes the polynomial value at the given point z.
	 * 	@param z The point at which the value should be computed.
	 * 	@return The polynomial value at the given point.
	 */
	public Complex apply(Complex z) {
		Complex ret = constant;
		for(var c : roots) {
			ret = ret.multiply(c.sub(z));
		}
		return ret;
	}
	
	/**
	 * 	Returns a {@link ComplexPolynomial} equal to this {@link ComplexRootedPolynomial}.
	 * 	@return A {@link ComplexPolynomial} representation of this {@link ComplexRootedPolynomial}.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial ret = new ComplexPolynomial(constant);
		for(var c : roots) {
			ret = ret.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));
		}
		
		return ret;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(constant.toString());
		for(var c : roots) {
			sb.append("*(z-").append(c.toString()).append(")");
		}
		return sb.toString();
	}	
	
	/**
	 * 	Finds the index of closest root for given {@link ComplexPolynomial} number 
	 * 	z that is within treshold.
	 * 	@param z {@link Complex} number from which the closest root should be found.
	 * 	@param treshold Max distance of root from given {@link Complex} number z.
	 * 	@return Index of closest root or -1 if there is no such root.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		for(int i = 0; i < roots.size(); ++i) {
			if(roots.get(i).sub(z).module() < treshold) {
				return i;
			}
		}
		return -1;
	}
	
}
