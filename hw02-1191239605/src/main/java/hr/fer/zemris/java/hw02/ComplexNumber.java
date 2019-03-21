package hr.fer.zemris.java.hw02;

import java.lang.Math;
import java.util.Objects;

/**
 * 	Class ComplexNumber represents a complex number. Each method
 * 	returns a new instance which represents the modified complex number.
 * 
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class ComplexNumber {
	
	/**
	 * 	The constant used to compare two numbers. If the 
	 * 	difference between two numbers is smaller that this, 
	 * 	the numbers are considered equal.
	 */
	private static final double DIFFERENCE_TO_IGNORE = 0.0000000001;

	private double real;
	private double imaginary;
	
	/**
	 * 	Creates a new {@link ComplexNumber} with given real and 
	 * 	imaginary values.
	 * 	@param real Value of the real part of the complex number.
	 * 	@param imaginary Value of the imaginary part of the complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 *	Creates a new {@link ComplexNumber} with the given real value. The
	 *	imaginary part of the {@link ComplexNumber} is set to 0.
	 *	@param real Real part of the complex number.
	 *	@return A new instance of the class {@link ComplexNumber} with the
	 *			given real part and 0 as imaginary part.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * 	Creates a new {@link ComplexNumber} with the given imaginary value. 
	 * 	The real value of the {@link ComplexNumber} is set to 0.
	 *	@param imaginary Imaginary part of the complex number.
	 *	@return A new instance of the class {@link ComplexNumber} with the
	 *			given imaginary part and 0 as real part.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * 	Creates a new {@link ComplexNumber} from the given magnitude and 
	 * 	angle. The magnitude and angle are taken from the polar form of the
	 * 	complex number.
	 * 	@param magnitude A non-negative {@link Double} value.
	 * 	@param angle An angle in radians.
	 * 	@return A new instance of the class {@link ComplexNumber}.
	 * 	@throws IllegalArgumentException If the magnitude is < 0.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0) {
			throw new IllegalArgumentException();
		}
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * 	Parses the given string and if it can be interpreted as a complex
	 * 	number, creates a new {@link ComplexNumber} and assigns it the values
	 * 	it has parsed.
	 * 	The format of the given string should consist of 2 double values and
	 * 	a + or - sign between them, where the first one represents the real 
	 * 	part and the second the imaginary part,	naturally the one representing
	 * 	the imaginary part should be followed by "i". If one of the parts is 0,
	 * 	that part can be skipped(if the imaginary part is 0, "i" doesn't need
	 * 	to be written.
	 * 	@param s The string which should be parsed.
	 * 	@return A new instance of the class {@link ComplexNumber}.
	 * 	@throws IllegalArgumentException If the string cannot be parsed to
	 * 			a valid complex number format.
	 * 	@throws NullPointerException If the given input is null.
	 */
	public static ComplexNumber parse(String s) {
		Objects.requireNonNull(s);
		s = s.replace(" ", "");
		if(s.matches("[+-]?\\d+(\\.\\d+)?")) {									//only real part
			return new ComplexNumber(Double.parseDouble(s), 0);
		} else if(s.matches("\\+?i")) {											//+i and i
			return new ComplexNumber(0, 1);
		} else if(s.matches("-i")) {											//-i
			return new ComplexNumber(0, -1);
		} else if(s.matches("[+-]?\\d+(\\.\\d+)?i")) {							//only imaginary part
			return new ComplexNumber(0, Double.parseDouble(s.replace("i", "")));
		} else if(s.matches("[+-]?\\d+(\\.\\d+)?(\\+|-)\\d*(\\.\\d+)?i")) {		//both real and imaginary parts
			
			double r = Double.parseDouble(s.replaceFirst("[+-]\\d*(\\.\\d+)?i", "")); 	//removes imaginary part
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
			return new ComplexNumber(r, i);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 	Returns the value of the real part of the {@link ComplexNumber}.
	 * 	@return The value of the real part of the {@link ComplexNumber}.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * 	Returns the value of the imaginary part of the {@link ComplexNumber}.
	 * 	@return The value of the imaginary part of the {@link ComplexNumber}.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 *  Calculates the magnitude of the polar form of {@link ComplexNumber}.
	 *  @return The magnitude of the {@link ComplexNumber}.
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	/**
	 * 	Calculates the angle of the polar form of the {@link ComplexNumber}
	 * 	in radians in the inverval [0,2pi> (zero included, 2pi excluded).
	 * 	@return The angle of the {@link ComplexNumber} in radians.
	 */
	public double getAngle() {
		if(Math.abs(imaginary)< DIFFERENCE_TO_IGNORE ) {
			if(real > 0) {
				return 0;
			}
			return Math.PI;
		}
		double angle = Math.atan2(imaginary, real);
		if(angle < 0) {
			angle += 2 * Math.PI;
		}
		return angle;	
	}
	
	/**
	 * 	Adds 2 {@link ComplexNumber}s together, adding the real part to
	 * 	the real part and the imaginary part to the imaginary part.
	 * 	@return A new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}
	
	/**
	 * 	Subtracts 2 {@link ComplexNumber}s, subtracting the real part from
	 * 	the real part and the imaginary part from the imaginary part.
	 * 	@return A new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}
	
	/**
	 * 	Multiplies 2 {@link ComplexNumber}s.
	 * 	@return A new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real * c.real - imaginary * c.imaginary, real * c.imaginary + c.real * imaginary);
	}
	
	/**
	 * Divides 2 {@link ComplexNumber}s.
	 * @return A new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double div = c.real * c.real + c.imaginary * c.imaginary;
		return new ComplexNumber((real * c.real + imaginary * c.imaginary)/div, (c.real * imaginary - real * c.imaginary)/div);
	}
	
	/**
	 * 	Raises the {@link ComplexNumber} to the power n using de Moivre's formula.
	 * 	@param n The exponent to raise the {@link ComplexNumber} to. Must be >= 0.
	 * 	@return A new instance of {@link ComplexNumber}.
	 * 	@throws IllegalArgumentException If the given value is < 0.
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		//de Moivre's formula
		double r = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return new ComplexNumber(r * Math.cos(angle), r * Math.sin(angle));
	}
	
	/**
	 * 	Calculates the n-th root of the {@link ComplexNumber}.
	 * 	@param n The root to calculate. Must be > 0.
	 * 	@return Array of {@link ComplexNumber}s which are the roots.
	 * 	@throws IllegalArgumentException If the given value is >= 0.
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		double r = Math.pow(getMagnitude(), 1 / n);
		for(int k = 0; k < n; k++) {
			double angle = (getAngle() + 2 * k * Math.PI)/n;
			roots[k] = new ComplexNumber(r * Math.cos(angle), r * Math.sin(angle));
		}
		return roots;
	}
	
	/**
	 * 	Returns the {@link ComplexNumber} in the "Re+Imi" format. Re stands
	 * 	for the real part and Im for the imaginary part of the {@link ComplexNumber}.
	 * 	@return A string representation of the {@link ComplexNumber}.
	 */
	@Override
	public String toString() {
		if(imaginary >= 0) {
			return String.valueOf(real) + "+" + String.valueOf(imaginary) + "i";
		}
		return String.valueOf(real) + String.valueOf(imaginary) + "i";
	}

	/**
	 * 	Checks if an {@link Object} is equal to this object.
	 * 	It's considered equal if the absolute difference between the real
	 * 	parts and the absolute difference between the imaginary parts is
	 * 	smaller that the DIFFERENCE_TO_IGNORE.
	 * 	return True if the objects are equal, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return  Math.abs(real - other.real) < DIFFERENCE_TO_IGNORE && Math.abs(imaginary - other.imaginary) < DIFFERENCE_TO_IGNORE;
		}
	
	
	
}


