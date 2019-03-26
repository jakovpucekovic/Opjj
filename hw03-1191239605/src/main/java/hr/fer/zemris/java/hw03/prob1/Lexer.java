package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 *	Class Lexer...
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class Lexer {

	/**
	 * 	The inputted text.
	 */
	private char[] data;
	
	/**
	 * 	The current {@link Token}.
	 */
	private Token token;
	
	/**
	 * 	The index of the next character to evaluate.
	 */
	private int currentIndex;
	
	private LexerState state;
	
	/**
	 * 	Constructs a {@link Lexer} which evaluates the 
	 * 	given string.
	 * 	@param text Text to evaluate.
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		state = LexerState.BASIC;
	}
	
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	/**
	 * 	Generates and returns the next {@link Token} in Basic work mode.
	 * 	@return The newly generated {@link Token}.
	 * 	@throws LexerException If an error occurs.
	 */
	private Token nextTokenBasic() {
		if(currentIndex > data.length) {
			throw new LexerException("Processed all data");
		}
		
		if(eof()) {
			return token;
		} else if(spaces()) {
			if(number()) {//sta s minusom
				return token;
			} else if(word()) {
				return token;
			} else if(symbol()) {
				return token;
			} else {
				throw new LexerException("nemoguce da nije nista");
			}
		} else {
			if(!eof()) {
				throw new LexerException("should have been eof");
			}
			return token;
		}
	}
	
	/**
	 * 	Generates and returns the next {@link Token}.
	 * 	@return The newly generated {@link Token}.
	 * 	@throws LexerException If an error occurs.
	 */
	public Token nextToken() {
		if(state.equals(LexerState.BASIC)) {
			return nextTokenBasic();
		}
		if(state.equals(LexerState.EXTENDED)) {
			return nextTokenExtended();
		}
		throw new LexerException("Lexer State is neither basic nor extended.");
	}

	
	private Token nextTokenExtended() {
		if(currentIndex > data.length) {
			throw new LexerException("Processed all data");
		}
		
		if(eof()) {
			return token;
		} else if(spaces()) {
			if(wordExtended()) {
				return token;
			} else if(hashtag()){
				return token;
			} else{
				throw new LexerException("nemoguce da nije nista");
			}
		} else {
			if(!eof()) {
				throw new LexerException("should have been eof");
			}
			return token;
		}
	}
	/**
	 * 	Skips the whitespaces in data[]
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
	 * 	@return <code>true</code> if it succeded, <code>false</code> otherwise
	 * 	@throws LexerException If the number cannot be parsed as {@link Long}.
	 */
	private boolean number() {
		StringBuilder number = new StringBuilder();
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
				token = new Token(TokenType.NUMBER, Long.parseLong(number.toString()));
				return true;
			} catch(NumberFormatException ex) {
				throw new LexerException("Cannot parse long");
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
					if(Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\') {
						word.append(data[currentIndex + 1]);
						currentIndex += 2;
					} else {
						throw new LexerException("Invalid escape sequence");
					}
				} else {
					throw new LexerException("Invalid escape sequence");
				}
			} else {
				break;
			}
		}
		
		if(word.length() > 0) {
			token = new Token(TokenType.WORD, word.toString());
			return true;
		}
		return false;
	}
	
	/**
	 *	Tries to parse word from current position in extended mode.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not.
	 *	@throws LexerException If there is an invalid escape sequence. 
	 */
	private boolean wordExtended() {
		StringBuilder word = new StringBuilder();
		while(currentIndex < data.length) {
			if(!Character.isWhitespace(data[currentIndex])) {
				if(data[currentIndex] == '#') {
					break;
				}
				word.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
		
		if(word.length() > 0) {
			token = new Token(TokenType.WORD, word.toString());
			return true;
		}
		return false;
	}
	
	/**
	 *	Tries to parse # symbol from current position.
	 *	@return <code>true</code> if it succeded, <code>false</code> if not. 
	 */
	private boolean hashtag() {
		if(currentIndex < data.length) {
			if(data[currentIndex] == '#') {
				token = new Token(TokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				return true;
			}
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
				token = new Token(TokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				return true;
			}
		}
		return false;
	}
	
	/**
	 *	Checks if currentIndex is at the end of data.
	 *	@return <code>true</code> if yes, <code>false</code> if no. 
	 */
	private boolean eof() {
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return true;
		}
		return false;
	}
	
	/**
	 * 	Returns the last generated {@link Token}. Doesn't
	 * 	generate a new {@link Token}.
	 * 	@return The last generated {@link Token}.
	 */
	public Token getToken() {
		return token;
	}		
	
}
