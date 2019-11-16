package hr.fer.zemris.java.hw07.demo2;

/**
 * 	Demo class which prints the first 5 prime numbers 
 *	using a {@link PrimesCollection}. 
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class PrimesDemo1 {
	
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: "+prime);
		}
	}
}
