package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QueryParserTest {

	@Test
	public void test() {
		QueryParser p = new QueryParser();
		var l = p.getLexer("query jmbag=\"1\"");
		
		while(l.hasNext()) {
			System.out.println(l.nextToken());
		}
	}
	
	
	
}
