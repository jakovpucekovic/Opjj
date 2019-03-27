package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.LexerException;
import hr.fer.zemris.java.hw03.prob1.LexerState;
import hr.fer.zemris.java.hw03.prob1.Token;
import hr.fer.zemris.java.hw03.prob1.TokenType;

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
			new SmartScriptingToken(SmartScriptingTokenType.START_TAG, "{$"),
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "=1$}"),			
			new SmartScriptingToken(SmartScriptingTokenType.EOF, null)
		};

		checkSmartScriptingTokenStream(lexer, correctData);
	}

//	@Disabled
	@Test
	public void testInvalidEscape() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("\\"); 

		// will throw!
		assertThrows(SmartScriptingLexerException.class, () -> lexer.nextToken());
	}

//	@Disabled
	@Test
	public void testWordWithManyEscapes() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("  ab\\\\\\{$cd$}");

		// We expect the following stream of tokens
		SmartScriptingToken correctData[] = {
			new SmartScriptingToken(SmartScriptingTokenType.TEXT, "  ab\\{$cd$}"),
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

//	@Disabled
	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptingLexer("").setState(null));
	}
	
//	@Disabled
	@Test
	public void testNotNullInExtended() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

//	@Disabled
	@Test
	public void testEmptyInExtended() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		assertEquals(SmartScriptingTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

//	@Disabled
	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		SmartScriptingToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

//	@Disabled
	@Test
	public void testRadAfterEOFInExtended() {
		SmartScriptingLexer lexer = new SmartScriptingLexer("");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptingLexerException.class, () -> lexer.nextToken());
	}
	
//	@Disabled
	@Test
	public void testNoActualContentInExtended() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptingLexer lexer = new SmartScriptingLexer("   \r\n\t    ");
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		assertEquals(SmartScriptingTokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
//	@Disabled
	@Test
	public void testFORInput1() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$ FOR i -1 10 1 $}");
		
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.START_TAG, "{$"));
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.FOR, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.INTEGER, -1));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.END_TAG, "$}"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.EOF, null));
		
		
		lexer.setState(SmartScriptingLexerState.IN_TAG);
	}
	
//	@Disabled
	@Test
	public void testFORInput2() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptingLexer lexer = new SmartScriptingLexer("{$    FOR\n   sco_re\n	\"- 1\" 10 \"1\" $}");
		
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.START_TAG, "{$"));
		lexer.setState(SmartScriptingLexerState.IN_TAG);
		
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.FOR, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.VARIABLE, "sco_re"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.STRING, "- 1"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.STRING, "1"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.END_TAG, "$}"));
		checkToken(lexer.nextToken(), new SmartScriptingToken(SmartScriptingTokenType.EOF, null));
		
		
		lexer.setState(SmartScriptingLexerState.IN_TAG);
	}
	
	
	private void checkToken(SmartScriptingToken actual, SmartScriptingToken expected) {
			String msg = "Token are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}
	
	
}
	
	