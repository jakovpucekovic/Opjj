package hr.fer.zemris.java.hw06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void testHextobyte() {
		byte[] expected = {1, -82, 34};
		byte[] calculated = Util.hextobyte("01aE22");
		assertEquals(expected[0], calculated[0]);
		assertEquals(expected[1], calculated[1]);
		assertEquals(expected[2], calculated[2]);
	}
	

	@Test
	public void testBytetohex() {
		byte[] array = {1, -82, 34};
		assertEquals("01ae22", Util.bytetohex(array));
	}
	
}
