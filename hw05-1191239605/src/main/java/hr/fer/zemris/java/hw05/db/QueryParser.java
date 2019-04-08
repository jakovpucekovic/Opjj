package hr.fer.zemris.java.hw05.db;

import java.util.Iterator;

public class QueryParser {

	private enum QueryTokenType{
		COMMAND,
		ATTRIBUTE_NAME,
		OPERATOR,
		STRING_LITERAL,
		AND
	}
	
	private static class QueryToken{
		
		QueryTokenType type;
		String content;
		
		public QueryToken(QueryTokenType type, String content) {
			this.type = type;
			this.content = content;
		}

		@Override
		public String toString() {
			return "QueryToken [type=" + type + ", content=" + content + "]";
		}	
		
		
	}
	
	public QueryLexer getLexer(String s) {
		return new QueryLexer(s);
	}
	
	public static class QueryLexer{
		
		private char[] input;
		private int index;
		private QueryToken token;
		
		public QueryLexer(String text) {
			input = text.toCharArray();
			index = -1;
		}
		
		public QueryToken getToken() {
			return token;
		}
		
		public boolean hasNext() {
			return index + 1 <= input.length;
		}
		
		public QueryToken nextToken() {
			checkIndex(1);
			index++;
			eatSpaces();
			if(groupQuery()) {
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
		
		private boolean groupQuery() {
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
			return false;
		}
		
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
		
		private void eatSpaces() {
			while(Character.isWhitespace(input[index])) {
				checkIndex(1);
				index++;
			}	
		}
		
		private void checkIndex(int i) {
			if(index + i >= input.length) {
				throw new RuntimeException("No more input");
			}
		}
	}
	
	
	
}
