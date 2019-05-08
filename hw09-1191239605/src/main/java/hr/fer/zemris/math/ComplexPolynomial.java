package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *	Class which represents a polynomial with the given factors,
 *	e.g. f(x)=an*x^n + an-1*x^n-1 + ... + a1x + a0 where the factors
 *	are a0,a1,...,an.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class ComplexPolynomial {
		
	/**Factors of the polynomial.*/
	private List<Complex> factors;
	
	/**
	 * 	Constructs a new {@link ComplexPolynomial} with the value 0.
	 */
	public ComplexPolynomial() {
		this(Complex.ZERO);
	}
	
	/**
	 * 	Constructs a new {@link ComplexPolynomial} with the given factors.
	 * 	@param factors Factors of the {@link ComplexPolynomial}.
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = new ArrayList<>();
		for(var c : factors) {
			this.factors.add(c);
		}
	}
	
	/**
	 * 	Returns the degree of this {@link ComplexPolynomial}.
	 * 	@return The degree of this {@link ComplexPolynomial}.
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}
	
	/**
	 * 	Multiplies this {@link ComplexPolynomial} with the given
	 * 	{@link ComplexPolynomial}.
	 * 	@param p The {@link ComplexPolynomial} to multiply with.
	 * 	@return A new {@link ComplexPolynomial} which is the product.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		ComplexPolynomial ret = new ComplexPolynomial();
		ret.factors.clear();
		
		int degreeSum = order() + p.order();
		for(int l = 0; l <= degreeSum; ++l) {
			Complex s = Complex.ZERO;
			for(int i = 0; i <= l; ++i) {
				Complex a = i <= order() ? factors.get(i) : Complex.ZERO;
				Complex b = l - i <= p.order() ? p.factors.get(l - i) : Complex.ZERO;
				s = s.add(a.multiply(b));
			}
			ret.factors.add(s);
		}
		
		
//		int degreeSum = order() + p.order();
//		for(int i = 0; i <= degreeSum; ++i) {
//			Complex s = Complex.ZERO;
//			for(int j = 0; j <= i ; ++j) {
//				s = s.add(factors.get(j).multiply(p.factors.get(i - j)));
//			}
//			ret.factors.add(s);
//		}
		
		return ret;
	}
	
	/**
	 * 	Calculates the derivative of this {@link ComplexPolynomial}.
	 * 	@return A new {@link ComplexPolynomial} which is the derivative
	 * 			of this {@link ComplexPolynomial}.
	 */
	public ComplexPolynomial derive() {
		List<Complex> list = new ArrayList<>();
		for(int i = 1, size = factors.size(); i < size; ++i) {
			list.add(factors.get(i).multiply(new Complex(i,0)));
		}
		if(list.isEmpty()) {
			list.add(Complex.ZERO);
		}
		return new ComplexPolynomial(list.toArray(new Complex[list.size()]));
	}
	
	/**
	 * 	Computes the polynomial value at the given point z.
	 * 	@param z The point at which the value should be computed.
	 * 	@return The polynomial value at the given point.
	 */
	public Complex apply(Complex z) {
		/*Horner algorithm*/
		Complex ret = factors.get(factors.size()-1);
		for(int i = factors.size() - 2; i >= 0; --i) {
			ret = ret.multiply(z).add(factors.get(i));
		}
		return ret;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.size() - 1; i > 0; --i) {
			sb.append(factors.get(i));
			sb.append("*z^");
			sb.append(i);
			sb.append("+");
		}
		sb.append(factors.get(0));
		return sb.toString();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(factors);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexPolynomial))
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		return Objects.equals(factors, other.factors);
	}
}
