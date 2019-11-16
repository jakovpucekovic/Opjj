package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTest {
	
	@Test
	public void testGetX() {
		Vector2D vec = new Vector2D(0, 3.14);
		
		assertEquals(0, vec.getX());
	}
	
	@Test
	public void testGetY() {
		Vector2D vec = new Vector2D(0, 3.14);
		
		assertEquals(0, vec.getX());
		assertEquals(3.14, vec.getY());
	}
	
	@Test
	public void testTranslate() {
		Vector2D vec = new Vector2D(-1, 3.14);
		Vector2D newVec = new Vector2D(0, 6.14);
		vec.translate(new Vector2D(1, 3));
		
		assertEquals(newVec, vec);
	}
	
	@Test
	public void testTranslated() {
		Vector2D vec = new Vector2D(-1, 3.14);
		Vector2D newVec = new Vector2D(0, 6.14);

		assertEquals(newVec, vec.translated(new Vector2D(1, 3)));
		
	}
	
	@Test
	public void testRotate() {
		Vector2D vec = new Vector2D(1, 0);
		Vector2D newVec = new Vector2D(0, 1);
		vec.rotate(Math.PI/2);
		
		assertEquals(newVec, vec);
	}
	
	@Test
	public void testRotated() {
		Vector2D vec = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2);
		Vector2D newVec = new Vector2D(-Math.sqrt(2)/2, -Math.sqrt(2)/2);
		
		assertEquals(newVec, vec.rotated(Math.PI));
	}
	
	@Test
	public void testScale() {
		Vector2D vec = new Vector2D(1.d/3, 2.d/3);
		Vector2D newVec = new Vector2D(1, 2);
		vec.scale(3);
		
		assertEquals(newVec, vec);
	}

	@Test
	public void testScaled() {
		Vector2D vec = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/4);
		Vector2D newVec = new Vector2D(2 * Math.sqrt(2), Math.sqrt(2));
		
		assertEquals(newVec, vec.scaled(4));
	}
	
	@Test
	public void testCopy() {
		Vector2D vec = new Vector2D(Math.E, -7);
		
		assertEquals(vec, vec.copy());
		
	}
}