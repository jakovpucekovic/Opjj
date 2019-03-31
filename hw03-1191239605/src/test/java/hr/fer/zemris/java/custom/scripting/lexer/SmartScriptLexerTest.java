package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.LexerException;
import hr.fer.zemris.java.hw03.prob1.LexerState;
import hr.fer.zemris.java.hw03.prob1.Token;
import hr.fer.zemris.java.hw03.prob1.TokenType;

public class SmartScriptLexerTest {


//	@Disabled
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken(), "SmartScriptToken was expected but null was returned.");
	}

//	@Disabled
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

//	@Disabled
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

//	@Disabled
	@Test
	public void testGetReturnsLastNext() {
		// Calling getSmartScriptToken once or several times after calling nextSmartScriptToken must return each time what nextSmartScriptToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getSmartScriptToken returned different token than nextSmartScriptToken.");
		assertEquals(token, lexer.getToken(), "getSmartScriptToken returned different token than nextSmartScriptToken.");
	}

//	@Disabled
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
//	@Disabled
	@Test
	public void testOnlySpaces() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "   \r\n\t    "),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};	
		
		checkSmartScriptTokenStream(lexer, correctData);

	}

//	@Disabled
	@Test
	public void testTwoWords() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkSmartScriptTokenStream(lexer, correctData);
	}

//	@Disabled
	@Test
	public void testEscapeSequences() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now \\\\actually write one {$=1$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now \\actually write one "),
			new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "=1$}"),			
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkSmartScriptTokenStream(lexer, correctData);
	}

//	@Disabled
	@Test
	public void testInvalidEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\"); 

		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

//	@Disabled
	@Test
	public void testWordWithManyEscapes() {
		SmartScriptLexer lexer = new SmartScriptLexer("  ab\\\\\\{$cd$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "  ab\\{$cd$}"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkSmartScriptTokenStream(lexer, correctData);
	}


	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkSmartScriptTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for(SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
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
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}
	
//	@Disabled
	@Test
	public void testNotNullInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

//	@Disabled
	@Test
	public void testEmptyInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

//	@Disabled
	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

//	@Disabled
	@Test
	public void testRadAfterEOFInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
//	@Disabled
	@Test
	public void testNoActualContentInExtended() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
//	@Disabled
	@Test
	public void testFORInput1() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1 10 1 $}");
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$"));
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, -1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		
		lexer.setState(SmartScriptLexerState.IN_TAG);
	}
	
//	@Disabled
	@Test
	public void testFORInput2() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$    FOR\n   sco_re\n	\"- 1\" 10 \"1\" $}");
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$"));
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FOR, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "sco_re"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "- 1"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "1"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		
		lexer.setState(SmartScriptLexerState.IN_TAG);
	}
	
	
	private void checkToken(SmartScriptToken actual, SmartScriptToken expected) {
			String msg = "Token are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}
	
	
}
	
	