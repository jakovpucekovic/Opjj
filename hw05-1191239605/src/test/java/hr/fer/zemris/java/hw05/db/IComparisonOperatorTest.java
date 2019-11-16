package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IComparisonOperatorTest {
	
	@Test
	public void testEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("Pero", "Pero"));
		assertTrue(ComparisonOperators.EQUALS.satisfied("", ""));
		assertFalse(ComparisonOperators.EQUALS.satisfied("Marko", "Pero"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("pero", "Pero"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("", "Pero"));
	}
	
	@Test
	public void testDoesntEqual() {
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Pero", "Pero"));
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("", ""));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Marko", "Pero"));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("pero", "Pero"));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("", "Pero"));
	}
	
	@Test
	public void testLess() {            
		assertFalse(ComparisonOperators.LESS.satisfied("Pero", "Pero"));
		assertFalse(ComparisonOperators.LESS.satisfied("", ""));
		assertFalse(ComparisonOperators.LESS.satisfied("Pero", "Marko"));
		assertTrue(ComparisonOperators.LESS.satisfied("Marko", "Pero"));
		assertTrue(ComparisonOperators.LESS.satisfied("Pero", "pero"));
		assertTrue(ComparisonOperators.LESS.satisfied("", "Pero"));
	}
	
	@Test
	public void testLessOrEqual() {            
		assertTrue(ComparisonOperators.LESS_OR_EQUAL.satisfied("", ""));
		assertTrue(ComparisonOperators.LESS_OR_EQUAL.satisfied("Pero", "Pero"));
		assertTrue(ComparisonOperators.LESS_OR_EQUAL.satisfied("Marko", "Pero"));		
		assertTrue(ComparisonOperators.LESS_OR_EQUAL.satisfied("Pero", "pero"));
		assertTrue(ComparisonOperators.LESS_OR_EQUAL.satisfied("", "Pero"));
		assertFalse(ComparisonOperators.LESS_OR_EQUAL.satisfied("Pero", "Marko"));
	}
	

	@Test
	public void testGreater() {         
		assertFalse(ComparisonOperators.GREATER.satisfied("Pero", "Pero"));
		assertFalse(ComparisonOperators.GREATER.satisfied("", ""));
		assertFalse(ComparisonOperators.GREATER.satisfied("Marko", "Pero"));
		assertFalse(ComparisonOperators.GREATER.satisfied("Pero", "pero"));
		assertFalse(ComparisonOperators.GREATER.satisfied("", "Pero"));
		assertTrue(ComparisonOperators.GREATER.satisfied("Pero", "Marko"));
	}
	
	@Test
	public void testGreaterOrEqual() {            
		assertTrue(ComparisonOperators.GREATER_OR_EQUAL.satisfied("", ""));
		assertTrue(ComparisonOperators.GREATER_OR_EQUAL.satisfied("Pero", "Pero"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUAL.satisfied("Pero", "Marko"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUAL.satisfied("Pero", "pero"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUAL.satisfied("", "Pero"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUAL.satisfied("Marko", "Pero"));		
	}                                  
	
	@Test
	public void testLike() {
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("A", "*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "abc"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "*abc"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "*bc"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "*c"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "a*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "a*c"));
		assertTrue(ComparisonOperators.LIKE.satisfied("abc", "abc*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("", ""));

		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertFalse(ComparisonOperators.LIKE.satisfied("abc", "A*bc"));
		assertFalse(ComparisonOperators.LIKE.satisfied("abc", ""));
		
	}
}
