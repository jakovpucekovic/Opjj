package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void constructorTestEmptyList() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
	}
	
	@Test
	public void constructorTestCollection() {		
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		assertEquals(3, list.size());
		assertEquals(0, list.get(0));
		assertEquals(1, list.get(1));
		assertEquals(2, list.get(2));
	}
	
	@Test
	public void sizeTest() {
		assertEquals(0, new LinkedListIndexedCollection().size());
		assertEquals(3, new LinkedListIndexedCollection(makeCollection()).size());
	}
	
	@Test
	public void containsTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		assertTrue(list.contains(0));
		assertTrue(list.contains(2));
		assertFalse(list.contains(7));
	}
	
	@Test
	public void removeObjectTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		list.add(3);
		list.add(4);
		
		assertTrue(list.remove((Object) 4));
		assertTrue(list.remove((Object) 0));
		assertTrue(list.remove((Object) 2));
		assertTrue(list.remove((Object) 1));
		assertTrue(list.remove((Object) 3));
		assertFalse(list.remove((Object) 11));
		assertFalse(list.remove((Object) 0));
		assertFalse(list.remove(null));
	}
	
	@Test
	public void toArrayTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		Object[] emptyArray = list.toArray();
		assertEquals(0, emptyArray.length);
		
		list = new LinkedListIndexedCollection(makeCollection());
		Object[] objectArray = list.toArray();
		assertEquals(3, objectArray.length);
		assertEquals(0, objectArray[0]);
		assertEquals(1, objectArray[1]);
		assertEquals(2, objectArray[2]);
	}
	
	
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		list.clear();
		assertEquals(0, list.size());
	}
	
	@Test
	public void addTestNull() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, ()-> list.add(null));
	}
	
	@Test
	public void addTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(1);
		assertEquals(1, list.size());
		assertEquals(1, list.get(list.size() - 1));
		list.add(2);
		assertEquals(2, list.size());
		assertEquals(2, list.get(list.size() - 1));
		list.add(1);
		assertEquals(3, list.size());
		assertEquals(1, list.get(list.size() - 1));
	}
	
	@Test
	public void getTestOutOfBounds() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->list.get(0));
		assertThrows(IndexOutOfBoundsException.class, ()->list.get(1));
	}
	
	@Test
	public void getTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		assertEquals(0, list.get(0));
		assertEquals(1, list.get(1));
		assertEquals(2, list.get(2));
	}
	
	@Test
	public void insertTestNull() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, ()->list.insert(null, 0));
	}
	
	@Test
	public void insertTestIndexOutOfBounds() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->list.insert(1, -1));
		assertThrows(IndexOutOfBoundsException.class, ()->list.insert(1, 3));
	}
	
	@Test
	public void insertTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		list.insert(7, 0);
		list.insert(8, list.size());
		list.insert(9, 2);

		assertEquals(7, list.get(0));
		assertEquals(0, list.get(1));
		assertEquals(9, list.get(2));
		assertEquals(1, list.get(3));
		assertEquals(2, list.get(4));
		assertEquals(8, list.get(5));
	}
	
	@Test
	public void indexOfTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		assertEquals(1, list.indexOf(1));
		assertEquals(2, list.indexOf(2));
		assertEquals(-1, list.indexOf(null));
		assertEquals(-1, list.indexOf(7));
	}
	
	@Test
	public void removeTestIndexOutOfBounds() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		assertThrows(IndexOutOfBoundsException.class, ()->list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->list.remove(7));
	}
	
	@Test
	public void removeTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(makeCollection());
		list.remove(1);
		assertFalse(list.contains(1));
	}
	
	@Test
	public void removeIndexTestOnlyOneElementInList() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add(2);
		list.remove(0);
		assertTrue(list.isEmpty());
	}
	
	/**
	 *	Makes an {@link ArrayIndexedCollection} for testing purposes.
	 *	It has 3 elements: 0, 1 and 2 and their value is equal to their
	 *	position in the {@link ArrayIndexedCollection}.
	 *	@return The Collection.
	 */
	private Collection makeCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(0);
		col.add(1);
		col.add(2);		
		return col;
	}
	
}
