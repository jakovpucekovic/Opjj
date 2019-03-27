package hr.fer.zemris.java.custom.scripting.lexer;

public enum SmartScriptingTokenType {
	EOF,
	TEXT, //allows whitespace inside
	VARIABLE, 
	INTEGER,
	DOUBLE,
	STRING, //slova, brojevi, znakovi, \\ i \"
	FUNCTION, //started with @
	OPERATOR, //+-*/^
	START_TAG,  //{$
	END_TAG,	//$}
	FOR,
	END,
	EQUALS
}
