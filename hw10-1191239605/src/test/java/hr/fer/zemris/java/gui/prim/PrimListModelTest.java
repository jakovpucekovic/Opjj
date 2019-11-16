package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PrimListModelTest {

	@Test
	public void testSize() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
		
		model.next();
		model.next();
		assertEquals(3, model.getSize());
	}
	
	@Test
	public void testNext() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getElementAt(0));
		
		model.next();
		assertEquals(2, model.getElementAt(1));	
	}
	
	@Test
	public void testGetElement() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getElementAt(0));
	
		assertThrows(IndexOutOfBoundsException.class, ()->model.getElementAt(5));
		
		model.next();
		model.next();
		model.next();
		model.next();
		assertEquals(7, model.getElementAt(4));
	}
}
