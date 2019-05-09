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

	/**Consider 2 {@link Complex} numbers equal if their distance is smaller than this.*/
	private static final double DIFFERENCE_TO_IGNORE = 1e-10;
	
	/**Real value*/
	private double re;
	
	/**Imaginary value*/
	private double im;
	
	/**
	 * 	Constructs a new {@link Complex} with the value 0+0i;
	 */
	public Complex() {
		this(0,0);
	}
	
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
	 * 	Returns the module of the {@link Complex} number.
	 * 	@return Module of the {@link Complex} number.
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
	 * 	{@link Complex} number.
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
	 * 	Raises the {@link Complex} number to the given power.
	 * 	@param n Power to raise the {@link Complex} number to.
	 * 	@return New {@link Complex} number which is the result.
	 * 	@throws IllegalArgumentException If the given power is < 0.
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		double angle;
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
	 * 	Calculates the n-th roots of this {@link Complex} number.
	 * 	@param n Which root to calculate.
	 * 	@return A {@link List} of {@link Complex} roots.
	 * 	@throws IllegalArgumentException If the given argument is <= 0.
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		List<Complex> list = new ArrayList<>();
		
		
		double r = Math.pow(module(), 1d / n);
		double angle;
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
	
	/**
	 * 	Parses the given string and if it can be interpreted as a complex
	 * 	number, creates a new {@link Complex} and assigns it the values
	 * 	it has parsed.
	 * 	The format of the given string should consist of 2 double values and
	 * 	a + or - sign between them, where the first one represents the real 
	 * 	part and the second the imaginary part,	naturally the one representing
	 * 	the imaginary part should be followed by "i". If one of the parts is 0,
	 * 	that part can be skipped(if the imaginary part is 0, "i" doesn't need
	 * 	to be written.
	 * 	@param s The string which should be parsed.
	 * 	@return A new instance of the class {@link Complex}.
	 * 	@throws IllegalArgumentException If the string cannot be parsed to
	 * 			a valid complex number format.
	 * 	@throws NullPointerException If the given input is null.
	 */
	public static Complex parse(String s) {
		Objects.requireNonNull(s);
		s = s.replace(" ", "");
		if(s.matches("[+-]?\\d+(\\.\\d+)?")) {									//only real part
			return new Complex(Double.parseDouble(s), 0);
		} else if(s.matches("\\+?i")) {											//+i and i
			return new Complex(0, 1);
		} else if(s.matches("-i")) {											//-i
			return new Complex(0, -1);
		} else if(s.matches("[+-]?i\\d+(\\.\\d+)?")) {							//only imaginary part
			return new Complex(0, Double.parseDouble(s.replace("i", "")));
		} else if(s.matches("[+-]?\\d+(\\.\\d+)?(\\+|-)i\\d*(\\.\\d+)?")) {		//both real and imaginary parts
			
			double r = Double.parseDouble(s.replaceFirst("[+-]i\\d*(\\.\\d+)?", "")); 	//removes imaginary part
			String iPart = s.replaceFirst("[+-]?\\d+(\\.\\d+)?", "");					//removes real part
			double i;
			//can't parse i, +i, -i with parseDouble()
			if(iPart.matches("\\+?i")) { 
				i = 1;
			} else if(iPart.equals("-i")) {
				i = -1;
			} else {
				i = Double.parseDouble(iPart.replace("i", ""));
			}
			return new Complex(r, i);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
