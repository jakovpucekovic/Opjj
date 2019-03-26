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
		state = SmartScriptingLexerState.ALL_TEXT;
	}
	
	public void setState(SmartScriptingLexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	public SmartScriptingLexerState getState() {
		return state;
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
		if(state.equals(SmartScriptingLexerState.ALL_TEXT)) {
			return nextTokenAllText();
		}
		if(state.equals(SmartScriptingLexerState.IN_TAG)) {
			return nextTokenInTag();
		}
		throw new SmartScriptingLexerException("SmartScriptingLexer State is not ALL_TEXT.");
	}

	/**
	 * 
	 */
	private SmartScriptingToken nextTokenInTag() {
		if(currentIndex > data.length) {
			throw new SmartScriptingLexerException("Processed all data");
		}
		
		if(eof()) {
			return token;
		} else if(spaces()) {
			if(number()) {
				return token;
			} else if(word()) {
				return token;
			} else if(dolar()) {
				return token;
			} else if(symbol()) {
				return token;
			} else {
				throw new SmartScriptingLexerException("nemoguce da nije nista");
			}
		} else {
			if(!eof()) {
				throw new SmartScriptingLexerException("should have been eof");
			}
			return token;
		}
	}
	
	private SmartScriptingToken nextTokenAllText() {
			if(currentIndex > data.length) {
				throw new SmartScriptingLexerException("Processed all data");
			}
			
			if(eof()) {
				return token;
			} else {
				if(allText()) {
					return token;
				} else if(dolar()){
					return token;
				} else{
					throw new SmartScriptingLexerException("nemoguce da nije nista");
				}
			} 
	//		if(!eof()) {
	//			throw new SmartScriptingLexerException("should have been eof");
	//		}
	//		return token;
			
		}

	/**
	 * 	Skips the whitespace in data[]
	 * 	@return <code>true</code> if the currentIndex is not at the end of data[],
	 * 			<code>false</code> if it is.
	 */
	private boolean spaces() {
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
	 * 	Tries to parse number from current position.
	 * 	@return <code>true</code> if it succeeded, <code>false</code> otherwise
	 * 	@throws LexerException If the number cannot be parsed as {@link Long}.
	 */
	private boolean number() {
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
				if(number.indexOf(".") == -1) {
					token = new SmartScriptingToken(SmartScriptingTokenType.NUMBER, Integer.parseInt(number.toString()));	
				}
				else {
					token = new SmartScriptingToken(SmartScriptingTokenType.NUMBER, Double.parseDouble(number.toString()));
				}
				return true;
			} catch(NumberFormatException ex) {
				throw new SmartScriptingLexerException("Cannot parse double");
			}
		}
		return false;
	}

	/**
	 *	Tries to parse word from current position.
	 *	@return <code>true</code> if it succeeded, <code>false</code> if not.
	 *	@throws LexerException If there is an invalid escape sequence. 
	 */
	private boolean word() {
		StringBuilder word = new StringBuilder();
		while(currentIndex < data.length) {
			if(Character.isLetter(data[currentIndex])) {
				word.append(data[currentIndex]);
				currentIndex++;
			} else if(data[currentIndex] == '\\') {
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '\"') {
						word.append(data[currentIndex + 1]);
						currentIndex += 2;
					} else {
						throw new SmartScriptingLexerException("Invalid escape sequence");
					}
				} else {
					throw new SmartScriptingLexerException("Invalid escape sequence");
				}
			} else {
				break;
			}
		}
		
		if(word.length() > 0) {
			token = new SmartScriptingToken(SmartScriptingTokenType.WORD, word.toString());
			return true;
		}
		return false;
	}
	
	/**
	 *	Tries to parse symbol from current position.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not. 
	 */
	private boolean symbol() {
		if(currentIndex < data.length) {
			if(!Character.isLetterOrDigit(data[currentIndex]) && !Character.isWhitespace(data[currentIndex])) {
				token = new SmartScriptingToken(SmartScriptingTokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				return true;
			}
		}
		return false;
	}

	/**
	 *	Tries to parse {$ or $} from current position.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not. 
	 */
	private boolean dolar() {
		if(currentIndex < data.length) {
			if(data[currentIndex] == '{') { // checks for {$
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '$') {
						token = new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "{$");
						currentIndex += 2;
						return true;
					} else {
						throw new SmartScriptingLexerException("Invalid escape sequence");
					}
				} else {
					throw new SmartScriptingLexerException("Invalid escape sequence");
				}
			} else if(data[currentIndex] == '$') {	//checks for $}
				if(currentIndex + 1 < data.length) {
					if(data[currentIndex + 1] == '}') {
						token = new SmartScriptingToken(SmartScriptingTokenType.MODE_SWITCHER, "$}");
						currentIndex += 2;
						return true;
					} else {
						throw new SmartScriptingLexerException("Invalid escape sequence");
					}
				} else {
					throw new SmartScriptingLexerException("Invalid escape sequence");
				}
			}
		}
		return false;
	}
	
	
	/**
	 *	Tries to parse word from current position in ALL_TEXT mode.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not.
	 */
	private boolean allText() {
		StringBuilder word = new StringBuilder();
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				word.append(data[currentIndex]);
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
						word.append(data[currentIndex + 1]);
						currentIndex+=2;					
					} else {
						throw new SmartScriptingLexerException("Invalid escape seqeunce");
					}
				} else {
					throw new SmartScriptingLexerException("Invalid escape seqeunce");
				}
			} else {
				word.append(data[currentIndex]);
				currentIndex++;
			}
			
		}
		
		if(word.length() > 0) {
			token = new SmartScriptingToken(SmartScriptingTokenType.TEXT, word.toString());
			return true;
		}
		return false;
	}

	/**
	 *	Checks if currentIndex is at the end of data.
	 *	@return <code>true</code> if yes, <code>false</code> if no. 
	 */
	private boolean eof() {
		if(currentIndex == data.length) {
			token = new SmartScriptingToken(SmartScriptingTokenType.EOF, null);
			currentIndex++;
			return true;
		}
		return false;
	}		
	
}
