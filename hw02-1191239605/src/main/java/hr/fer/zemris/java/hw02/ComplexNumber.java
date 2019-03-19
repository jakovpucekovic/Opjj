package hr.fer.zemris.java.hw02;

import java.lang.Math;
import java.util.Objects;

public class ComplexNumber {

	private double real;
	private double imaginary;
	
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	public static ComplexNumber parse(String s) {
		Objects.requireNonNull(s);
//		s = s.replace(" ", ""); //??
		if(s.matches("[+-]?\\d+(\\.\\d+)?")) {	//+-ili nista, min 1 znamenka pa opcionalno dec tocka i dec dio
			return new ComplexNumber(Double.parseDouble(s), 0);
		} else if(s.matches("\\+?i")) {	//+ ili nista pa i
			return new ComplexNumber(0, 1);
		} else if(s.matches("-i")) {	//-i
			return new ComplexNumber(0, -1);
		} else if(s.matches("[+-]?\\d+(\\.\\d+)?i")) {	//+- ili nista, 1 ili vise znamenki pa opcionalno dec tocka i dec dio pa i
			return new ComplexNumber(0, Double.parseDouble(s.replace("i", "")));
		} else if(s.matches("[+-]?\\d+(\\.\\d+)?(\\+|-)\\d*(\\.\\d+)?i")) {	//+- ili nista, 1 ili vise znamenki pa opcionalno dec tocka i dec dio pa + ili - pa to opet sa i na kraju
			double r = Double.parseDouble(s.replaceFirst("[+-]\\d*(\\.\\d+)?i", "")); 	//replacea imaginarni dio sa ""
			String iDio = s.replaceFirst("[+-]?\\d+(\\.\\d+)?", "");	//replacea realni dio sa ""
			double i;
			if(iDio.matches("\\+?i")) { //gledamo sto je dio sa i koji je ostao jer parseDouble ne zna parsirati i
				i = 1;
			} else if(iDio.equals("-i")) {
				i = -1;
			} else {
				i = Double.parseDouble(iDio.replace("i", ""));
			}
			return new ComplexNumber(r, i);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public double getReal() {
		return real;
	}
	
	public double getImaginary() {
		return imaginary;
	}

	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	public double getAngle() {//krivi raspon
		return Math.atan2(imaginary, real);	
	}
	
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}
	
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}
	
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real * c.real - imaginary * c.imaginary, real * c.imaginary + c.real * imaginary);
		//polar form
//		double r = getMagnitude() * c.getMagnitude();
//		double angle = getAngle() + c.getAngle();
//		return new ComplexNumber( r * Math.cos(angle), r * Math.asin(angle);
	}
	
	public ComplexNumber div(ComplexNumber c) {
		double div = 1/(real * real + imaginary * imaginary);
		return new ComplexNumber((real * c.real + imaginary * c.imaginary)/div, (real * c.imaginary - c.real * imaginary)/div);
		//polar form
//		double r = getMagnitude() / c.getMagnitude();
//		double angle = getAngle() - c.getAngle();
//		return new ComplexNumber(r * Math.cos(angle), r * Math.sin(angle));
	}
	
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		//de Moivre's formula
		double r = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return new ComplexNumber(r * Math.cos(angle), r * Math.sin(angle));
	}
	
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
	
	@Override
	public String toString() {
		return "ComplexNumber [real=" + real + ", imaginary=" + imaginary + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return Double.doubleToLongBits(imaginary) == Double.doubleToLongBits(other.imaginary)
				&& Double.doubleToLongBits(real) == Double.doubleToLongBits(other.real);
	}
	
	
	
}


