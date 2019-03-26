package hr.fer.zemris.java.custom.scripting.lexer;

public enum SmartScriptingTokenType {
	EOF,
	WORD,	//doesnt allow whitespace
	NUMBER,
	SYMBOL,
	TEXT, //allows whitespace inside
	MODE_SWITCHER
}
