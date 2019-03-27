package hr.fer.zemris.java.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptingParser;

public class SmartScriptingParserTest {

	@Test
	public void test() {
		String s = new String("This is sample text.\n {$ FOR i 1 10 1 $}\n This is {$= i $}-th time this message is generated.\n{$END$}\n{$FOR i 0 10 2 $}\n sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n{$END$}");
//		String s = new String("{$FOR i 0 10 2 $}\n sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n{$END$}");
		
		SmartScriptingParser parser = new SmartScriptingParser(s);
		
		StringBuilder ss = new StringBuilder();
		ss.append("###").append("\"").append("###");
		
		System.out.print(ss.toString());
	}
	
}
