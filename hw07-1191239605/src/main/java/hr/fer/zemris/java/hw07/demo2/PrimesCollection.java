package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *	Class which represents a collection of the first n prime 
 *	numbers. The prime numbers are not stored, but calculated
 *	as needed.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {

	/**Size of the collection*/
	private int size;
	
	/**
	 * 	Constructs a new PrimesCollection of the given size.
	 * 	@param size Size of the collection.
	 * 	@throws IllegalArgumentException If given size if < zero.
	 */
	public PrimesCollection(int size) {
		if(size < 0) {
			throw new IllegalArgumentException("Size of collection cannot be negative.");
		}
		this.size = size;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}
	
	/**
	 * 	Checks whether a given number is prime by checking
	 * 	if it is divisible by any number in the range 
	 * 	from 2 to sqrt(n)+1.
	 * 	@param n Number to check if it's prime.
	 * 	@return <code>true</code> if the number is prime, <code>false</code> if not.
	 */
	private boolean isPrime(int n) {
		for(int i = 2, s = (int) (Math.round(Math.sqrt(n)) + 1); i < s; ++i) {
			if(n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 	Private class which iterates over {@link PrimesCollection}
	 * 	and calculates the next prime number as needed.
	 */
	private class PrimesIterator implements Iterator<Integer>{

		/**Index of the current prime.*/
		private int currentIndex = 0;
		
		/**Last returned prime.*/
		private int lastPrime = -1;
		
		/**
		 * 	{@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		/**
		 * 	{@inheritDoc}
		 */
		@Override
		public Integer next() {
			if(lastPrime == -1) {
				++currentIndex;
				lastPrime = 2;
				return 2;
			}
			if(currentIndex >= size) {
				throw new NoSuchElementException("No more elements.");
			}
			int n = ++lastPrime;
			while(!isPrime(n)) {
				++n;
			}
			++currentIndex;
			lastPrime = n;
			return lastPrime;
		}
		
	}
	
}
