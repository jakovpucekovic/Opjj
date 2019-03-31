package hr.fer.zemris.java.hw03.prob1;

/**
 *	Enumeration which describes the tokens of which
 *	the {@link Token} is made.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public enum TokenType {
	/**There are no more tokens*/
	EOF,
	/**Word*/
	WORD,
	/**Number*/
	NUMBER,
	/**Symbol*/
	SYMBOL
}
