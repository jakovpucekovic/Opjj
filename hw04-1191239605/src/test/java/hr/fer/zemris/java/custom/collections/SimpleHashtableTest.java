package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

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
		assertFalse(()->hashTable.containsKey(false));
		assertFalse(()->hashTable.containsKey(3.0));
		assertFalse(()->hashTable.containsKey(27));

		assertThrows(NullPointerException.class ,()->hashTable.containsKey(null));
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
		hashTable.put("9", 1.0);
		hashTable.put("17", 1.0);
		hashTable.put("2", 2.0);
		hashTable.put("10", 2.0);
		hashTable.put("4", 4.0);
		
		assertEquals(6, hashTable.size());
		
		hashTable.remove(null);
		hashTable.remove("ivo");
		hashTable.remove("");
		hashTable.remove(3.14);
		hashTable.remove(1);
		
		assertEquals(6, hashTable.size());

		hashTable.remove("4");
		assertEquals(5, hashTable.size());
		assertFalse(()->hashTable.containsKey("4"));

		hashTable.remove("9");
		hashTable.remove("17");
		hashTable.remove("1");
		assertEquals(2, hashTable.size());
		assertFalse(()->hashTable.containsKey("1"));
		assertFalse(()->hashTable.containsKey("9"));
		assertFalse(()->hashTable.containsKey("17"));
		

		hashTable.remove("2");
		hashTable.remove("10");
		assertEquals(0, hashTable.size());
		assertFalse(()->hashTable.containsKey("10"));
		assertFalse(()->hashTable.containsKey("2"));
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);		
		hashTable.put("6", 6.0);
		
		hashTable.clear();
		assertTrue(hashTable.isEmpty());
	}
	
	@Test
	public void testIterator() {
		SimpleHashtable<String, Double> hashTable = new SimpleHashtable<>(2);		
		hashTable.put("1", 1.0);
		hashTable.put("2", 2.0);
		hashTable.put("3", 3.0);
		
		var iter = hashTable.iterator();
		
		assertTrue(iter.hasNext());
		assertEquals(1.0, iter.next().getValue());
		assertTrue(iter.hasNext());
		
		iter.remove();
		assertFalse(hashTable.containsKey("1"));
		
		assertThrows(IllegalStateException.class ,()->iter.remove());
	}
	

	@Test
	public void testIteratorExceptions() {
		SimpleHashtable<String, Double> hashTable1 = new SimpleHashtable<>(2);
		var iter1 = hashTable1.iterator();
		assertThrows(NoSuchElementException.class ,()->iter1.next());
		assertThrows(IllegalStateException.class ,()->iter1.remove());

		SimpleHashtable<String, Double> hashTable2 = new SimpleHashtable<>(2);
		hashTable2.put("1", 1.);
		hashTable2.put("5", 5.);
		var iter2 = hashTable2.iterator();
		assertDoesNotThrow(()->iter2.next());	
		assertDoesNotThrow(()->iter2.remove());	
		assertDoesNotThrow(()->iter2.next());	
		assertDoesNotThrow(()->iter2.remove());	
		assertThrows(NoSuchElementException.class ,()->iter2.next());
		assertThrows(IllegalStateException.class ,()->iter2.remove());	
	
		SimpleHashtable<String, Double> hashTable3 = new SimpleHashtable<>(2);
		hashTable3.put("1", 1.);
		hashTable3.put("0", 0.);
		hashTable3.put("9", 1.);
		hashTable3.put("8", 0.);
		var iter3 = hashTable3.iterator();
		assertDoesNotThrow(()->iter3.next());	
		assertDoesNotThrow(()->iter3.next());	
		assertDoesNotThrow(()->iter3.remove());	
		assertDoesNotThrow(()->iter3.next());	
		
		hashTable3.clear();
		assertThrows(ConcurrentModificationException.class ,()->iter3.next());
		assertThrows(ConcurrentModificationException.class ,()->iter3.hasNext());
		assertThrows(ConcurrentModificationException.class ,()->iter3.remove());
	
		SimpleHashtable<String, Double> hashTable4 = new SimpleHashtable<>(2);
		var iter4 = hashTable4.iterator();
		hashTable4.put("1", 1.);
		assertThrows(ConcurrentModificationException.class ,()->iter4.next());
		assertThrows(ConcurrentModificationException.class ,()->iter4.hasNext());
		assertThrows(ConcurrentModificationException.class ,()->iter4.remove());
	
		SimpleHashtable<String, Double> hashTable5 = new SimpleHashtable<>(2);
		hashTable5.put("1", 1.);
		var iter5 = hashTable5.iterator();
		hashTable5.remove("1");		
		assertThrows(ConcurrentModificationException.class ,()->iter5.next());
		assertThrows(ConcurrentModificationException.class ,()->iter5.hasNext());
		assertThrows(ConcurrentModificationException.class ,()->iter5.remove());
	}
	
	@Test
	public void testToString() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<>(2);
		hashTable.put("ivo", 3);
		hashTable.put("mara", 4);
		hashTable.put("mislav", 6);
		hashTable.put("bara", 4);
		hashTable.put("joza", 4);
		assertEquals("[ivo=3, mara=4, joza=4, mislav=6, bara=4]", hashTable.toString());
	}
	
}
