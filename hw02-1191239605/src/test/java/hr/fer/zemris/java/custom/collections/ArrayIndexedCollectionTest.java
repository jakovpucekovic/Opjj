package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//napisi testove za duplikate


public class ArrayIndexedCollectionTest {

	@Test
	public void ConstructorTest() {
		//array with size 0
		assertThrows(IllegalArgumentException.class, ()-> new ArrayIndexedCollection(0));
				
		//array from null collection
		assertThrows(NullPointerException.class, ()->new ArrayIndexedCollection(null));
		assertThrows(NullPointerException.class, ()->new ArrayIndexedCollection(null, 7));
		
		//default constructor
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
		assertEquals("ArrayIndexedCollection [size=0, elements=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]]", array.toString());
		
		//constructor with initialCapacity
		array = new ArrayIndexedCollection(1);
		assertEquals(0, array.size());
		assertEquals("ArrayIndexedCollection [size=0, elements=[null]]", array.toString());
		
		//constructor with collection size larger than default capacity
		array = new ArrayIndexedCollection(makeArray(20));
		assertEquals(20, array.size());
		assertEquals("ArrayIndexedCollection [size=20, elements=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]]", array.toString());
		
		//constructor with collection size larger than initalCapacity
		array = new ArrayIndexedCollection(array, 19);
		assertEquals(20, array.size());
		assertEquals("ArrayIndexedCollection [size=20, elements=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]]", array.toString());	
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
	public void sizeTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
		array.add(7);
		assertEquals(1, array.size());
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
		assertTrue(array.isEmpty());
		assertEquals("ArrayIndexedCollection [size=0, elements=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]]", array.toString());
		}
	
	@Test
	public void addTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(7);
		assertThrows(NullPointerException.class, ()->array.add(null));
	}
	
	@Test
	public void getTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->array.get(0));
		
		ArrayIndexedCollection array2 = makeArray(20);
		assertEquals(0, array2.get(0));
		assertEquals(13, array2.get(13));
		assertThrows(IndexOutOfBoundsException.class, ()->array2.get(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->array2.get(20));
	}
	
	@Test
	public void insertTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.insert(9, 0);
		assertThrows(NullPointerException.class, ()->array.insert(null, 0));
		assertThrows(IndexOutOfBoundsException.class, ()->array.insert(-1, -1));
		assertThrows(IndexOutOfBoundsException.class, ()->array.insert(11, 11));
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
	public void removeArrayTest() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()->array.remove(0));
		
//		array = makeArray(5);
//		assertThrows(IndexOutOfBoundsException.class, ()->array.remove(-1));
		
		ArrayIndexedCollection array2 = makeArray(5);
		assertThrows(IndexOutOfBoundsException.class, ()->array2.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, ()->array2.remove(5));
		array2.remove(0);
		array2.remove(2);
		assertEquals(3, array2.size());
		assertEquals(1, array2.get(0));
		assertEquals(2, array2.get(1));
		assertEquals(4, array2.get(2));
	}
	
	//makes an array and adds values from 0 to given value
	private ArrayIndexedCollection makeArray(int value) {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for(int i = 0; i < value; i++) {
			array.add(i);
		}
		return array;
	}
}
