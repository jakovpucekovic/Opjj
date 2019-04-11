package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

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
	private static enum QueryTokenType{
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

		/**
		 *	Returns a string representation of the object which is only
		 *	the content. Also content of type STRING_LITERAL is returned
		 *	as a normal {@link String} not a literal(only 1 ").
		 *	@return String representation of the token.
		 */
		@Override
		public String toString() {
			/*Return STRING_LITERAL as normal String, without double ".*/
			if(type == QueryTokenType.STRING_LITERAL) {
				return content.substring(1, content.length() - 1);
			}
			return content;
		}
	}

	/**
	 *	Class which represents a simple lexer used for
	 *	grouping character into {@link QueryToken}s. 
	 */
	private static class QueryLexer{
		
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
		try {
			while(lexer.hasNext()) {
				array.add(lexer.nextToken());
			}
		} catch(QueryLexerException ex) {
			throw new QueryParserException("Given query cannot be parsed.");
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
	
	/**
	 *	Returns the jmbag given in the direct query.
	 *	@return The string literal given in the direct query which equals jmbag.
	 *	@throws IllegalStateException If the query is not a direct query. 
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Query isn't a direct query.");
		}
		return array.get(2).toString();
	}
	/**
	 * 	Method creates a {@link List} of {@link ConditionalExpression}s which
	 * 	represent the parsed query.
	 * 	@return {@link List} of {@link ConditionalExpression}s.
	 * 	@throws QueryParserException If {@link ConditionalExpression}s can't be 
	 * 								 made from the parsed query.
	 */
	public List<ConditionalExpression> getQuery(){
		List<ConditionalExpression> list = new ArrayList<>();
		int i = 0;
		/*Each expression has 3 parts.*/
		while(i + 2 < array.size()) {
			var name = array.get(i);
			var operator = array.get(i + 1);
			var literal = array.get(i + 2);
			
			if(name.type == QueryTokenType.ATTRIBUTE_NAME &&
			   operator.type == QueryTokenType.OPERATOR &&
			   literal.type == QueryTokenType.STRING_LITERAL) {
				
				IFieldValueGetter getter;
				switch (name.content) {
				case "jmbag"   : getter = FieldValueGetters.JMBAG; 		break;
				case "lastName": getter = FieldValueGetters.LAST_NAME;  break;
				case "firstName": getter = FieldValueGetters.FIRST_NAME; break;
				default  	   : throw new QueryParserException();		
				}
				
				IComparisonOperator comparisonOperator;
				switch(operator.content) {
					case "="   : comparisonOperator = ComparisonOperators.EQUALS;			break;
					case "!="  : comparisonOperator = ComparisonOperators.NOT_EQUALS; 	  	break;
					case "<"   : comparisonOperator = ComparisonOperators.LESS; 			break;
					case "<="  : comparisonOperator = ComparisonOperators.LESS_OR_EQUAL;	break;
					case ">"   : comparisonOperator = ComparisonOperators.GREATER; 	      	break;
					case ">="  : comparisonOperator = ComparisonOperators.GREATER_OR_EQUAL;	break;
					case "LIKE": comparisonOperator = ComparisonOperators.LIKE; 		  	break;
					default	   : throw new QueryParserException();
				}

				list.add(new ConditionalExpression(getter, literal.toString(), comparisonOperator));
				i +=3;
				if(i >= array.size() || array.get(i).type != QueryTokenType.AND) {
					break;
				}
				i++;
			}
		}
		if(list.size() != 0) {
			return list;
		}
		throw new QueryParserException("Cannot parse query.");
	}
	
	
	/**
	 * 	Class which describes the exception which
	 *	is thrown when {@link QueryLexer}
	 *	encounters unexpected behavior.
	 */
	public static class QueryLexerException extends RuntimeException{
		
		private static final long serialVersionUID = 1L;
	
		/**
		 *	Constructs a new {@link QueryLexerException} with {@code null}
		 *	as its detail message.
		 */
		public QueryLexerException() {
			super();
		}
	
		/**
		 *	Constructs a new {@link QueryLexerException} with the given 
		 *	message.
		 *	@param message The detail message. 
		 */
		public QueryLexerException(String message) {
			super(message);
		}
	}

	/**
	 * 	Class which describes the exception which
	 *	is thrown when {@link QueryParser}
	 *	encounters unexpected behavior.
	 */
	public class QueryParserException extends RuntimeException{

		private static final long serialVersionUID = 1L;

		/**
		 *	Constructs a new {@link QueryParserException} with {@code null}
		 *	as its detail message.
		 */
		public QueryParserException() {
			super();
		}

		/**
		 *	Constructs a new {@link QueryParserException} with the given 
		 *	message.
		 *	@param message The detail message. 
		 */
		public QueryParserException(String message) {
			super(message);
		}
	}
	
}
