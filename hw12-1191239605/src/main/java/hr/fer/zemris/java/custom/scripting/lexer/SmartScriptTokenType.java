package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *	Enumeration used to describe the type of tokens which
 *	the {@link SmartScriptLexer} uses.
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public enum SmartScriptTokenType {
	
	/**Token describing end of file.*/
	EOF,
	/**Token describing text with whitespace inside.*/
	TEXT,
	/**Token describing variable.*/
	VARIABLE, 
	/**Token describing integer.*/
	INTEGER,
	/**Token describing double.*/
	DOUBLE,
	/**Token describing string.*/
	STRING, //slova, brojevi, znakovi, \\ i \"
	/**Token describing function name.*/
	FUNCTION, //started with @
	/**Token describing operator symbol.*/
	OPERATOR, //+-*/^
	/**Token describing start tag {$.*/
	START_TAG,  //{$
	/**Token describing end tag $}.*/
	END_TAG,	//$}
	/**Token describing keyword for.*/
	FOR,
	/**Token describing keyword end.*/
	END,
	/**Token describing key symbol =*/
	EQUALS,
	NOW
}
