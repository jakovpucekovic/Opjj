package hr.fer.zemris.java.hw05.db.parser.utils;

/**
 *	Enum which lists all the types of the {@link QueryToken}.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public enum QueryTokenType{
	/**Name of an attribute.*/
	ATTRIBUTE_NAME,
	/**<,<=,>,>=,=,!=,LIKE*/
	OPERATOR,
	/**A string literal enclosed in "".*/
	STRING_LITERAL,
	/**Word AND in any casing.*/
	AND
}
