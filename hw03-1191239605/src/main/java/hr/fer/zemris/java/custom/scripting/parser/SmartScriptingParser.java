package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptingParser {

	private ObjectStack nodeStack;
	
	public SmartScriptingParser(String documentBody) {
		
		nodeStack = new ObjectStack();
		nodeStack.push(new DocumentNode());
		
		parse(documentBody);
	}

	private void parse(String str) {
		SmartScriptingLexer lexer = new SmartScriptingLexer(str);
		
		SmartScriptingToken token = lexer.nextToken();
		while(token.getType()!= SmartScriptingTokenType.EOF) {
			if(token.getType() == SmartScriptingTokenType.MODE_SWITCHER) {
				changeState(lexer);
			} else if(token.getType() == SmartScriptingTokenType.TEXT) {
				
			}
		}
		
		
	}
	
	
	private void changeState(SmartScriptingLexer lexer) {
		lexer.setState(lexer.getState() == SmartScriptingLexerState.ALL_TEXT ? SmartScriptingLexerState.IN_TAG : SmartScriptingLexerState.ALL_TEXT);
	}
}
