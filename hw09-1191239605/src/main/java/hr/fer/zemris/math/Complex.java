package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *	Class which models a complex number.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Complex {


	/**{@link Complex} number 0*/
	public static final Complex ZERO = new Complex(0,0);
	
	/**{@link Complex} number 1*/
	public static final Complex ONE = new Complex(1,0);
	
	/**{@link Complex} number -1*/
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**{@link Complex} number i*/
	public static final Complex IM = new Complex(0,1);

	/**{@link Complex} number -i*/
	public static final Complex IM_NEG = new Complex(0,-1);

	/**TODO javadoc*/
	private static final double DIFFERENCE_TO_IGNORE = 1e-10;
	
	/**Real value*/
	private double re;
	
	/**Imaginary value*/
	private double im;
	
	/**
	 * 	Construct a new {@link Complex} with the given real and
	 * 	imaginary value.
	 * 	@param re Real value of the {@link Complex} number.
	 * 	@param im Imaginary value of the {@link Complex} number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * 	
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * 	Multiplies this {@link Complex} number with the given
	 * 	{@link Complex} number.
	 * 	@param c The {@link Complex} to multiply with.
	 * 	@return A new {@link Complex} number which is the product of the multiplication.
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + c.re * im);
	}
	
	/**
	 * 	Divides this {@link Complex} number with the given
	 * 	{@link Complex} number.\
	 * 	@param c The {@link Complex} number to divide with.
	 * 	@return A new {@link Complex} number which is the quotient of the division.
	 */
	public Complex divide(Complex c) {
		double div = c.re * c.re + c.im * c.im;
		return new Complex((re * c.re + im * c.im)/div, (c.re * im - re * c.im)/div);
	}
	
	/**
	 * 	Adds the given {@link Complex} number to this {@link Complex} number.
	 * 	@param c The {@link Complex} number to add.
	 * 	@return A new {@link Complex} number which is the sum.
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * 	Subtracts the given {@link Complex} number from this {@link Complex} number.
	 * 	@param c The {@link Complex} number to subtract.
	 * 	@return A new {@link Complex} number which is the difference.
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * 	Returns the negative value of this {@link Complex} number.
	 * 	@return A new {@link Complex} number which is this {@link Complex} number * -1.
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * 	Returns the	TODO javadoc
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		double angle; //TODO provjeri jel ovo dobro actually
		if(Math.abs(im)< DIFFERENCE_TO_IGNORE ) {
			angle = re > 0 ? 0 : Math.PI;
		} else {
			angle = Math.atan2(im, re);
			if(angle < 0) {
				angle += 2 * Math.PI;
			}
		}
		return new Complex(Math.pow(module(), n) * Math.cos(angle * n), Math.pow(module(), n) * Math.sin(angle * n));
	}

	/**
	 * 	TODO javadoc
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		List<Complex> list = new ArrayList<>();
		
		
		double r = Math.pow(module(), 1d / n);
		double angle; //TODO provjeri jel ovo dobro actually
		if(Math.abs(im)< DIFFERENCE_TO_IGNORE ) {
			angle = re > 0 ? 0 : Math.PI;
		} else {
			angle = Math.atan2(im, re);
			if(angle < 0) {
				angle += 2 * Math.PI;
			}
		}
		
		for(int k = 0; k < n; k++) {
			double newAngle = (angle + 2 * k * Math.PI)/n;
			list.add(new Complex(r * Math.cos(newAngle), r * Math.sin(newAngle)));
		}		
		
		return list;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + re + (im >= 0 ? "+" : "") + im + "i)";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(im, re);
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
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		return Math.abs(sub(other).module()) < DIFFERENCE_TO_IGNORE; 
	}
	
}
