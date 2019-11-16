package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *	Enumeration used to describe the type of states in 
 *	which the {@link SmartScriptLexer} can be in.
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public enum SmartScriptLexerState {
	
	/**State in which everything is parsed into text.*/
	TEXT,  
	/**State inside a tag.*/
	IN_TAG
	
	
}
