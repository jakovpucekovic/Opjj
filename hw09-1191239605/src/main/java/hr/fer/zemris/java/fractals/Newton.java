package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 *	Newton TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done");

		List<Complex> roots = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int i = 0;
		System.out.printf("Root %d> ", i);
		while(sc.hasNext()) {
			String input = sc.nextLine();//TODO popravi da ne prihvaca prazan input
			if(input.equals("done")) {
				break;
			}			
			try {
				roots.add(parse(input));
			} catch(NumberFormatException ex) {
				System.out.println("Given argument is not a complex number.");
				System.out.printf("Root %d> ", i);
				continue;
			}
			System.out.printf("Root %d> ", ++i);
			
		}
		roots.forEach(System.out::println);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new MojProducer(roots));	
		
		sc.close();
	}
	
	
	private static class MojProducer implements IFractalProducer{
		
		private static final int maxIter = 16;
		private static final double convergenceThreshold = 1e-3;
		private static final double rootTreshold = 2e-3;
		
		private ComplexRootedPolynomial rooterdPoly;
		private ComplexPolynomial poly;
		
//		private ExecutorService pool = Executors.newFixedThreadPool(nThreads, threadFactory)
		
		/**
		 * 	Constructs a new Newton.MojProducer.
		 * 	TODO javadoc
		 */
		public MojProducer(List<Complex> roots) {
			rooterdPoly = new ComplexRootedPolynomial(roots);
			poly = rooterdPoly.toComplexPolynom();
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			
			short[] data = new short[width * height];//mozda bez +1
			
			calculate(reMin, reMax, imMin, imMax, width, height, data, 0, height-1, cancel);
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(poly.order()+1), requestNo);
		
		}	
	
		private void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, short[] data, int yMin, int yMax, AtomicBoolean cancel) {
			int offset = yMin * width;
			for(int y = yMin; y < yMax; y++) {
				for(int x = 0; x < width; x++) {
					Complex c = mapToComplexPlain(x,y,0,width,yMin,yMax,reMin,reMax,imMin,imMax);
					Complex zn = c;
					int iter = 0;
					Complex znold;
					do {
						znold = zn;
						zn = zn.sub(poly.apply(zn).divide(poly.derive().apply(zn)));
						iter++;
					} while(zn.sub(znold).module()>convergenceThreshold && iter < maxIter);
					int index = rooterdPoly.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short)(index + 1);
				}
			}
		}
		
		private Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin, double reMax, double imMin, double imMax) {
			double a = ((xMax - x) * reMin + reMax * (x - xMin) )/(xMax - xMin);
			double b = ((yMax - y) * imMin + imMax * (y - yMin) )/(yMax - yMin);
			
			
			return new Complex(a, b);
		}
		
		
	}
	
	
	public static Complex parse(String input) {//TODO ne radi za "+i"
		input = input.replaceAll("\\s+", "");
		int plus = input.lastIndexOf('+');
		int minus = input.lastIndexOf('-');
		if(plus > 0) {
			if(plus + 2 < input.length()) {
				return new Complex(Double.parseDouble(input.substring(0, plus)),
								   Double.parseDouble(input.substring(plus + 2)) );
			}
			return new Complex(Double.parseDouble(input.substring(0, plus)), 1);
		}
		if(minus > 0) {
			if(minus + 2 < input.length()) {
				return new Complex(Double.parseDouble(input.substring(0, minus)),
								  -Double.parseDouble(input.substring(minus + 2)) );
			}
			return new Complex(Double.parseDouble(input.substring(0, minus)), -1);
		}
		if(input.equals("i")) {
			return Complex.IM;
		}
		if(input.equals("-i")) {
			return Complex.IM_NEG;
		}
		if(input.indexOf('i')!= -1) {
			return new Complex(0, Double.parseDouble(input.substring(input.indexOf('i')+1)));
		}
		return new Complex(Double.parseDouble(input), 0);
	}
	
}
