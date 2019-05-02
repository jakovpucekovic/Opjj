package hr.fer.zemris.java.hw06.shell.commands.utils;

/**
 *	Class which parses an expression and creates an
 *	{@link NameBuilder} which builds the name based on 	
 *	the parsed expression.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class NameBuilderParser {
	
	/**Array that is being parsed.*/
	private char[] array;
	
	/**Current index of parsing.*/
	private int currentIndex;
	
	/**{@link NameBuilder} to generate.*/
	private NameBuilder nb;	
	
	/**
	 *	Returns the generated {@link NameBuilder}.
	 *	@return The generated {@link NameBuilder}. 
	 */
	public NameBuilder getNameBuilder() {
		return nb;
	}
	
	/**
	 * 	Constructs a new NameBuilderParser with the given expression.
	 * 	@param expression Expression to parse.
	 * 	@throws IllegalArgumentException If the expression cannot be parsed.
	 */
	public NameBuilderParser(String expression) {
		array = expression.strip().toCharArray();
		currentIndex = 0;
		nb = DefaultNameBuilders.initialize();
		/*Parses the expression*/
		while(currentIndex < array.length) {
			parseGroup();
			parseText();
		}
	}
	
	/***
	 * 	Parses text and adds a text {@link NameBuilder} with the
	 * 	parsed text to the generated {@link NameBuilder}.
	 */
	private void parseText() {
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < array.length) {
			if(array[currentIndex] == '$' ) {
				break;
			}
			sb.append(array[currentIndex]);
			++currentIndex;
		}
		nb = nb.then(DefaultNameBuilders.text(sb.toString()));
	}

	/**
	 * 	Parses a group and adds a group {@link NameBuilder} with the 
	 * 	appropriate arguments to the generated {@link NameBuilder}. 
	 * 	@throws IllegalArgumentException If the group isn't closed with }.
	 */
	private void parseGroup() {
		/*Group must start with ${*/
		if(array[currentIndex] != '$' ||
		   array[currentIndex + 1] != '{') {
			return;
		}
		
		currentIndex +=2;
		eatSpaces();

		StringBuilder first = new StringBuilder();

		/*First number*/
		while(currentIndex < array.length && Character.isDigit(array[currentIndex])){
			first.append(array[currentIndex]);
			++currentIndex;
		}
		
		eatSpaces();
		
		if(currentIndex >= array.length) {
			throw new IllegalArgumentException("Group isn't closed with }.");
		} else if(array[currentIndex] == '}') {
			++currentIndex;
			nb = nb.then(DefaultNameBuilders.group(Integer.parseInt(first.toString())));
		/*Second number*/
		} else if(array[currentIndex] == ',') {
			
			StringBuilder second = new StringBuilder();
			/*Flag which tells if the empty spaces should be filled with 0 or " ".*/
			boolean zero = false;
			
			if(currentIndex + 2 >= array.length) {
				throw new IllegalArgumentException("Group isn't closed with }.");
			}
			
			++currentIndex;
			eatSpaces();

			if(array[currentIndex] == '0' && array[currentIndex + 1] != '}') {
				zero = true;
			}
			
			while(currentIndex < array.length && Character.isDigit(array[currentIndex])) {
				second.append(array[currentIndex]);
				++currentIndex;
			}
			
			eatSpaces();
			
			if(currentIndex >= array.length|| array[currentIndex] != '}') {
				throw new IllegalArgumentException("Group isn't closed with }.");
			}
			++currentIndex;
			nb = nb.then(DefaultNameBuilders.group(Integer.parseInt(first.toString()), zero ? '0' : ' ', Integer.parseInt(second.toString())));
			
		} else {
			throw new IllegalArgumentException("Group isn't closed with }.");
		}
	}
	
	/**
	 * 	Skips all the spaces.
	 */
	private void eatSpaces() {
		while(currentIndex < array.length) {
			if(array[currentIndex] != ' ') {
				break;
			}
			++currentIndex;
		}
	}
}
