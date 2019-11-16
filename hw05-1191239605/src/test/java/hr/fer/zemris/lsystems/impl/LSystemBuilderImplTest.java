package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LSystemBuilderImplTest {

	@Test
	public void testGenerate1() {
		LSystemBuilderImpl l = new LSystemBuilderImpl();
		var i = l.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		assertEquals("F",i.generate(0));
		assertEquals("F+F--F+F",i.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F",i.generate(2));
	}


	@Test
	public void testGenerate2() {
		LSystemBuilderImpl l = new LSystemBuilderImpl();
		var i = l.setAxiom("A").registerProduction('A', "B").registerProduction('B', "AB").build();
		assertEquals("A",i.generate(0));
		assertEquals("B",i.generate(1));
		assertEquals("AB",i.generate(2));
		assertEquals("BAB",i.generate(3));
		assertEquals("ABBAB",i.generate(4));
		assertEquals("BABABBAB",i.generate(5));		
	}
	
	
}
