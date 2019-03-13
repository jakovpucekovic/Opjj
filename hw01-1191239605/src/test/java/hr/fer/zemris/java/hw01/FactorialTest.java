package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FactorialTest {

	@Test
	void test0() {
		long result = Factorial.factorial(0);
		assertEquals(1, result);
	}
	
	@Test
	void test3() {
		long result = Factorial.factorial(3);
		assertEquals(6l, result);
	}
	
	@Test
	void test10() {
		long result = Factorial.factorial(10);
		assertEquals(3628800l, result);
	}
	
	@Test 
	void test20() {
		long result = Factorial.factorial(20);
		assertEquals(2432902008176640000l, result);
	}
	
	@Test
	void negativeNumber() {
		assertThrows(IllegalArgumentException.class,()-> Factorial.factorial(-7));
	}
}
