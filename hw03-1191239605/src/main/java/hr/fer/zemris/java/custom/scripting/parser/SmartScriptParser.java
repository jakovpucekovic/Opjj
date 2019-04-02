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
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 *	Class SmartScriptParser which performs the parsing
 *	of the input using {@link SmartScriptToken}s which
 *	the {@link SmartScriptLexer} generates.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptParser {

	/**Helps with the tree construction.*/
	private ObjectStack nodeStack;
	
	/**
	 *	Constructs a new {@link SmartScriptParser} and which
	 *	then parses the given documentBody.
	 *	@param documentBody The document to parse.
	 */
	public SmartScriptParser(String documentBody) {
		
		nodeStack = new ObjectStack();
		nodeStack.push(new DocumentNode());
		
		parse(documentBody);
	}

	/**
	 *	Returns the {@link DocumentNode} of the parsed document.
	 *	@return The {@link DocumentNode} of the parsed document.
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) nodeStack.peek();
	}
	
	/**
	 *	Actual method that does the parsing. Constructs a tree representation
	 *	of the parsed document as described in the third homework.
	 *	@param documentBody Document body which needs to be parsed.
	 *	@throws SmartScriptParserException If document cannot be parsed.
	 */
	public void parse(String documentBody) {
		SmartScriptLexer lexer = new SmartScriptLexer(documentBody);
		
		SmartScriptToken token = lexer.nextToken();
		while(token.getType()!= SmartScriptTokenType.EOF) {
			if(token.getType() == SmartScriptTokenType.START_TAG) {
				lexer.setState(SmartScriptLexerState.IN_TAG);
				parseInTag(lexer);
			} else if(token.getType() == SmartScriptTokenType.END_TAG) {
				lexer.setState(SmartScriptLexerState.TEXT); 
			} else if(token.getType() == SmartScriptTokenType.TEXT) {
				TextNode textNode = new TextNode((String) token.getValue());
				((Node)nodeStack.peek()).addChildNode(textNode);
			} else {
				throw new SmartScriptParserException("error");
			}
			token = lexer.nextToken();
		}
	}
	
	/**
	 *	Method that does that actual parsing when inside of a tag.
	 *	@param lexer The {@link SmartScriptLexer} which gives the tokens.
	 *	@throws SmartScriptParserException If the tag cannot be parsed.
	 */
	private void parseInTag(SmartScriptLexer lexer) {
		SmartScriptToken token = lexer.nextToken();
		if(token.getType() == SmartScriptTokenType.FOR) {
			ForLoopNode forLoopNode = parseFor(lexer);
			((Node)nodeStack.peek()).addChildNode(forLoopNode);
			nodeStack.push(forLoopNode);
		} else if(token.getType() == SmartScriptTokenType.EQUALS) {
			EchoNode echoNode = parseEcho(lexer);
			((Node)nodeStack.peek()).addChildNode(echoNode);
		} else if(token.getType() == SmartScriptTokenType.END) {
			try{
				Object node = nodeStack.pop();
				if(!node.getClass().equals(ForLoopNode.class)) {
					throw new SmartScriptParserException("End tag doesn't close For tag.");
				}
			} catch(EmptyStackException ex) {
				throw new SmartScriptParserException("End tag doesn't close anything.");
			}
		} else {
			throw new SmartScriptParserException("Invalid tag name.");
		}
	}
	
	
	/**
	 *	Parses the FOR tag and adds it to its parent on the stack and pushes it 
	 *	onto the stack. A valid FOR tag has 3 or 4 elements and ends in $}.
	 *	@param lexer The {@link SmartScriptLexer} which gives the tokens.
	 *	@throws SmartScriptParserException If the for-loop cannot be parsed.
	 */
	private ForLoopNode parseFor(SmartScriptLexer lexer) {		
		
		lexer.nextToken();
		if(lexer.getToken().getType() != SmartScriptTokenType.VARIABLE) {
			throw new SmartScriptParserException("Invalid for loop");
		}
		
		ElementVarible var = new ElementVarible((String) lexer.getToken().getValue());
		lexer.nextToken();
	
		Element start, end, step = null;
		
		try {
			start = parseStartEndStepExpression(lexer);
			lexer.nextToken();
			end = parseStartEndStepExpression(lexer);
		} catch(SmartScriptParserException ex) {
			throw new SmartScriptParserException("Invalid for loop");
		}
		
		lexer.nextToken();
		try {
			step = parseStartEndStepExpression(lexer);
			lexer.nextToken();
		} catch(SmartScriptParserException ex) {
			step = new ElementString("");
		}
		
		if(lexer.getToken().getType() != SmartScriptTokenType.END_TAG) {
			throw new SmartScriptParserException("Invalid for loop");
		}
		lexer.setState(SmartScriptLexerState.TEXT);
		
		return new ForLoopNode(var, start, end, step);
		
	}
	
	/**
	 *	Helper method which is used to parse the start, end and step	
	 *	expressions in the for-loop.
	 *	@param lexer The {@link SmartScriptLexer} which gives the tokens.
	 *	@throws SmartScriptParserException If the expression cannot be parsed.
	 */
	private Element parseStartEndStepExpression(SmartScriptLexer lexer) {
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
				throw new SmartScriptParserException("Start,End,Step cannot be parsed.");
			}
		}
	}
	
	/**
	 *	Parses the ECHO tag and adds it to its parent on the stack.
	 *	@param lexer The {@link SmartScriptLexer} which gives the tokens.
	 *	@throws SmartScriptParserException If the echo tag cannot be parsed.
	 */
	private EchoNode parseEcho(SmartScriptLexer lexer) {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();		
		SmartScriptToken token = lexer.nextToken();
		
		while(token.getType() != SmartScriptTokenType.END_TAG) {
			if(token.getType() == SmartScriptTokenType.VARIABLE) {
				elements.add((Element) new ElementVarible((String) token.getValue()));
			} else if(token.getType() == SmartScriptTokenType.DOUBLE) {
				elements.add((Element) new ElementConstantDouble((double) token.getValue()));
			} else if(token.getType() == SmartScriptTokenType.INTEGER) {
				elements.add((Element) new ElementConstantInteger((int) token.getValue()));
			} else if(token.getType() == SmartScriptTokenType.OPERATOR) {
				elements.add((Element) new ElementOperator(Character.toString((char)token.getValue())));
			} else if(token.getType() == SmartScriptTokenType.FUNCTION) {
				elements.add((Element) new ElementFunction((String) token.getValue()));
			} else if(token.getType() == SmartScriptTokenType.STRING) {
				elements.add((Element) new ElementString((String) token.getValue()));
			} else {
				throw new SmartScriptParserException("Cannot parse echo tag");
			}
			token = lexer.nextToken();			
		}
		/*End of echo tag, switch state*/
		lexer.setState(SmartScriptLexerState.TEXT);
		return new EchoNode(objectArrayToElementArray(elements.toArray()));
		
	}
	
	/**
	 *	Helper method to cast Object[] into Element[].
	 *	@param objArray Object[] to be casted into Element[].
	 *	@return Given Object[] casted into Element[].
	 */
	private Element[] objectArrayToElementArray(Object[] objArray) {
		Element[] elements = new Element[objArray.length];
		for(int i = 0, s = objArray.length; i < s; i++ ) {
			elements[i] = (Element) objArray[i];
		}
		return elements;
	}
	
	
	
	
}
