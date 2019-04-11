package hr.fer.zemris.java.hw05.db.parser.utils;

/**
 *	Class which models a token which represents some
 *	grouped characters. 
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class QueryToken{
	
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
	 *	Returns the type of the {@link QueryToken}.
	 *	@return The type of the {@link QueryToken}. 
	 */
	public QueryTokenType getType() {
		return type;
	}

	/**
	 *	Returns the content of the {@link QueryToken}.
	 *	@return The content of the {@link QueryToken}. 
	 */
	public String getContent() {
		return content;
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