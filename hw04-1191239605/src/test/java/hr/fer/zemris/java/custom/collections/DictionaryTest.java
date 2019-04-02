package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void testConstructor() {
		Dictionary<String, Integer> dic = new Dictionary<>();
	
	}
	
	@Test
	public void testIsEmpty() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		assertTrue(dic.isEmpty());
		
		dic.put("pero", 3);
		assertFalse(dic.isEmpty());
		
	}
	
	@Test
	public void testSize() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		assertEquals(0, dic.size());
		
		dic.put("pero", 3);
		assertEquals(1, dic.size());
	}

	@Test
	public void testClear() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		dic.clear();
		assertEquals(0, dic.size());
		
		dic.put("pero", 3);
		dic.clear();
		assertEquals(0, dic.size());
	}

	@Test
	public void testGetExisting() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		dic.put("pero", 3);
		dic.put("ivana", 3);
		dic.put("2", 7);
		dic.put("null", null);
		
		assertEquals(3, dic.get("pero"));
		assertEquals(3, dic.get("ivana"));
		assertEquals(7, dic.get("2"));
		assertEquals(null, dic.get("null"));
	}
	
	@Test
	public void testGetNonExisting() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		dic.put("pero", 3);
		dic.put("ivana", 3);
		dic.put("2", 7);
		dic.put("null", null);
		
		assertEquals(null, dic.get("3"));
		assertEquals(null, dic.get(3));
		assertEquals(null, dic.get(true));
		assertEquals(null, dic.get(2.1));
		assertEquals(null, dic.get(null));
	}
	
	@Test
	public void testPutNullKey() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		assertThrows(NullPointerException.class, ()->dic.put(null, 3));
	}
	
	@Test
	public void testPutSameKey() {
		Dictionary<String, Integer> dic = new Dictionary<>();
		dic.put("1", 3);
		dic.put("2", 3);
		dic.put("3", 3);
		
		dic.put("2", 2);
		assertEquals(2, dic.get("2"));
		
	}
	
	
}
