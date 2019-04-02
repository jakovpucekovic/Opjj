package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken());
	}

	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(token, lexer.getToken());
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}
	
	@Test
	public void testNotNullInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		assertNotNull(lexer.nextToken());
	}

	@Test
	public void testEmptyInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNextInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(token, lexer.getToken());
	}

	@Test
	public void testRadAfterEOFInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testTextOnlyWhitespace() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}
	
	
	@Test
	public void testText() {
		SmartScriptLexer lexer = new SmartScriptLexer(" + Štefanija\r\n\t Automobil @i  ");
	
				SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, " + Štefanija\r\n\t Automobil @i  "),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
	
		checkSmartScriptTokenStream(lexer, correctData);
	}

	@Test
	public void testTextOnlySpaces() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "   \r\n\t    "),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};	
		
		checkSmartScriptTokenStream(lexer, correctData);

	}

	@Test
	public void testTextEscapeSequences() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now \\\\actually write one {$=1$}");

				SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now \\actually write one "),
			new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "=1$}"),			
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkSmartScriptTokenStream(lexer, correctData);
	}

	@Test
	public void testTextInvalidEscape() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("\\"); 
		SmartScriptLexer lexer2 = new SmartScriptLexer("\\}"); 
		SmartScriptLexer lexer3 = new SmartScriptLexer(" a\\a "); 

		assertThrows(SmartScriptLexerException.class, () -> lexer1.nextToken());
		assertThrows(SmartScriptLexerException.class, () -> lexer2.nextToken());
		assertThrows(SmartScriptLexerException.class, () -> lexer3.nextToken());
	}

	@Test
	public void testTextManyEscapes() {
		SmartScriptLexer lexer = new SmartScriptLexer("  ab\\\\\\{$cd$}");

				SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "  ab\\{$cd$}"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkSmartScriptTokenStream(lexer, correctData);
	}


	@Test
	public void testFORInput1() {
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
	}
	
	@Test
	public void testFORInput2() {
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

	}
	
	@Test
	public void testFORInput3() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$for year 1 last_year $}");
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$"));
		lexer.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FOR, "for"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "year"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "last_year"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	@Test
	public void testFunction() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("@pero");
		SmartScriptLexer lexer2 = new SmartScriptLexer("@p9_74a");
		SmartScriptLexer lexer3 = new SmartScriptLexer("@9");
		SmartScriptLexer lexer4 = new SmartScriptLexer("@_pero");
		SmartScriptLexer lexer5 = new SmartScriptLexer("@@pero");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		lexer3.setState(SmartScriptLexerState.IN_TAG);
		lexer4.setState(SmartScriptLexerState.IN_TAG);
		lexer5.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.FUNCTION, "pero"));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.FUNCTION, "p9_74a"));
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		assertThrows(SmartScriptLexerException.class, ()-> lexer3.nextToken());
		assertThrows(SmartScriptLexerException.class, ()-> lexer4.nextToken());
		assertThrows(SmartScriptLexerException.class, ()-> lexer5.nextToken());
	}
	
	@Test
	public void testInteger() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("0");
		SmartScriptLexer lexer2 = new SmartScriptLexer("-3");
		SmartScriptLexer lexer3 = new SmartScriptLexer("3147483647"); //over int max value
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		lexer3.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 0));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, -3));
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	
		assertThrows(SmartScriptLexerException.class, ()-> lexer3.nextToken());
	}
	
	@Test
	public void testDouble() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("1.2");
		SmartScriptLexer lexer2 = new SmartScriptLexer("-1.0");
		SmartScriptLexer lexer3 = new SmartScriptLexer("0.99999999");
		SmartScriptLexer lexer4 = new SmartScriptLexer("1,2");
		SmartScriptLexer lexer5 = new SmartScriptLexer("1.1.1");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		lexer3.setState(SmartScriptLexerState.IN_TAG);
		lexer4.setState(SmartScriptLexerState.IN_TAG);
		lexer5.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, 1.2));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, -1.0));
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer3.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, 0.99999999));
		checkToken(lexer3.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer4.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		assertThrows(SmartScriptLexerException.class, ()-> lexer4.nextToken());
		
		checkToken(lexer5.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, 1.1));
		assertThrows(SmartScriptLexerException.class, ()-> lexer5.nextToken());
	}
	
	
	@Test
	public void testOperator() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("+");
		SmartScriptLexer lexer2 = new SmartScriptLexer("%");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.OPERATOR, '+'));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		assertThrows(SmartScriptLexerException.class, ()-> lexer2.nextToken());
	}
	
	
	@Test
	public void testString() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("\"string\"");
		SmartScriptLexer lexer2 = new SmartScriptLexer("string");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "string"));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "string"));
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));	
	}

	@Test
	public void testEnd() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("END");
		SmartScriptLexer lexer2 = new SmartScriptLexer("end");
		SmartScriptLexer lexer3 = new SmartScriptLexer("eNd");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		lexer3.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.END, "END"));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.END, "end"));
		checkToken(lexer2.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		checkToken(lexer3.nextToken(), new SmartScriptToken(SmartScriptTokenType.END, "eNd"));
		checkToken(lexer3.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	
	@Test
	public void testEquals() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("=");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EQUALS, "="));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	

	@Test
	public void testEndTag() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("$}");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	@Test
	public void testStartTag() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("{$");
		SmartScriptLexer lexer2 = new SmartScriptLexer("\"\\{$\"");
		
		lexer1.setState(SmartScriptLexerState.IN_TAG);
		lexer2.setState(SmartScriptLexerState.IN_TAG);
		
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$"));
		checkToken(lexer1.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		
		assertThrows(SmartScriptLexerException.class, ()->lexer2.nextToken());
	}
	
	
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

	private void checkToken(SmartScriptToken actual, SmartScriptToken expected) {
			String msg = "Token are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}
}
	
	