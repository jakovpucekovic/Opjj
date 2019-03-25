package hr.fer.zemris.java.hw03.prob1;

/**
 *	Class Token...
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class Token {

	/**	
	 * The type of the {@link Token}. Can be EOF, WORD, NUMBER
	 * 	or SYMBOL.
	 */
	private TokenType type;
	
	/**
	 * 	The value of the {@link Token}.
	 */
	private Object value;
	
	/**
	 *	Constructs a {@link Token} of the given type
	 *	and assigns it the given value.
	 *	@param type Type of the {@link Token}.
	 *	@param value Value to give the {@link Token}.
	 */
	public Token(TokenType type, Object value) {
//dodati provjeru da value odgovara type
		this.type = type;
		this.value = value;
	}

	/**
	 * 	Returns the type of the {@link Token}.
	 * 	@return Type of the {@link Token}.
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * 	Returns the value of the {@link Token}.
	 *	@return Value of the {@link Token}.
	 */
	public Object getValue() {
		return value;
	}

	
	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
	
	




}
