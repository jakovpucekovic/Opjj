package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 *	Class SmartScriptLexer which performs the grouping
 *	of the input into {@link SmartScriptToken} with the rules
 *	described in the third homework.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class SmartScriptLexer {

	/**
	 * 	Text that needs to be tokenized.
	 */
	private char[] data;
	
	/**
	 * 	The current {@link SmartScriptToken}.
	 */
	private SmartScriptToken token;
	
	/**
	 * 	The index of the next character to evaluate.
	 */
	private int currentIndex;
		
	private SmartScriptLexerState state;
	
	/**
	 * 	Constructs a {@link SmartScriptLexer} which evaluates the 
	 * 	given string.
	 * 	@param text Text to evaluate.
	 */
	public SmartScriptLexer(String text) {
		data = text.toCharArray();
		state = SmartScriptLexerState.TEXT;
	}
	
	/**
	 *	Sets the state of the {@link SmartScriptLexer}.
	 *	@param state State to set the {@link SmartScriptLexer} to.
	 *	@throws NullPointerException If given state is null.
	 */
	public void setState(SmartScriptLexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	/**
	 * 	Returns the last {@link SmartScriptToken} made with
	 * 	the <code>nextToken</code> method.
	 * 	@return Last generated token.
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * 	Return the next {@link SmartScriptToken}.
	 * 	@return The next {@link SmartScriptToken}.
	 */
	public SmartScriptToken nextToken() {
		if(state.equals(SmartScriptLexerState.TEXT)) {
			return nextTokenText();
		}
		if(state.equals(SmartScriptLexerState.IN_TAG)) {
			return nextTokenInTag();
		}
		throw new SmartScriptLexerException("SmartScriptLexerState is not set");
	}

	/**
	 *	Method which returns the nextToken when the {@link SmartScriptLexer} is 
	 *	in TEXT state.
	 *	@return The next {@link SmartScriptToken}.
	 *	@throws SmartScriptLexerException If called after tokenizing the whole data.
	 */
	private SmartScriptToken nextTokenText() {
			if(currentIndex > data.length) {
				throw new SmartScriptLexerException("Processed all data already");
			}
			
			/*In TEXT mode, we can only get EOF, TEXT or START_TAG.*/
			if(parseEof()) {
				return token;
			} else {
				if(parseText()) {
					return token;
				} else if(parseStartTag()) {
					return token;
				} 
				else{
					throw new SmartScriptLexerException("Cannot parse text.");
				}
			} 
		}

	/**
	 *	Tries to parse TEXT token from current position in TEXT mode.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not.
	 */
	private boolean parseText() {
		StringBuilder text = new StringBuilder();
		
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				text.append(data[currentIndex]);
				currentIndex++;
			} 
			/*Allow { in text if it's not part of a START_TAG*/
			else if(data[currentIndex] == '{') { 
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '$') {
						break;							
					}
				}
			} 
			/*Allow escaping a backslash or { with a backslash.*/
			else if(data[currentIndex] == '\\') {
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {	
						text.append(data[currentIndex + 1]);
						currentIndex+=2;					
					} else {
						throw new SmartScriptLexerException("\\ at the end of file.");
					}
				} else {
					throw new SmartScriptLexerException("Invalid escape seqeunce");
				}
			} 
			else {
				text.append(data[currentIndex]);
				currentIndex++;
			}
		}
		/*If parsed something.*/
		if(text.length() > 0) {
			token = new SmartScriptToken(SmartScriptTokenType.TEXT, text.toString());
			return true;
		}
		return false;
	}

	/**
	 *	Method which returns the nextToken when the {@link SmartScriptLexer} is 
	 *	in IN_TAG state.
	 *	@return The next {@link SmartScriptToken}.
	 *	@throws SmartScriptLexerException If called after tokenizing the whole data.
	 */
	private SmartScriptToken nextTokenInTag() {
		if(currentIndex > data.length) {
			throw new SmartScriptLexerException("Processed all data already");
		}
		
		if(parseEof()) {
			return token;
		} else if(parseSpaces()) { /*First parse all whitespace*/
			if(parseEndTag()) {
				return token;
			} else if(parseStartTag()) {
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
				throw new SmartScriptLexerException("Cannot parse tag");
			}
		} else if(parseEof()) { /*If input ends only in whitespace.*/
			return token;
		} else {
			throw new SmartScriptLexerException("Cannot parse tag");
		}
	}

	/**
	 *	Tries to parse EOF token from current position.
	 *	@return <code>true</code> if yes, <code>false</code> if no. 
	 */
	private boolean parseEof() {
		if(currentIndex == data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
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
	 *	Tries to parse VARIABLE token from current position in IN_TAG mode.
	 *	VARIABLE must start with a letter which can be followed by more letters, numbers or underscores.
	 *	@return <code>true</code> if it succeeded, <code>false</code> if not.
	 *	@throws SmartScriptLexerException If there is an invalid escape sequence. 
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
			token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, variable.toString());
			return true;
		}
		return false;
	}

	/**
	 *	Tries to parse INTEGER token from current position in IN_TAG mode.
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
				token = new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.parseInt(number.toString()));	
				return true;
			} catch(NumberFormatException ex) {
				throw new SmartScriptLexerException("Cannot parse Integer");
			}
		}
		return false;
	}
	
	/**
	 *	Tries to parse DOUBLE token from current position in IN_TAG mode. Double must be in digit-dot-digit format.
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
				/*Found a . and hasn't already parsed one.*/
				if(number.length() >= 1 && number.indexOf(".") == -1 ) {
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
				token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.parseDouble(number.toString()));
				return true;
			} catch(NumberFormatException ex) {
				throw new SmartScriptLexerException("Cannot parse double");
			}
		}
		return false;
	}
	
	
	/**
	 *	Tries to parse STRING token from current position in IN_TAG mode. STRING starts 
	 *	and ends with "" and can have letters and numbers in it. Also supports backslash
	 *	escape character on backslash and ".
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
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
			token = new SmartScriptToken(SmartScriptTokenType.STRING, string.toString());
			return true;
		}
		return false;
		
		}

	/**
	 *	Tries to parse FUNCTION token from current position in IN_TAG mode.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
	private boolean parseFunction() {
		/*Function starts with @*/
		if(data[currentIndex] != '@') {
				return false;
		}
		/*@ isn't part of the functions name*/
		currentIndex++;
	
		StringBuilder function = new StringBuilder();
			
		/*Must start with letter*/
		if(!Character.isLetter(data[currentIndex])) {
			return false;
		}
		function.append(data[currentIndex]);
		currentIndex++;
		/*Same rules as VARIABLE*/
		while(currentIndex < data.length) {
			if(Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_' ) {
				function.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
		
		if(function.length() > 0) {
			token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, function.toString());
			return true;
		}
		return false;
	}
	
	/**
	 *	Tries to parse OPERATOR token from current position in IN_TAG mode.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
	private boolean parseOperator() {
		if(currentIndex < data.length) {
			if("+-*/^".indexOf(data[currentIndex]) != -1) {
				token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				return true;
			}
		}
		return false;
	}
	
	/**
	 *	Tries to parse START_TAG token from current position.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
	private boolean parseStartTag() {
		if(currentIndex + 1 < data.length) {
			if(data[currentIndex] == '{' && data[currentIndex + 1] == '$' ) {
				token = new SmartScriptToken(SmartScriptTokenType.START_TAG, "{$");
				currentIndex += 2;
				return true;
			} 
		}
		return false;
	}
	
	/**
	 *	Tries to parse END_TAG token from current position.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 * 	@throws LexerException If the number cannot be parsed as {@link Long}.
	 */
	private boolean parseEndTag() {
		if(currentIndex + 1 < data.length) {
			if(data[currentIndex] == '$' && data[currentIndex + 1] == '}' ) { 
				token = new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}");
				currentIndex += 2;
				return true;
			} 
		}
		return false;
	}
	
	/**
	 *	Tries to parse FOR token from current position in IN_TAG mode. For is case insensitive.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
	private boolean parseFor() {
		if(currentIndex + 3 < data.length) {
			char c1 = data[currentIndex];
			char c2 = data[currentIndex + 1];
			char c3 = data[currentIndex + 2];
			
			if( Character.toUpperCase(c1) == 'F' && Character.toUpperCase(c2) == 'O' && Character.toUpperCase(c3) == 'R' ) { 
				token = new SmartScriptToken(SmartScriptTokenType.FOR, String.format("%c%c%c", c1, c2, c3));
				currentIndex += 3;
				return true;
			} 
		}
		return false;
	}
	
	/**
	 *	Tries to parse END token from current position in IN_TAG mode. End is case insensitive.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
	private boolean parseEnd() {
		if(currentIndex + 3 < data.length) {
			char c1 = data[currentIndex];
			char c2 = data[currentIndex + 1];
			char c3 = data[currentIndex + 2];
			
			if( Character.toUpperCase(c1) == 'E' && Character.toUpperCase(c2) == 'N' && Character.toUpperCase(c3) == 'D' ) {  
				token = new SmartScriptToken(SmartScriptTokenType.END, String.format("%c%c%c", c1, c2, c3));
				currentIndex += 3;
				return true;
			} 
		}
		return false;
	}
	
	/**
	 *	Tries to parse EQUALS token from current position in IN_TAG mode.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 */
	private boolean parseEqual() {
		if(currentIndex < data.length) {
			if(data[currentIndex] == '=') { 
				token = new SmartScriptToken(SmartScriptTokenType.EQUALS, "=");
				currentIndex++;
				return true;
			} 
		}
		return false;
	}
}
