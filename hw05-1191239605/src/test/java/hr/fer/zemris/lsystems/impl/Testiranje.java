package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Testiranje {

	@Test
	public void test1() {
		LSystemBuilderImpl l = new LSystemBuilderImpl();
		var i = l.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		assertEquals("F",i.generate(0));
		assertEquals("F+F--F+F",i.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F",i.generate(2));
	}

	
}
