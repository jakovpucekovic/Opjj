package hr.fer.zemris.java.primjeri;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class SumaRedaTest{

	@Test
	void prazanInput(){
		double z=Primjeri.SumaReda("");
		assertEquals(-1, z);
	}

	@Test
	void kriviInput(){
		double z=Primjeri.SumaReda("perolero");
		assertEquals(-1, z);
	}

	@Test
	void trivijalanInput(){
		double z=Primjeri.SumaReda("0");
		assertEquals(1, z);
	}

	@Test 
	void legitInput(){
		double z=Primjeri.SumaReda("3.14");
		assertEquals(23.06834908994199, z);
	}
}