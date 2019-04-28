package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.commands.ParserUtil;

public class ParserUtilTest {

	@Test
	public void testParseBasic() {
		List<String> actual = ParserUtil.parse("\\home\\test");
		assertEquals("\\home\\test", actual.get(0));
		assertEquals(1, actual.size());
	}
	
	@Test
	public void testParseQuotes() {
		List<String> actual = ParserUtil.parse("\"\\home\\test\"");
		assertEquals("\\home\\test", actual.get(0));
		assertEquals(1, actual.size());
	}
	
	@Test
	public void test2ArgumentsNoQuotes() {
		List<String> actual = ParserUtil.parse("\\home\\test .");
		assertEquals("\\home\\test", actual.get(0));
		assertEquals(".", actual.get(1));
		assertEquals(2, actual.size());
	}
	
	@Test
	public void test2ArgumentsQuotes() {
		List<String> actual = ParserUtil.parse("\"\\home\\test\" .");
		assertEquals("\\home\\test", actual.get(0));
		assertEquals(".", actual.get(1));
		assertEquals(2, actual.size());
		
		List<String> actual2 = ParserUtil.parse("\\home\\test \".\"");
		assertEquals("\\home\\test", actual2.get(0));
		assertEquals(".", actual2.get(1));
		assertEquals(2, actual2.size());

		List<String> actual3 = ParserUtil.parse("\"\\home\\test\" \".\"");
		assertEquals("\\home\\test", actual3.get(0));
		assertEquals(".", actual3.get(1));
		assertEquals(2, actual3.size());
	}
	
	@Test
	public void testMultipleSpaces() {
		List<String> actual = ParserUtil.parse("\\home\\test  .");
		assertEquals("\\home\\test", actual.get(0));
		assertEquals(".", actual.get(1));
		assertEquals(2, actual.size());
	}
	
	@Test
	public void testSpacesInQuotes() {
		List<String> actual = ParserUtil.parse("\"\\h o m e\\test\"");
		assertEquals("\\h o m e\\test", actual.get(0));
		assertEquals(1, actual.size());
		
		List<String> actual2 = ParserUtil.parse("\"\\ho me\\test\" \".\"");
		assertEquals("\\ho me\\test", actual2.get(0));
		assertEquals(".", actual2.get(1));
		assertEquals(2, actual2.size());
		
		List<String> actual3 = ParserUtil.parse("\"\\ho me\\test\" .");
		assertEquals("\\ho me\\test", actual3.get(0));
		assertEquals(".", actual3.get(1));
		assertEquals(2, actual3.size());
	}
	
	@Test
	public void testEscapeSequences() {
		List<String> actual = ParserUtil.parse("\"Pe\\\\ro\"");
		assertEquals("Pe\\ro", actual.get(0));
		assertEquals(1, actual.size());
		
		List<String> actual2 = ParserUtil.parse("\"Pe\\\"ro\"");
		assertEquals("Pe\"ro", actual2.get(0));
		assertEquals(1, actual2.size());		
	}	
	
	@Test
	public void testExceptions() {
		assertThrows(IllegalArgumentException.class, () -> ParserUtil.parse("\"quotesBefore"));
		assertThrows(IllegalArgumentException.class, () -> ParserUtil.parse("quotesAfter\""));
	}

	@Test
	public void testParseEmpty() {
		List<String> actual = ParserUtil.parse("");
		assertEquals("", actual.get(0));
		assertEquals(1, actual.size());

		List<String> actual2 = ParserUtil.parse("\"\"");
		assertEquals("", actual2.get(0));
		assertEquals(1, actual2.size());
	}
	
}
