package hr.fer.zemris.java.hw06.shell.commands;

/**
 *	Class ParserUtil which is used to parse paths.
 *	Allows a path to be in quotation marks "", but after
 *	quotation marks must come a whitespace.
 * 	
 * 	@author Jakov Pucekovic
 * 	@version 2.0
 */
public class ParserUtil {
		
	
	/**
	 *	Method which does the parsing of arguments
	 *	@param str {@link String} which should be parsed.
	 *	@return {@link String} array which contains the parsed arguments.
	 *	@throws IllegalArgumentException If the given string contains more than 2 
	 *									 arguments or has unclosed quotes.
	 */
	public static String[] parse(String str) {
		String[] pathAndOther = new String[2];	
		
		char[] array = str.strip().toCharArray();
		int currentIndex = 0;
		StringBuilder sb = new StringBuilder();
		boolean insideString = false;
		boolean pathFinished = false;
		
		while(currentIndex < array.length) {
			/*If inside of quotation marks.*/
			if(insideString) {
				/*Exit quotation marks.*/
				if(array[currentIndex] == '\"') {
					insideString = false;
					currentIndex++;
					continue;
					
				/*Escaping inside quotation marks.*/
				} else if(array[currentIndex] == '\\' && currentIndex + 1 < array.length) {
					if(array[currentIndex + 1] == '\\' || array[currentIndex + 1] == '\"') {
						sb.append(array[currentIndex + 1]);
						currentIndex += 2;
					} else {
						sb.append(array[currentIndex]);
						++currentIndex;
					}
					continue;
				}
				
				sb.append(array[currentIndex]);
				++currentIndex;
				continue;
			}
			
			/*Go inside quotation marks.*/
			if(array[currentIndex] == '\"') {
				insideString = true;
				currentIndex++;
				continue;
			}
			
			if(array[currentIndex] == ' ') {
				/*Everything up to the first space is path.*/
				if(!pathFinished) {
					pathFinished = true;
					pathAndOther[0] = sb.toString();
					sb.setLength(0);

					/*Skip multiple spaces*/
					while(currentIndex + 1 < array.length && array[currentIndex + 1] == ' ') {
						++currentIndex;
					}
					++currentIndex;
					continue;
					
				}
				throw new IllegalArgumentException("Cannot have more than 2 arguments.");
			}
			
			sb.append(array[currentIndex]);
			currentIndex++;
			
		}
		
		if(insideString) {
			throw new IllegalArgumentException("Quotes should be closed");
		}
		
		/*If there was a space, sb contains the second argument.*/
		if(!pathFinished) {
			pathAndOther[0] = sb.toString();
		} else {
			pathAndOther[1] = sb.toString();
		}
		
		
		return pathAndOther;
	}

}
