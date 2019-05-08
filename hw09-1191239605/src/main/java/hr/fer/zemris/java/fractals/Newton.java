package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
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

//	public static void main(String[] args) {
//		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
//		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done");
//
//		List<Complex> roots = new ArrayList<>();
//		Scanner sc = new Scanner(System.in);
//		int i = 1;
//		System.out.printf("Root %d> ", i);
//		while(sc.hasNext()) {
//			String input = sc.nextLine();//TODO popravi da ne prihvaca prazan input, i da mora biti barem 2 roota pri unosu
//			if(input.equals("done")) {
//				break;
//			}			
//			try {
//				roots.add(parse(input));
//			} catch(NumberFormatException ex) {
//				System.out.println("Given argument is not a complex number.");
//				System.out.printf("Root %d> ", i);
//				continue;
//			}
//			System.out.printf("Root %d> ", ++i);
//			
//		}
//		roots.forEach(System.out::println);
//		
//		System.out.println("Image of fractal will appear shortly. Thank you.");
//		
//		FractalViewer.show(new MojProducer(roots));	
//		
//		sc.close();
//	}
	
	public static void main(String[] args) {
		List<Complex> roots = new ArrayList<Complex>();
		roots.add(Complex.ONE);
		roots.add(Complex.ONE_NEG);
		roots.add(Complex.IM);
		roots.add(Complex.IM_NEG);
		
		FractalViewer.show(new MojProducer(roots));
	}
	
	
	public static class Work implements Callable<Void>{

		private static final int maxIter = 16*16*16;
		private static final double convergenceThreshold = 0.001;
		private static final double rootTreshold = 0.002;
		
		private double reMin;
		private double reMax;
		private double imMin;
		private double imMax;
		private int height;
		private int width;
		private int yMin;
		private int yMax;
		private short[] data;
		private AtomicBoolean cancel;
		private ComplexRootedPolynomial rootedPoly;
		private ComplexPolynomial poly;
		
		public Work(double reMin, double reMax, double imMin, double imMax, int height, int width, int yMin, int yMax,
				short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rootedPoly, ComplexPolynomial poly) {
			super();
			this.reMin		= reMin;
			this.reMax 		= reMax;
			this.imMin 		= imMin;
			this.imMax 		= imMax;
			this.height 	= height;
			this.width 		= width;
			this.yMin 		= yMin;
			this.yMax 		= yMax;
			this.data 		= data;
			this.cancel 	= cancel;
			this.rootedPoly = rootedPoly;
			this.poly 		= poly;
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public Void call() throws Exception {
			
			int offset = yMin * width;
			for(int y = yMin; y < yMax; y++) {
				for(int x = 0; x < width; x++) {
					double a = ((width - x) * reMin + reMax * x )/width;
					double b = -((yMax - y) * imMin + imMax * (y - yMin) )/(yMax - yMin);
					
					Complex c = new Complex(a, b);
//					Complex c = mapToComplexPlain(x,y,0,width,0,height,reMin,reMax,imMin,imMax);

					Complex zn = c;
					int iter = 0;
					Complex znold;
					do {
						znold = zn;
						zn = zn.sub(poly.apply(zn).divide(poly.derive().apply(zn)));
						iter++;
					} while(zn.sub(znold).module() > convergenceThreshold && iter < maxIter);
					int index = rootedPoly.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short)(index + 1);
				}
			}
			
			return null;
		}
		
		private Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin, double reMax, double imMin, double imMax) {
			double a = ((xMax - x) * reMin + reMax * (x - xMin) )/(xMax - xMin);
			double b = -((yMax - y) * imMin + imMax * (y - yMin) )/(yMax - yMin);
			
			return new Complex(a, b);
		}
	}
	
	public static class MojProducer implements IFractalProducer{
		
		private ComplexRootedPolynomial rooterdPoly;
		private ComplexPolynomial poly;
		
		private int availableProcessors = Runtime.getRuntime().availableProcessors();
		private ExecutorService pool = Executors.newFixedThreadPool(availableProcessors, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				
				thread.setDaemon(true);
				return thread;
			}
		});
		
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
			
			short[] data = new short[width * height];
		
			List<Future<Void>> results = new ArrayList<>();
			int numberOfRegions = availableProcessors * 8;
			int numberOfYInRegion = height/numberOfRegions;
			
			for(int i = 0; i < numberOfRegions; ++i) {
				int yMin = i * numberOfYInRegion;
				int yMax = (i + 1)*numberOfYInRegion;
				if(i == numberOfRegions - 1) {
					yMax = height; //TODO why?
				}
				Work work = new Work(reMin, reMax, imMin, imMax, height, width, yMin, yMax, data, cancel,rooterdPoly,poly);
				results.add(pool.submit(work));
			}
			
			for(Future<Void> work : results) {
				try {
					work.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(poly.order() + 1), requestNo);
		
		}	

	}
	
	//mogu uzeti iz 2.dz ali treba doraditi i think
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
