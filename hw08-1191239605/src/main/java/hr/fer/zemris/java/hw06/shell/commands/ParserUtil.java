package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

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
	 *	@return {@link List} of {@link String}s which contains the parsed arguments.
	 *	@throws IllegalArgumentException If the given string has unclosed quotes.
	 */
	public static List<String> parse(String str) {
		
		char[] array = str.strip().toCharArray();
		int currentIndex = 0;
		StringBuilder sb = new StringBuilder();
		boolean insideString = false;		
		List<String> parsedArguments = new ArrayList<>();
		
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
				parsedArguments.add(sb.toString());
				sb.setLength(0);
				
				/*Skip multiple spaces*/
				while(currentIndex + 1 < array.length && array[currentIndex + 1] == ' ') {
					++currentIndex;
				}
				
				++currentIndex;
				continue;
							
			}
			
			sb.append(array[currentIndex]);
			currentIndex++;
			
		}
		
		if(insideString) {
			throw new IllegalArgumentException("Quotes should be closed");
		}
		
		parsedArguments.add(sb.toString());
		
		return parsedArguments;
	}

}
