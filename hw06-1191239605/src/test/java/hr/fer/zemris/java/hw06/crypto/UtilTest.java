package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	public void testHextobyteThrows() {
		assertThrows(IllegalArgumentException.class,() -> Util.hextobyte("123"));
		assertThrows(IllegalArgumentException.class,() -> Util.hextobyte("aaga"));
		assertThrows(IllegalArgumentException.class,() -> Util.hextobyte("0123456789abcdefh"));
	}
	
	@Test
	public void testBytetohex() {
		byte[] array = {1, -82, 34};
		assertEquals("01ae22", Util.bytetohex(array));
	}
	
	@Test
	public void testHextobyteEmptyInput() {
		byte[] calculated = Util.hextobyte("");
		assertEquals(0, calculated.length);
	}
	
	@Test
	public void testBytetohexEmptyInput() {
		byte[] array = {};
		assertEquals("", Util.bytetohex(array));
	}
}
