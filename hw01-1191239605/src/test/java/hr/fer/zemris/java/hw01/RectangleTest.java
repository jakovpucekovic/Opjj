package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RectangleTest {

	@Test
	void circumferenceTest() {
		double result = Rectangle.circumference(2.0d, 7.0d);
		assertEquals(18.0d, result);
	}
	
	@Test
	void circumferenceTestLesserThanZero() {
		assertThrows(IllegalArgumentException.class, ()->Rectangle.circumference(-3.0d, 7.0d));
		assertThrows(IllegalArgumentException.class, ()->Rectangle.circumference(-3.0d, -7.0d));
		assertDoesNotThrow(()->Rectangle.circumference(0.0d, 0.0d));
	}
	
	@Test
	void areaTest() {
		double result = Rectangle.area(3.14d, 1.41d);
		assertEquals(4.4274, result);
	}

	@Test
	void areaTestLesserThanZero() {
		assertThrows(IllegalArgumentException.class, ()->Rectangle.area(-3.0d, 7.0d));
		assertThrows(IllegalArgumentException.class, ()->Rectangle.area(-3.0d, -7.0d));
		assertDoesNotThrow(()->Rectangle.area(0.0d, 0.0d));	
	}
}
