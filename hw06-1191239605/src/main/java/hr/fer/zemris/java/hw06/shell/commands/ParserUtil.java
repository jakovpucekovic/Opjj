package hr.fer.zemris.java.hw06.shell.commands;

/**
 *	Class ParserUtil which is used to parse paths.
 *	Allows a path to be in quotation marks "", but after
 *	quotation marks must come a whitespace.
 * 	
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ParserUtil {
		
	
	/**
	 *	Method which does the parsing of the path.
	 *	@param str {@link String} which should be parsed.
	 *	@return {@link String} which contains the parsed path.
	 *	@throws IllegalArgumentException If the path cannot be parsed.
	 */
	public static String parse(String str) {
		char[] array = str.toCharArray();
		int currentIndex = 0;
		StringBuilder sb = new StringBuilder();
		boolean insideString = false;
		
		while(currentIndex < array.length) {
			/*If inside of quotation marks.*/
			if(insideString) {
				/*Exit quotation marks.*/
				if(array[currentIndex] == '\"') {
					insideString = false;
					currentIndex++;
					/*Check if there is whitespace after exiting quotation marks.*/
					if(currentIndex < array.length) {
						if(!Character.isWhitespace(array[currentIndex])) {
							throw new IllegalArgumentException("After quotes should be a whitespace");
						}
					}
					continue;
				/*Escaping inside quotation marks.*/
				} else if(array[currentIndex] == '\\' && currentIndex + 1 < array.length) {
					currentIndex++;
					continue;
				}
			}
			
			/*Go inside quotation marks.*/
			if(array[currentIndex] == '\"') {
				insideString = true;
				currentIndex++;
				continue;
			}
			
			sb.append(array[currentIndex]);
			currentIndex++;
			
		}
		if(insideString) {
			throw new IllegalArgumentException("Quotes should be closed");
		}
		
		return sb.toString();
	}

}
