package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void ConstructorTestSizeZero() {
		assertThrows(IllegalArgumentException.class, ()-> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void ConstructorTestNullCollection() {
		assertThrows(NullPointerException.class, ()->new ArrayIndexedCollection(null));
		assertThrows(NullPointerException.class, ()->new ArrayIndexedCollection(null, 7));
	}
	
	@Test
	public void ConstructorTestDefault() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
	}

	@Test
	public void ConstructorTestCollection() {
		ArrayIndexedCollection array = makeArray(5);
		ArrayIndexedCollection array2 = new ArrayIndexedCollection(array);
		assertEquals(array.size(), array2.size());
		for(int i = 0; i < array.size(); i++) {
			assertEquals(array.get(i), array2.get(i));
		}
	}
	
	@Test
	public void ConstructorTestCollectionAndInitialCapacityLowerThan0() {
		assertThrows(IllegalArgumentException.class, ()-> new ArrayIndexedCollection(makeArray(5), -3));
	}
	
	@Test
	public void ConstructorTestCollectionAndInitialCapacity() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(makeArray(20), 18);
		assertEquals(20, array.size());
	}
	
	@Test
	public void sizeTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
		array.add(7);
		assertEquals(1, array.size());
	}
	
	@Test
	public void addTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(7);
		assertThrows(NullPointerException.class, ()->array.add(null));
	}
	
	@Test
	public void containsTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertFalse(array.contains(7));
		array.add(3);
		array.add(7);
		assertTrue(array.contains(3));
		assertFalse(array.contains(8));
		assertFalse(array.contains(null));
	}
	
	@Test
	public void isEmptyTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertTrue(array.isEmpty());
		array.add(7);
		assertFalse(array.isEmpty());
		array.clear();
		assertTrue(array.isEmpty());
	}
	
	@Test 
	public void removeObjectValueTest() {
		ArrayIndexedCollection array = makeArray(10);
		assertTrue(array.remove((Object) 0));
		assertTrue(array.remove((Object) 9));
		assertFalse(array.remove((Object) 11));
		assertFalse(array.remove((Object) 0));
		assertFalse(array.remove(null));
	}

	@Test
	public void toArrayTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Object[] emptyArray = array.toArray();
		assertEquals(0, emptyArray.length);
		
		array = makeArray(2);
		Object[] objectArray = array.toArray();
		assertEquals(2, objectArray.length);
		assertEquals(0, objectArray[0]);
		assertEquals(1, objectArray[1]);
	}
	
	
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection array = makeArray(3);
		array.clear();
		assertEquals(0, array.size());
		}
	
	@Test
	public void getTestEmptyArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->array.get(0));
	}
	
	@Test
	public void getTestIndexOutOfBounds() {
		ArrayIndexedCollection array = makeArray(10);
		assertThrows(IndexOutOfBoundsException.class, ()->array.get(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->array.get(10));
	}
	
	@Test
	public void getTest() {
		ArrayIndexedCollection array = makeArray(10);
		assertEquals(0, array.get(0));
		assertEquals(7, array.get(7));
	}
	
	@Test
	public void insertTestNull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, ()->array.insert(null, 0));
	}
	
	@Test
	public void insertTestIndexOutOfBounds() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->array.insert(-1, -1));
		assertThrows(IndexOutOfBoundsException.class, ()->array.insert(11, 11));
	}
	
	@Test
	public void insertTest(){
		ArrayIndexedCollection array = new ArrayIndexedCollection();

		array.insert(9, 0);
		array.insert(true, 0);
		array.insert(3.14, array.size());
		
		assertEquals(3, array.size());
		assertEquals(true, array.get(0));
		assertEquals(9, array.get(1));
		assertEquals(3.14, array.get(2));
	}
	
	@Test
	public void indexOfTest() {
		ArrayIndexedCollection array = makeArray(6);
		assertEquals(-1, array.indexOf(null));
		assertEquals(-1, array.indexOf(7));
		assertEquals(2, array.indexOf(2));
	}
	
	@Test
	public void removeIndexTestIndexOutOfBounds() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->array.remove(0));
		
		ArrayIndexedCollection array2 = makeArray(5);
		assertThrows(IndexOutOfBoundsException.class, ()->array2.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->array2.remove(5));
	}
	
	@Test
	public void removeIndexTest() {
		ArrayIndexedCollection array = makeArray(5);

		array.remove(0);
		array.remove(2);
		assertEquals(3, array.size());
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(4, array.get(2));
	}
	
	/**
	 *	Makes a new {@link ArrayIndexedCollection} and adds values from
	 *	0 to given value so that the index of the value = the value.
	 *	@param value The number of elements.
	 *	@return The constructed {@link ArrayIndexedCollection}.
	 */
	private ArrayIndexedCollection makeArray(int value) {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for(int i = 0; i < value; i++) {
			array.add(i);
		}
		return array;
	}
}
