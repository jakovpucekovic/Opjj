package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void constructorTest() {
		LinkedListIndexedCollection linkedList = new LinkedListIndexedCollection();
	
		linkedList = new LinkedListIndexedCollection(makeCollection());
		
	}
	
	private Collection makeCollection() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(0);
		col.add(1);
		col.add(2);		
		return col;
	}
	
}
