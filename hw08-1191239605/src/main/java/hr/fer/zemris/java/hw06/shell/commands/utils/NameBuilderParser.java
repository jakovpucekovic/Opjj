package hr.fer.zemris.java.hw06.shell.commands.utils;

/**
 *	NameBuilderParser TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class NameBuilderParser {
	
	public static NameBuilder text(String txt) {return (FilterResult fr, StringBuilder sb) -> sb.append(txt);};

	public static NameBuilder group(int index) {return (FilterResult fr, StringBuilder sb) -> sb.append(fr.group(index));};
	
	public static NameBuilder group(int index, char padding, int minWidth) { return new NameBuilder() {
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			for(int i = 0; i < minWidth - result.group(index).length(); ++i) {
				sb.append(padding);
			}
			sb.append(result.group(index));
		}
	};}
	
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
	 */
	public NameBuilderParser(String expression) {
		array = expression.strip().toCharArray();
		currentIndex = 0;
		nb = text("");
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
		nb = nb.then(text(sb.toString()));
	}

	/**
	 * 	Parses a group and adds a group {@link NameBuilder} with the 
	 * 	appropriate arguments to the generated {@link NameBuilder}. 
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
			nb = nb.then(group(Integer.parseInt(first.toString())));
		/*Second number*/
		} else if(array[currentIndex] == ',') {
			StringBuilder second = new StringBuilder();
			/*Flag which tells if the empty spaces should be filled with 0 or " ".*/
			boolean zero = false;
			
			if(currentIndex + 2 >= array.length) {
				throw new IllegalArgumentException("Group isn't closed with }.");
			}
			
			++currentIndex;
			if(array[currentIndex] == '0' && array[currentIndex + 1] != '}') {
				zero = true;
			}
			
			while(currentIndex < array.length && Character.isDigit(array[currentIndex])) {
				second.append(array[currentIndex]);
				++currentIndex;
			}
			if(currentIndex >= array.length|| array[currentIndex] != '}') {
				throw new IllegalArgumentException("Group isn't closed with }.");
			}
			++currentIndex;
			nb = nb.then(group(Integer.parseInt(first.toString()), zero ? '0' : ' ', Integer.parseInt(second.toString())));
			
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
