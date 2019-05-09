package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
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
 *	Class which draws allows viewing of fractals based on Newton-Raphson iteration.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Newton {

	/**
	 *	Main method which interacts with the user.
	 *	@param args None. 
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done");

		List<Complex> roots = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int i = 1;
		System.out.printf("Root %d> ", i);
		while(true) {
			String input = sc.nextLine();
			if(input.toLowerCase().equals("done")) {
				break;
			}			
			try {
				roots.add(Complex.parse(input));
			} catch(IllegalArgumentException | NullPointerException ex) {
				System.out.println("Given argument is not a complex number.");
				System.out.printf("Root %d> ", i);
				continue;
			}
			System.out.printf("Root %d> ", ++i);	
		}
		sc.close();
		
		if(roots.size() < 2) {
			System.out.println("Should give atleast 2 roots.");
			return;
		}
				
		System.out.println("Image of fractal will appear shortly. Thank you.");		
		FractalViewer.show(new MojProducer(roots));	
		
	}
	
	/**
	 *   Class which represents work that should be done.
	 */
	public static class Work implements Callable<Void>{

		
		/**Number of iterations to do*/
		private static final int maxIter = 16*16*16;
		/**Convergence treshold*/
		private static final double convergenceTreshold = 0.001;
		/**Root treshold*/
		private static final double rootTreshold = 0.002;
		
		
		/**Minimal real value on the complex plane.*/
		private double reMin;
		/**Maximal real value on the complex plane.*/
		private double reMax;
		/**Minimal imaginary value on the complex plane.*/
		private double imMin;
		/**Maximal imaginary value on the complex plane.*/
		private double imMax;
		/**Height of the window in pixels.*/
		private int height;
		/**Width of the window in pixels.*/
		private int width;
		/**Minimal height for which the {@link Work} should be done.*/
		private int yMin;
		/**Maximal height for which the {@link Work} should be done.*/
		private int yMax;
		/**Data containing the colors.*/
		private short[] data;
		/**Signals whether to cancel an operation.*/
		private AtomicBoolean cancel;
		/**Polynomial in rooted form.*/
		private ComplexRootedPolynomial rootedPoly;
		/**Polynomial in "normal" form.*/
		private ComplexPolynomial poly;
		
		/**
		 * 	Construct a new {@link Work} with the given parameters.
		 * 	@param reMin Minimal real value on the complex plane.
		 * 	@param reMax Maximal real value on the complex plane.
		 * 	@param imMin Minimal imaginary value on the complex plane.
		 * 	@param imMax Maximal imaginary value on the complex plane.
		 * 	@param height Height of the window in pixels.
		 * 	@param width Width of the window in pixels.
		 * 	@param yMin Minimal height for which the {@link Work} should be done.
		 * 	@param yMax Maximal height for which the {@link Work} should be done.
		 * 	@param data Data containing the colors.
		 * 	@param cancel Signals whether to cancel an operation.
 		 * 	@param rootedPoly Polynomial in rooted form.
		 * 	@param poly Polynomial in "normal" form.
		 */
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
					double a = ((width - x) * reMin + reMax * x)/width;
					double b = ((height - y) * imMax + imMin * y)/height;
					Complex c = new Complex(a, b);
					
					Complex zn = c;
					int iter = 0;
					Complex znold;
					do {					
						znold = zn;
						zn = zn.sub(poly.apply(zn).divide(poly.derive().apply(zn)));
						iter++;
					} while(zn.sub(znold).module() > convergenceTreshold && iter < maxIter);
					int index = rootedPoly.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short)(index + 1);
				}
				if(cancel.get()) {
					return null;
				}
			}
			return null;
		}
		
	}
	
	public static class MojProducer implements IFractalProducer{
				
		/**Polynomial in rooted form.*/
		private ComplexRootedPolynomial rootedPoly;
		/**Polynomial in "normal" form.*/
		private ComplexPolynomial poly;
		/**Pool which supplies daemon threads.*/
		private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				
				thread.setDaemon(true);
				return thread;
			}
		});
		
		/**
		 * 	Constructs a new {@link MojProducer} from the given 
		 * 	{@link List} of {@link Complex} roots.
		 * 	@param roots {@link List} of {@link Complex} roots of the function.
		 */
		public MojProducer(List<Complex> roots) {
			rootedPoly = new ComplexRootedPolynomial(roots);
			poly = rootedPoly.toComplexPolynom();
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			short[] data = new short[width * height];
		
			List<Future<Void>> results = new ArrayList<>();
			int numberOfRegions = Runtime.getRuntime().availableProcessors()* 8;
			int numberOfYInRegion = height/numberOfRegions;

			//Generate work
			for(int i = 0; i <= numberOfRegions; ++i) {
				int yMin = i * numberOfYInRegion;
				int yMax = (i + 1)*numberOfYInRegion;
				if(i == numberOfRegions - 1) {
					yMax = height;
				}
				Work work = new Work(reMin, reMax, imMin, imMax, height, width, yMin, yMax, data, cancel, rootedPoly, poly);
				results.add(pool.submit(work));
			}
			
			//Wait for work to be done
			for(Future<Void> work : results) {
				try {
					work.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			observer.acceptResult(data, (short)(poly.order() + 1), requestNo);
		}	
	}
	
}
