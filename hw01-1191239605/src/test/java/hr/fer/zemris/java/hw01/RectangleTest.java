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
	void areaTest() {
		double result = Rectangle.area(3.14d, 1.41d);
		assertEquals(4.4274, result);
	}
	
}
