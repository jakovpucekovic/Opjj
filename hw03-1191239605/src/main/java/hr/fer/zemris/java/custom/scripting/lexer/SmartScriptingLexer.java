package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 *	Class SmartScriptingLexer...
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptingLexer {

	/**
	 * 	The inputted text.
	 */
	private char[] data;
	
	/**
	 * 	The current {@link Token}.
	 */
	private SmartScriptingToken token;
	
	/**
	 * 	The index of the next character to evaluate.
	 */
	private int currentIndex;
		
	private SmartScriptingLexerState state;
	
	/**
	 * 	Constructs a {@link SmartScriptingLexer} which evaluates the 
	 * 	given string.
	 * 	@param text Text to evaluate.
	 */
	public SmartScriptingLexer(String text) {
		data = text.toCharArray();
		state = SmartScriptingLexerState.TEXT;
	}
	
	public void setState(SmartScriptingLexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	/**
	 * 
	 */
	public SmartScriptingToken getToken() {
		return token;
	}

	/**
	 * 	
	 */
	public SmartScriptingToken nextToken() {
		if(state.equals(SmartScriptingLexerState.TEXT)) {
			return nextTokenText();
		}
		if(state.equals(SmartScriptingLexerState.IN_TAG)) {
			return nextTokenInTag();
		}
		throw new SmartScriptingLexerException("SmartScriptingLexer State is not ALL_TEXT.");
	}

	private SmartScriptingToken nextTokenText() {
			if(currentIndex > data.length) {
				throw new SmartScriptingLexerException("Processed all data");
			}
			
			if(parseEof()) {
				return token;
			} else {
				if(parseText()) {
					return token;
				} else if(parseStartTag()) {
					return token;
				} 
				else{
					throw new SmartScriptingLexerException("nemoguce da nije nista");
				}
			} 
		}

	/**
	 *	Tries to parse word from current position in TEXT mode.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not.
	 */
	private boolean parseText() {
		StringBuilder text = new StringBuilder();
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				text.append(data[currentIndex]);
				currentIndex++;
			} else if(data[currentIndex] == '{') { 
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '$') {	//{$
						break;							
					}
				}
			} else if(data[currentIndex] == '\\') {
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {	// \ ili { kao dio stringa
						text.append(data[currentIndex + 1]);
						currentIndex+=2;					
					} else {
						throw new SmartScriptingLexerException("\\ at the end of file.");
					}
				} else {
					throw new SmartScriptingLexerException("Invalid escape seqeunce");
				}
			} else {
				text.append(data[currentIndex]);
				currentIndex++;
			}
			
		}
		
		if(text.length() > 0) {
			token = new SmartScriptingToken(SmartScriptingTokenType.TEXT, text.toString());
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	private SmartScriptingToken nextTokenInTag() {
		if(currentIndex > data.length) {
			throw new SmartScriptingLexerException("Processed all data");
		}
		
		if(parseEof()) {
			return token;
		} else if(parseSpaces()) {
			if(parseEndTag()) {
				return token;
			} else if(parseEnd()) {
				return token;
			} else if(parseEqual()) {
				return token;
			} else if(parseFor()) {
				return token;
			} else if(parseInteger()) {
				return token;
			} else if(parseDouble()) {
				return token;
			} else if(parseOperator()) {
				return token;
			} else if(parseFunction()) {
				return token;
			} else if(parseVariable()) {
				return token;
			} else if(parseString()) {
				return token;
			} else {
				throw new SmartScriptingLexerException("nemoguce da nije nista");
			}
		} else if(parseEof()) { //moze se dogoditi da parseSpaces() dode do kraja
			return token;
		} else {
			throw new SmartScriptingLexerException("should never execute");
		}
	}

	/**
	 *	Checks if currentIndex is at the end of data.
	 *	@return <code>true</code> if yes, <code>false</code> if no. 
	 */
	private boolean parseEof() {
		if(currentIndex == data.length) {
			token = new SmartScriptingToken(SmartScriptingTokenType.EOF, null);
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * 	Skips the whitespace in data[]
	 * 	@return <code>true</code> if the currentIndex is not at the end of data[],
	 * 			<code>false</code> if it is.
	 */
	private boolean parseSpaces() {
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 *	Tries to parse variable from current position.
	 *	@return <code>true</code> if it succeeded, <code>false</code> if not.
	 *	@throws LexerException If there is an invalid escape sequence. 
	 */
	private boolean parseVariable() {
		if(!Character.isLetter(data[currentIndex])) {
			return false;
		}
		StringBuilder variable = new StringBuilder();

		variable.append(data[currentIndex]);
		currentIndex++;
	
		while(currentIndex < data.length) {
			if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_' ) {
				variable.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
		
		if(variable.length() > 0) {
			token = new SmartScriptingToken(SmartScriptingTokenType.VARIABLE, variable.toString());
			return true;
		}
		return false;
	}

	/**
	 * 	Tries to parse integer from current position.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 * 	@throws LexerException If the number cannot be parsed as {@link Long}.
	 */
	private boolean parseInteger() {
		StringBuilder number = new StringBuilder();
		/*Negative numbers*/
		if(currentIndex + 1 < data.length) { 
			if(data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
				number.append(data[currentIndex]);
				number.append(data[currentIndex + 1]);
				currentIndex += 2;
			}
		}
		
		while(currentIndex < data.length) {
			if(Character.isDigit(data[currentIndex])) {
				number.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
		
		if(number.length() > 0) {
			try {
				token = new SmartScriptingToken(SmartScriptingTokenType.INTEGER, Integer.parseInt(number.toString()));	
				return true;
			} catch(NumberFormatException ex) {
				throw new SmartScriptingLexerException("Cannot parse Integer");
			}
		}
		return false;
	}
	
	/**
	 * 	Tries to parse double from current position.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 * 	@throws LexerException If the number cannot be parsed as {@link Long}.
	 */
	private boolean parseDouble() {
		StringBuilder number = new StringBuilder();
		/*Negative numbers*/
		if(currentIndex + 1 < data.length) { 
			if(data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
				number.append(data[currentIndex]);
				number.append(data[currentIndex + 1]);
				currentIndex += 2;
			}
		}
		
		while(currentIndex < data.length) {
			if(Character.isDigit(data[currentIndex])) {
				number.append(data[currentIndex]);
				currentIndex++;
			} else if (data[currentIndex] == '.'){ 
				if(number.length() >= 1 && number.indexOf(".") == -1 ) { //nasao je tocku i ne sadrzi ju vec
					if(currentIndex + 1 < data.length) { 
						if(Character.isDigit(data[currentIndex])) {
							number.append(data[currentIndex]);
							number.append(data[currentIndex + 1]);
							currentIndex += 2;
						}
					}
				}
			} else {
				break;
			}
		}
		
		if(number.length() > 0) {
			try {
				token = new SmartScriptingToken(SmartScriptingTokenType.DOUBLE, Double.parseDouble(number.toString()));
				return true;
			} catch(NumberFormatException ex) {
				throw new SmartScriptingLexerException("Cannot parse double");
			}
		}
		return false;
	}
	
	
	//string moze sadrzavati slova, brojeve pocinje i zavrsava sa ""
	private boolean parseString() {
		if(data[currentIndex] != '\"') {
			return false;
		}

		StringBuilder string = new StringBuilder();
		currentIndex++;
		
		while(currentIndex < data.length) {
			if(data[currentIndex] == '\"') {
				currentIndex++;
				break;
			} else if(data[currentIndex] == '\\'){
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '\"') {
						string.append(data[currentIndex + 1]);
						currentIndex += 2;
					}
				}
			} else {
				string.append(data[currentIndex]);
				currentIndex++;
			}
		}
		
		if(string.length() > 0) {
			token = new SmartScriptingToken(SmartScriptingTokenType.STRING, string.toString());
			return true;
		}
		return false;
		
		}

	private boolean parseFunction() {
	/*Function starts with @*/
	if(data[currentIndex] != '@') {
			return false;
	}		
	currentIndex++; //@ ne ulazi u ime funkcije

	StringBuilder function = new StringBuilder();
		
	/*Must start with letter*/
	if(!Character.isLetter(data[currentIndex])) {
		return false;
	}
	function.append(data[currentIndex]);
	currentIndex++;

	while(currentIndex < data.length) {
		if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_' ) {
			function.append(data[currentIndex]);
			currentIndex++;
		} else {
			break;
		}
	}
	
	if(function.length() > 0) {
		token = new SmartScriptingToken(SmartScriptingTokenType.FUNCTION, function.toString());
		return true;
	}
	
	return false;
	}
	
	
	private boolean parseOperator() {
		if(currentIndex < data.length) {
			if("+-*/^".indexOf(data[currentIndex]) != -1) {
				token = new SmartScriptingToken(SmartScriptingTokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				return true;
			}
		}
		return false;
	}
	
	private boolean parseStartTag() {
		if(currentIndex + 1 < data.length) {
			if(data[currentIndex] == '{' && data[currentIndex + 1] == '$' ) { // checks for {$
				token = new SmartScriptingToken(SmartScriptingTokenType.START_TAG, "{$");
				currentIndex += 2;
				return true;
			} 
		}
		return false;
	}
	
	
	private boolean parseEndTag() {
		if(currentIndex + 1 < data.length) {
			if(data[currentIndex] == '$' && data[currentIndex + 1] == '}' ) { // checks for $}
				token = new SmartScriptingToken(SmartScriptingTokenType.END_TAG, "$}");
				currentIndex += 2;
				return true;
			} 
		}
		return false;
	}
	
		private boolean parseFor() {
		if(currentIndex + 3 < data.length) {
			char c1 = data[currentIndex];
			char c2 = data[currentIndex + 1];
			char c3 = data[currentIndex + 3];
			
			if( Character.toUpperCase(c1) == 'F' && Character.toUpperCase(c2) == 'O' && Character.toUpperCase(c3) == 'R' ) { 
				token = new SmartScriptingToken(SmartScriptingTokenType.FOR, String.format("%c%c%c", c1, c2, c3));
				currentIndex += 3;
				return true;
			} 
		}
		return false;
	}
	
	private boolean parseEnd() {
		if(currentIndex + 3 < data.length) {
			char c1 = data[currentIndex];
			char c2 = data[currentIndex + 1];
			char c3 = data[currentIndex + 3];
			
			if( Character.toUpperCase(c1) == 'E' && Character.toUpperCase(c2) == 'N' && Character.toUpperCase(c3) == 'D' ) {  
				token = new SmartScriptingToken(SmartScriptingTokenType.END, String.format("%c%c%c", c1, c2, c3));
				currentIndex += 3;
				return true;
			} 
		}
		return false;
	}
	
	private boolean parseEqual() {
		if(currentIndex < data.length) {
			if(data[currentIndex] == '=') { 
				token = new SmartScriptingToken(SmartScriptingTokenType.EQUALS, "=");
				currentIndex++;
				return true;
			} 
		}
		return false;
	}
}
