package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.Objects;

/**
 *	Class which represents a simple query parser.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class QueryParser {

	/**
	 *	Enum which lists all the types of the {@link QueryToken}.
	 */
	private enum QueryTokenType{
		/**Command, e.g. "query", "exit" (without the ")*/
		COMMAND,
		/**Name of an attribute.*/
		ATTRIBUTE_NAME,
		/**<,<=,>,>=,=,!=,LIKE*/
		OPERATOR,
		/**A string literal enclosed in "".*/
		STRING_LITERAL,
		/**Word AND in any casing.*/
		AND
	}
	
	/**
	 *	Class which models a token which represents some
	 *	grouped characters. 
	 */
	private static class QueryToken{
		
		/**Type of the token.*/
		QueryTokenType type;
		/**Content of the token.*/
		String content;
		
		/**
		 *	Constructs a new {@link QueryToken} witht the given values.
		 *	@param type Type of the token.
		 *	@param content Content of the token. 
		 */
		public QueryToken(QueryTokenType type, String content) {
			this.type = type;
			this.content = content;
		}

	}

	/**
	 *	Class which represents a simple lexer used for
	 *	grouping character into {@link QueryToken}s. 
	 */
	public static class QueryLexer{
		
		/**Variable which holds the text that needs to be grouped.*/
		private char[] input;
		/**Current index.*/
		private int index;
		/**Last grouped token.*/
		private QueryToken token;
		
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
			return index + 1 <= input.length;
		}
		
		/**
		 *	Groups the next {@link QueryToken}.
		 *	@return The next {@link QueryToken}.
		 *	@throws IndexOutOfBoundsException If there is no more input.
		 *	@throws RuntimeException If the next token cannot be grouped. 
		 */
		public QueryToken nextToken() {
			checkIndex(1);
			index++;
			eatSpaces();
			if(groupCommand()) {
				return token;
			} else if(groupOperator()) {
				return token;
			} else if(groupStringLiteral()) {
				return token;
			} else if(groupAnd()) {
				return token;
			} else if(groupAttributeName()) {
				return token;
			} else {
				throw new RuntimeException("Invalid input.");
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
		 *	Groups a {@link QueryToken} of type AND.
		 *	@return <code>true</code> if successful, <code>false</code> otherwise. 
		 *  @throws IndexOutOfBoundsException If there is no more input.
		 */
		private boolean groupAnd() {
			if(Character.toUpperCase(input[index]) == 'A') {
				checkIndex(2);
				if(Character.toUpperCase(input[index]) == 'N' &&
				   Character.toUpperCase(input[index]) == 'D') {
					token = new QueryToken(QueryTokenType.AND, "AND");
					index +=2;
					return true;
				}
			}
			return false;
		}
		
		/**
		 *	Groups a {@link QueryToken} of type COMMAND.
		 *	@return <code>true</code> if successful, <code>false</code> otherwise. 
		 *  @throws IndexOutOfBoundsException If there is no more input.
		 */
		private boolean groupCommand() {
			if(input[index] == 'q') {
				checkIndex(4);
				if(input[index + 1] == 'u' &&
				   input[index + 2] == 'e' &&
			       input[index + 3] == 'r' &&
			       input[index + 4] == 'y') {
					token = new QueryToken(QueryTokenType.COMMAND, "query");
					index +=4;
					return true;
				}
			}
			if(input[index] == 'e') {
				checkIndex(3);
				if(input[index + 1] == 'x' &&
			       input[index + 2] == 'i' &&
			       input[index + 3] == 't') {
					token = new QueryToken(QueryTokenType.COMMAND, "exit");
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
				token = new QueryToken(QueryTokenType.ATTRIBUTE_NAME, sb.toString());
				return true;
			}
			return false;
		}
		
		/**
		 *	Skips whitespace in grouping.
		 *  @throws IndexOutOfBoundsException If there is no more input.
		 */
		private void eatSpaces() {
			while(Character.isWhitespace(input[index])) {//TODO jel samo razmake ili sve whitespaceove?
				checkIndex(1);
				index++;
			}	
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
	
	/**Lexer used for grouping of character.*/
	private QueryLexer lexer;
	/**ArrayList used to store grouped tokens.*/
	private ArrayList<QueryToken> array;
	
	/**
	 *	Constructs a new {@link QueryParser} with the given value.
	 *	@param queryString {@link String} which needs to be parsed. 
	 */
	public QueryParser(String queryString) {
		array = new ArrayList<>();
		lexer = new QueryLexer(queryString);
		/*Fill the array with the QueryTokens of the given queryString.*/
		while(lexer.hasNext()) {
			array.add(lexer.nextToken());
		}
	}
	
	/**
	 *	Checks if the query is a direct query. Direct queries
	 *  are made only of attribute name "jmbag", operator "=" and a 
	 *  string literal.
	 *  @return <code>true</code> if the query is a direct query, <code>false</code> if not.
	 */
	public boolean isDirectQuery() {
		if(array.size() != 3) {
			return false;
		}
		if((array.get(0).type == QueryTokenType.ATTRIBUTE_NAME) && (array.get(0).content.equals("jmbag")) &&
		   array.get(1).content.equals("=") &&
		   array.get(2).type == QueryTokenType.STRING_LITERAL) {
			return true;
		}
		return false;
	}
	
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Query isn't a direct query.");
		}
		//TODO nekako doci do StudentDatabase i pozvati forJMBAG
		
		
		
		return null;
	}
	
	
}