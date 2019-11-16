package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.commands.ParserUtil;

public class ParserUtilTest {

	@Test
	public void testParse() {
		
		assertEquals("", ParserUtil.parse(""));
		assertEquals("Pero", ParserUtil.parse("Pero"));
		assertEquals("Pero", ParserUtil.parse("\"Pero\""));
		assertEquals("C:/Program Files/Program1/info.txt", ParserUtil.parse("\"C:/Program Files/Program1/info.txt\""));
		
		assertThrows(IllegalArgumentException.class, () -> ParserUtil.parse("\"C:\\fi le\".txt"));
	}
	
	
	
}
