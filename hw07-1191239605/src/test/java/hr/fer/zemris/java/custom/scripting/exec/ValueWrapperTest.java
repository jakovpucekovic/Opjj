package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {

	@Test
	public void testExpectedBehaviour(){
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(13.0, v3.getValue());
		assertEquals(1, v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		assertEquals(13, v5.getValue());
		assertEquals(1, v6.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class,()->v7.add(v8.getValue()));
	}
	
	@Test
	public void testThrows() {
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class,() -> vv1.add(Integer.valueOf(5)));
		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class,() -> vv2.add(Boolean.valueOf(true)));
	}

	@Test
	public void testScinetificSFormat() {
		ValueWrapper v1 = new ValueWrapper("1E2");
		v1.add(0);
		assertEquals(100.0, v1.getValue());
		
		v1.subtract("5.0e1");
		assertEquals(50.0, v1.getValue());
		
		v1.multiply("5e-1");
		assertEquals(25.0, v1.getValue());
		
		v1.divide("25E-2");
		assertEquals(100.0, v1.getValue());
	}
	
	@Test
	public void testEquals() {
		ValueWrapper v1 = new ValueWrapper(null);
		assertEquals(0, v1.numCompare(null));
		assertEquals(-1, v1.numCompare(1.0));
		assertEquals(1, v1.numCompare(-1.0));

		ValueWrapper v2 = new ValueWrapper(1.0);
		
		assertEquals(0, v2.numCompare(1.0000000000000001));
		assertEquals(-1, v2.numCompare("2"));
		assertEquals(1, v2.numCompare("0.5"));
	}
	
}