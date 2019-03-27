package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVarible;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptingParser {

	private ObjectStack nodeStack;
	
	public SmartScriptingParser(String documentBody) {
		
		nodeStack = new ObjectStack();
		nodeStack.push(new DocumentNode());
		
		parse(documentBody);
	}

	public DocumentNode getDocumentNode() {
		return (DocumentNode) nodeStack.peek();
	}
	
	public void parse(String str) {
		SmartScriptingLexer lexer = new SmartScriptingLexer(str);
		
		SmartScriptingToken token = lexer.nextToken();
		while(token.getType()!= SmartScriptingTokenType.EOF) {
			if(token.getType() == SmartScriptingTokenType.START_TAG) {
				lexer.setState(SmartScriptingLexerState.IN_TAG);
			} else if(token.getType() == SmartScriptingTokenType.END_TAG) {
				lexer.setState(SmartScriptingLexerState.TEXT);
			} else if(token.getType() == SmartScriptingTokenType.FOR) {
				ForLoopNode forLoopNode = parseFor(lexer);
				((Node)nodeStack.peek()).addChildNode(forLoopNode);
				nodeStack.push(forLoopNode);
			} else if(token.getType() == SmartScriptingTokenType.EQUALS) {
				EchoNode echoNode = parseEcho(lexer);
				((Node)nodeStack.peek()).addChildNode(echoNode);
			} else if(token.getType() == SmartScriptingTokenType.END) {
				try{
					nodeStack.pop();
				} catch(EmptyStackException ex) {
					throw new SmartScriptingParserException("End tag doesn't close anything.");
				}
			} else if(token.getType() == SmartScriptingTokenType.TEXT) {
				TextNode textNode = new TextNode((String) token.getValue());
				((Node)nodeStack.peek()).addChildNode(textNode);
			} else {
				throw new SmartScriptingParserException("error");
			}
			
		}
	}
	
	/**
	 *	Parses the FOR tag and adds it to its parent on the stack and pushes it 
	 *	onto the stack. A valid FOR tag has 3 or 4 elements and ends in $}.
	 */
	private ForLoopNode parseFor(SmartScriptingLexer lexer) {		
		
		if(lexer.getToken().getType() != SmartScriptingTokenType.VARIABLE) {
			throw new SmartScriptingParserException("Invalid for loop");
		}
		
		ElementVarible var = new ElementVarible((String) lexer.getToken().getValue());
		lexer.nextToken();
	
		Element start, end, step = null;
		
		try {
			start = parseStartEndStepExpression(lexer);
			lexer.nextToken();
			end = parseStartEndStepExpression(lexer);
		} catch(SmartScriptingParserException ex) {
			throw new SmartScriptingParserException("Invalid for loop");
		}
		
		lexer.nextToken();
		try {
			step = parseStartEndStepExpression(lexer);
		} catch(SmartScriptingParserException ex) {
		}
		
		return new ForLoopNode(var, start, end, step);
		
	}
	
	
	private Element parseStartEndStepExpression(SmartScriptingLexer lexer) {
		switch(lexer.getToken().getType()) {
			case VARIABLE:{
				return new ElementVarible((String) lexer.getToken().getValue());
			}
			case DOUBLE:{
				return (Element) new ElementConstantDouble((double) lexer.getToken().getValue());
			}
			case INTEGER:{
				return (Element) new ElementConstantInteger((int) lexer.getToken().getValue());
			}
			case STRING:{
				return (Element) new ElementString((String) lexer.getToken().getValue());
			}
			default: {
				throw new SmartScriptingParserException("Start,End,Step cannot be parsed.");
			}
		}
	}

	private EchoNode parseEcho(SmartScriptingLexer lexer) {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();		
		SmartScriptingToken token = lexer.nextToken();
		
		while(token.getType() != SmartScriptingTokenType.END) {
			if(token.getType() == SmartScriptingTokenType.VARIABLE) {
				elements.add((Element) new ElementVarible((String) token.getValue()));
			} else if(token.getType() == SmartScriptingTokenType.DOUBLE) {
				elements.add((Element) new ElementConstantDouble((double) token.getValue()));
			} else if(token.getType() == SmartScriptingTokenType.INTEGER) {
				elements.add((Element) new ElementConstantInteger((int) token.getValue()));
			} else if(token.getType() == SmartScriptingTokenType.OPERATOR) {
				elements.add((Element) new ElementOperator((String) token.getValue()));
			} else if(token.getType() == SmartScriptingTokenType.FUNCTION) {
				elements.add((Element) new ElementFunction((String) token.getValue()));
			} else if(token.getType() == SmartScriptingTokenType.STRING) {
				elements.add((Element) new ElementString((String) token.getValue()));
			} else {
				throw new SmartScriptingParserException("Cannot parse echo tag");
			}
			token = lexer.nextToken();
			
		}
		return new EchoNode((Element[]) elements.toArray());
		
	}
	

	
	
}
