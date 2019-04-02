package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTest {

	@Test
	public void testConstructor() {
		Vector2D vec = new Vector2D(0, 3.14);
		
		assertEquals(0, vec.getX());
		assertEquals(3.14, vec.getY());
	}
	
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
		vec.translate(new Vector2D(1, 3));
		
		assertEquals(0, vec.getX());
		assertEquals(6.14, vec.getY());
	}
	
	@Test
	public void testTranslated() {
		Vector2D vec = new Vector2D(-1, 3.14);
		Vector2D newVec = vec.translated(new Vector2D(1, 3));
		
		assertEquals(-1, vec.getX());
		assertEquals(3.14, vec.getY());
		assertEquals(0, newVec.getX());
		assertEquals(6.14, newVec.getY());
	}
	
	@Test
	public void testRotate() {
		Vector2D vec = new Vector2D(1, 0);
		vec.rotate(Math.PI/2);
		
		assertEquals(0, vec.getX());
		assertEquals(1, vec.getY());
	}
	
	@Test
	public void testRotated() {
		Vector2D vec = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2);
		Vector2D newVec = vec.rotated(Math.PI);
		
		assertEquals(Math.sqrt(2)/2, vec.getX());
		assertEquals(Math.sqrt(2)/2, vec.getY());
		assertEquals(-Math.sqrt(2)/2, newVec.getX());
		assertEquals(-Math.sqrt(2)/2, newVec.getY());

	}
	
	@Test
	public void testScale() {
		Vector2D vec = new Vector2D(1.d/3, 2.d/3);
		vec.scale(3);
		
		assertEquals(1, vec.getX());
		assertEquals(2, vec.getY());
	}

	@Test
	public void testScaled() {
		Vector2D vec = new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/4);
		Vector2D newVec = vec.scaled(4);
		
		assertEquals(Math.sqrt(2)/2, vec.getX());
		assertEquals(Math.sqrt(2)/4, vec.getY());
		assertEquals(Math.sqrt(2)*2, newVec.getX());
		assertEquals(Math.sqrt(2), newVec.getY());
	}
	
	@Test
	public void testCopy() {
		Vector2D vec = new Vector2D(Math.E, -7);
		Vector2D newVec = vec.copy();
		
		assertEquals(vec.getX(), newVec.getX());
		assertEquals(vec.getY(), newVec.getY());
		
	}
}