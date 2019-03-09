package hr.fer.zemris.java.proba;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProbaTest{

	@Test
	public void PrazanInput(){
		Proba.main("");
	}

	@Test
	public void Brojevi(){
		Proba.main("1 2 34 79 83");
	}

}