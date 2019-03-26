package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SmartScriptingLexerTest {


//	@Disabled
	@Test
	public void testNotNull() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		
		assertNotNull(lexer.nextToken(), "SmartScriptingToken was expected but null was returned.");
	}

//	@Disabled
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptingLexer(null));
	}

//	@Disabled
	@Test
	public void testEmpty() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		
		assertEquals(SmartScriptingTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

//	@Disabled
	@Test
	public void testGetReturnsLastNext() {
		// Calling getSmartScriptingToken once or several times after calling nextSmartScriptingToken must return each time what nextSmartScriptingToken returned...
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		
		SmartScriptingToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getSmartScriptingToken returned different token than nextSmartScriptingToken.");
		assertEquals(token, lexer.getToken(), "getSmartScriptingToken returned different token than nextSmartScriptingToken.");
	}

//	@Disabled
	@Test
	public void testRadAfterEOF() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptingLexerException.class, () -> lexer.nextToken());
	}
	
//	@Disabled
	@Test
	public void testOnlySpaces() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptingLexer lexer = new SmartScriptingLexer("   \r\n\t    ");
		
		// We expect the following stream of tokens
		SmartScriptingToken correctData[] = {
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "   \r\n\t    "),
			new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
		};	
		
		checkSmartScriptingTokenStream(lexer, correctData);

	}

//	@Disabled
	@Test
	public void testTwoWords() {
		// Lets check for several words...
		SmartScriptingLexer lexer = new SmartScriptingLexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		SmartScriptingToken correctData[] = {
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
			new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
		};

		checkSmartScriptingTokenStream(lexer, correctData);
	}

//	@Disabled
	@Test
	public void testEscapeSequences() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("Example \\{$=1$}. Now \\\\actually write one {$=1$}");

		// We expect the following stream of tokens
		SmartScriptingToken correctData[] = {
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "Example {$=1$}. Now \\actually write one "),
			new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "{$"),
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "=1$}"),			
			new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
		};

		checkSmartScriptingTokenStream(lexer, correctData);
	}

//	@Disabled
	@Test
	public void testInvalidEscape() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("\\");  // this is three spaces and a single backslash -- 4 letters string

		// will throw!
		assertThrows(SmartScriptingLexerException.class, () -> lexer.nextToken());
	}

//	@Disabled
	@Test
	public void testWordWithManyEscapes() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("  ab\\\\\\{cd");

		// We expect the following stream of tokens
		SmartScriptingToken correctData[] = {
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "  ab\\{cd"),
			new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
		};

		checkSmartScriptingTokenStream(lexer, correctData);
	}


	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkSmartScriptingTokenStream(SmartScriptingLexer lexer, SmartScriptingToken[] correctData) {
		int counter = 0;
		for(SmartScriptingToken expected : correctData) {
			SmartScriptingToken actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------- Second part of tests----------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------

	
	@Test
	public void testFor() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR i 1 10 1 $}");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		
		SmartScriptingToken correctData[] = {
				new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "{$"),
				new SmartScriptingToken(SmartScriptingTokenType.WORD, "FOR"),
				new SmartScriptingToken(SmartScriptingTokenType.WORD, "i"),
				new SmartScriptingToken(SmartScriptingTokenType.NUMBER, 1),
				new SmartScriptingToken(SmartScriptingTokenType.NUMBER, 10),
				new SmartScriptingToken(SmartScriptingTokenType.NUMBER, 1),
				new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "$}"),
				new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
			};

			checkSmartScriptingTokenStream(lexer, correctData);
	}
	
	@Test
	public void testJednako() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$= i $}");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		
		SmartScriptingToken correctData[] = {
				new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "{$"),
				new SmartScriptingToken(SmartScriptingTokenType.SYMBOL, '='),
				new SmartScriptingToken(SmartScriptingTokenType.WORD, "i"),
				new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "$}"),
				new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
			};

			checkSmartScriptingTokenStream(lexer, correctData);
	}
	
	
}
	
	