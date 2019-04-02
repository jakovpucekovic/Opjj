package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
	public void testIsEmpty(){
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);
		
		assertTrue(()->hashTable.isEmpty());
		hashTable.put("ivo", 3.0);
		assertFalse(()->hashTable.isEmpty());
		hashTable.remove("ivo");
		assertTrue(()->hashTable.isEmpty());
	}
	
	@Test
	public void testSize() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);
		
		assertEquals(0, hashTable.size());
		hashTable.put("ivo", 3.0);
		assertEquals(1, hashTable.size());
		hashTable.remove("ivo");
		assertEquals(0, hashTable.size());
	}
	
	@Test
	public void testContainsKey() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);
		hashTable.put("ivo", 3.0);
		hashTable.put("null", 7.0);
		
		assertTrue(()->hashTable.containsKey("ivo"));
		assertTrue(()->hashTable.containsKey("null"));
		
		assertFalse(()->hashTable.containsKey("jabuka"));
		assertFalse(()->hashTable.containsKey(null));
		assertFalse(()->hashTable.containsKey(false));
		assertFalse(()->hashTable.containsKey(3.0));
		assertFalse(()->hashTable.containsKey(27));
	}
	
	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);
		hashTable.put("ivo", 3.0);
		hashTable.put("null", 7.0);
		
		assertTrue(()->hashTable.containsValue(3.0));
		assertTrue(()->hashTable.containsValue(7.0));

		assertFalse(()->hashTable.containsValue(3));
		assertFalse(()->hashTable.containsValue(null));
		assertFalse(()->hashTable.containsValue("ivo"));
		assertFalse(()->hashTable.containsValue(true));
		assertFalse(()->hashTable.containsValue(91.0));
	}
	
	@Test
	public void testPutKeyNull() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);
		
		assertThrows(NullPointerException.class, ()->hashTable.put(null, 2.0));
	}
	
	@Test
	public void testPut() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);		
		
		hashTable.put("Ivana", 0.0);
		assertEquals(1, hashTable.size());
		assertTrue(hashTable.containsKey("Ivana"));
		assertTrue(hashTable.containsValue(0.0));
		
		hashTable.put("Jasna", 0.0);
		assertEquals(2, hashTable.size());
		assertTrue(hashTable.containsKey("Jasna"));
		assertTrue(hashTable.containsValue(0.0));
	}
	
	@Test
	public void testPutSameKey() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);		
		
		hashTable.put("Ivana", 0.0);
		assertEquals(1, hashTable.size());
		assertTrue(hashTable.containsKey("Ivana"));
		assertTrue(hashTable.containsValue(0.0));
		
		hashTable.put("Ivana", 10.0);
		assertEquals(1, hashTable.size());
		assertTrue(hashTable.containsKey("Ivana"));
		assertTrue(hashTable.containsValue(10.0));
	}
	
	@Test
	public void testGet() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);		
		hashTable.put("Ivana", 1.0);
		hashTable.put("Marko", 2.0);
		hashTable.put("Ivan", 3.0);
		
		assertEquals(1.0, hashTable.get("Ivana"));
		assertEquals(2.0, hashTable.get("Marko"));
		assertEquals(3.0, hashTable.get("Ivan"));
		
		assertNull(hashTable.get("petar"));
		assertNull(hashTable.get(null));
		assertNull(hashTable.get(3));
		assertNull(hashTable.get(1.0));
		assertNull(hashTable.get(""));
	}
	
	@Test
	public void testRemove() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);		
		hashTable.put("1", 1.0);
		hashTable.put("2", 2.0);
		hashTable.put("3", 3.0);
		hashTable.put("4", 4.0);
		hashTable.put("5", 5.0);
		hashTable.put("6", 6.0);
		
		assertEquals(6, hashTable.size());
		
		hashTable.remove(null);
		hashTable.remove("ivo");
		hashTable.remove("");
		hashTable.remove(3.14);
		hashTable.remove(1.0);
		hashTable.remove(1);
		
		assertEquals(6, hashTable.size());

		hashTable.remove("2");
		assertEquals(5, hashTable.size());
		assertFalse(()->hashTable.containsKey("2"));

		hashTable.remove("1");
		hashTable.remove("6");
		assertEquals(3, hashTable.size());
		assertFalse(()->hashTable.containsKey("1"));
		assertFalse(()->hashTable.containsKey("6"));

		
		
	}
}
