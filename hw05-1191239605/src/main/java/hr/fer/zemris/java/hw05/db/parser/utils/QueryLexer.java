package hr.fer.zemris.java.hw05.db.parser.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *	Class which represents a simple lexer used for
 *	grouping character into {@link QueryToken}s. 
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class QueryLexer{
	
	/**Variable which holds the text that needs to be grouped.*/
	private char[] input;
	/**Current index.*/
	private int index;
	/**Last grouped token.*/
	private QueryToken token;
	
	/**List of all valid attribute names.*/
	private static final List<String> VALID_ATTRIBUTE_NAMES = new ArrayList<>(List.of("jmbag", "firstName", "lastName"));

	/**
	 * 	Constructs a new {@link QueryLexer} with the given value.
	 * 	@param text Text which needs to be grouped into tokens.
	 */
	public QueryLexer(String text) {
		input = text.toCharArray();
		index = -1;
	}
	
	/**
	 *	Returns the last grouped {@link QueryToken}.
	 *	@return The last grouped {@link QueryToken}. 
	 */
	public QueryToken getToken() {
		return token;
	}
	
	/**
	 *	Checks whether there is another token.
	 *	@return <code>true</code> if there is, <code>false</code> if not. 
	 */
	public boolean hasNext() {
		return index + 1 < input.length;
	}
	
	/**
	 *	Groups the next {@link QueryToken}.
	 *	@return The next {@link QueryToken}.
	 *	@throws QueryLexerException If the next token cannot be grouped. 
	 */
	public QueryToken nextToken() {
		try {
			checkIndex(1);
			index++;
			eatSpaces();
			if(groupOperator()) {
				return token;
			} else if(groupStringLiteral()) {
				return token;
			} else if(groupAnd()) {
				return token;
			} else if(groupAttributeName()) {
				return token;
			} else {
				throw new QueryLexerException("Input cannot be grouped.");
			}
		} catch(IndexOutOfBoundsException ex1) {
			throw new QueryLexerException("Input cannot be grouped.");
		}
	}
	
	/**
	 *	Skips whitespace in grouping.
	 *  @throws IndexOutOfBoundsException If there is no more input.
	 */
	private void eatSpaces() {
		while(Character.isWhitespace(input[index])) {
			checkIndex(1);
			index++;
		}	
	}

	/**
	 *	Groups a {@link QueryToken} of type OPERATOR.
	 *	@return <code>true</code> if successful, <code>false</code> otherwise. 
	 *  @throws IndexOutOfBoundsException If there is no more input.
	 */
	private boolean groupOperator() {
		char current = input[index];
		if("<>!=L".indexOf(current) == -1) {
			return false;
		}
		if(current == '=') {
			token = new QueryToken(QueryTokenType.OPERATOR, "=");
			return true;
		}
		if(current == '!') {
			checkIndex(1);
			if(input[index + 1] == '=') {
				token = new QueryToken(QueryTokenType.OPERATOR, "!=");
				index++;
				return true;
			}
			return false;
		}
		if(current == '>') {
			checkIndex(1);
			if(input[index + 1] == '=') {
				token = new QueryToken(QueryTokenType.OPERATOR, ">=");
				index++;
			} else {					
				token = new QueryToken(QueryTokenType.OPERATOR, ">");
			}
			return true;
		}
		if(current == '<') {
			checkIndex(1);
			if(input[index + 1] == '=') {
				token = new QueryToken(QueryTokenType.OPERATOR, "<=");
				index++;
			} else {					
				token = new QueryToken(QueryTokenType.OPERATOR, "<");
			}
			return true;
		}
		if(current == 'L') {
			checkIndex(3);
			if(input[index + 1] == 'I' &&
			   input[index + 2] == 'K' &&
			   input[index + 3] == 'E') {
				token = new QueryToken(QueryTokenType.OPERATOR, "LIKE");
				index +=3;
				return true;
			}
		}
		return false;
	}
	
	/**
	 *	Groups a {@link QueryToken} of type STRING_LITERAL.
	 *	@return <code>true</code> if successful, <code>false</code> otherwise. 
	 *  @throws IndexOutOfBoundsException If there is no more input.
	 */
	private boolean groupStringLiteral() {
		if(input[index] == '\"') {
			StringBuilder sb = new StringBuilder("\"");
			checkIndex(1);
			index++;
			while(input[index] != '\"') {
				sb.append(input[index]);
				checkIndex(1);
				index++;
			}
			sb.append("\"");
			token = new QueryToken(QueryTokenType.STRING_LITERAL, sb.toString());
			return true;
		}
		return false;
	}

	/**
	 *	Groups a {@link QueryToken} of type AND.
	 *	@return <code>true</code> if successful, <code>false</code> otherwise. 
	 *  @throws IndexOutOfBoundsException If there is no more input.
	 */
	private boolean groupAnd() {
		if(Character.toUpperCase(input[index]) == 'A') {
			checkIndex(2);
			if(Character.toUpperCase(input[index + 1]) == 'N' &&
			   Character.toUpperCase(input[index + 2]) == 'D') {
				token = new QueryToken(QueryTokenType.AND, "AND");
				index +=2;
				return true;
			}
		}
		return false;
	}
	
	/**
	 *	Groups a {@link QueryToken} of type ATTRIBUTE_NAME.
	 *	@return <code>true</code> if successful, <code>false</code> otherwise. 
	 *  @throws IndexOutOfBoundsException If there is no more input.
	 */
	private boolean groupAttributeName() {	
		StringBuilder sb = new StringBuilder("");
		while("<>!=\" ".indexOf(input[index]) == -1) {
			sb.append(input[index]);
			checkIndex(1);
			index++;
		}
		if(sb.length()!=0) {
			index--;
			if(!VALID_ATTRIBUTE_NAMES.contains(sb.toString())) {
				return false;
			}
			token = new QueryToken(QueryTokenType.ATTRIBUTE_NAME, sb.toString());
			return true;
		}
		return false;
	}
	
	/**
	 *	Method which checks if the index with the given offset is
	 *	in range.
	 *	@param offset The offset.
	 *	@throws IndexOutOfBoundsException If the index with the offset is bigger than
	 *									  the length of input.
	 */
	private void checkIndex(int offset) {
		if(index + offset >= input.length) {
			throw new IndexOutOfBoundsException("No more input");
		}
	}
	
}