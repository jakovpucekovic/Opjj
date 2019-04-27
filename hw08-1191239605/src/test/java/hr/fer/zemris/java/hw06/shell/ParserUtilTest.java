package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.commands.ParserUtil;

public class ParserUtilTest {

	@Test
	public void testParseBasic() {
		String[] actual = ParserUtil.parse("\\home\\test");
		assertEquals("\\home\\test", actual[0]);
		assertEquals(null, actual[1]);		
	}
	
	@Test
	public void testParseQuotes() {
		String[] actual = ParserUtil.parse("\"\\home\\test\"");
		assertEquals("\\home\\test", actual[0]);
		assertEquals(null, actual[1]);
	}
	
	@Test
	public void test2ArgumentsNoQuotes() {
		String[] actual = ParserUtil.parse("\\home\\test .");
		assertEquals("\\home\\test", actual[0]);
		assertEquals(".", actual[1]);	
	}
	
	@Test
	public void test2ArgumentsQuotes() {
		String[] actual = ParserUtil.parse("\"\\home\\test\" .");
		assertEquals("\\home\\test", actual[0]);
		assertEquals(".", actual[1]);
		
		String[] actual2 = ParserUtil.parse("\\home\\test \".\"");
		assertEquals("\\home\\test", actual2[0]);
		assertEquals(".", actual2[1]);
		
		String[] actual3 = ParserUtil.parse("\"\\home\\test\" \".\"");
		assertEquals("\\home\\test", actual3[0]);
		assertEquals(".", actual3[1]);
	}
	
	@Test
	public void testMultipleSpaces() {
		String[] actual2 = ParserUtil.parse("\\home\\test  .");
		assertEquals("\\home\\test", actual2[0]);
		assertEquals(".", actual2[1]);
	}
	
	@Test
	public void testSpacesInQuotes() {
		String[] actual = ParserUtil.parse("\"\\h o m e\\test\"");
		assertEquals("\\h o m e\\test", actual[0]);
		assertEquals(null, actual[1]);
		
		String[] actual2 = ParserUtil.parse("\"\\ho me\\test\" \".\"");
		assertEquals("\\ho me\\test", actual2[0]);
		assertEquals(".", actual2[1]);
		
		String[] actual3 = ParserUtil.parse("\"\\ho me\\test\" .");
		assertEquals("\\ho me\\test", actual3[0]);
		assertEquals(".", actual3[1]);
	}
	
	@Test
	public void testEscapeSequences() {
		String[] actual = ParserUtil.parse("\"Pe\\\\ro\"");
		assertEquals("Pe\\ro", actual[0]);
		assertEquals(null, actual[1]);
		
		String[] actual2 = ParserUtil.parse("\"Pe\\\"ro\"");
		assertEquals("Pe\"ro", actual2[0]);
		assertEquals(null, actual2[1]);		
	}	
	
	@Test
	public void testExceptions() {
		assertThrows(IllegalArgumentException.class, () -> ParserUtil.parse("\"quotesBefore"));
		assertThrows(IllegalArgumentException.class, () -> ParserUtil.parse("quotesAfter\""));

		assertThrows(IllegalArgumentException.class, () -> ParserUtil.parse("more than oneSpace"));
	}

	@Test
	public void testParseEmpty() {
		String[] actual = ParserUtil.parse("");
		assertEquals("", actual[0]);
		assertEquals(null, actual[1]);

		String[] actual2 = ParserUtil.parse("\"\"");
		assertEquals("", actual2[0]);
		assertEquals(null, actual2[1]);
	}
	
}
