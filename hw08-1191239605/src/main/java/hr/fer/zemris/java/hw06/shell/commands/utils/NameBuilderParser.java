package hr.fer.zemris.java.hw06.shell.commands.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *	NameBuilderParser TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class NameBuilderParser {
	
	//TODO ovo ljepse zapisati
	public static NameBuilder text(String t) { return new NameBuilder() {
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			sb.append(t);
			
		}
	}; }
	
	public static NameBuilder group(int index) { return new NameBuilder() {
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			sb.append(result.group(index));
			
		}
	}; }
	
	public static NameBuilder group(int index, char padding, int minWidth) { return new NameBuilder() {
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			for(int i = 0; i < minWidth - result.group(index).length(); ++i) {
				sb.append(padding);
			}
			sb.append(result.group(index));
		}
	};}
	
	public NameBuilder getNameBuilder() {
		return null;
	}
	
	private char[] array;
	private int currentIndex;
	private List<NameBuilder> list;
	
	/**
	 * 	Constructs a new NameBuilderParser.
	 * 	TODO javadoc
	 */
	public NameBuilderParser(String expression) {
		array = expression.strip().toCharArray();
		currentIndex = 0;
		list = new ArrayList<>();
		parse();
	}
	
	private void parse() {		
		while(currentIndex < array.length) {
			parseGroup();
			parseExpression();
		}
	}
	
	
	private void parseExpression() {
		
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < array.length) {
			if(array[currentIndex] == '$' ) {
				break;
			}
		}
		list.add(text(sb.toString()));
	}

	private boolean parseGroup() {
		if(currentIndex + 3 >= array.length) {
			return false;
		}
		
		if(array[currentIndex] != '$' ||
		   array[currentIndex + 1] != '{') {
			return false;
		}
		
		StringBuilder first = new StringBuilder();
		StringBuilder second = new StringBuilder();
		boolean zero = false;
		
		currentIndex +=2;
		eatSpaces();

		//first number
		while(currentIndex < array.length && Character.isDigit(array[currentIndex])){
			first.append(array[currentIndex]);
			++currentIndex;
		}
		
		eatSpaces();
		
		if(currentIndex >= array.length) {
			throw new IllegalArgumentException();//TODO message
		} else if(array[currentIndex] == '}') {
			list.add(group(Integer.parseInt(first.toString())));
			return true;
		//second number
		} else if(array[currentIndex] == ',') {
			eatSpaces();
			if(currentIndex + 2 >= array.length) {
				throw new IllegalArgumentException();
			}
			
			if(array[currentIndex] == '0') {
				zero = true;
			}
			
			while(currentIndex < array.length && Character.isDigit(array[currentIndex])) {
				second.append(array[currentIndex]);
				++currentIndex;
			}
			if(currentIndex < array.length && array[currentIndex] != '}') {
				throw new IllegalArgumentException();
			}
			++currentIndex;
			list.add(group(Integer.parseInt(first.toString()), zero ? '0' : ' ', Integer.parseInt(second.toString())));
			return true;
			
		} else {
			throw new IllegalArgumentException(); //TODO message
		}
	}
	
	
	private void eatSpaces() {
		while(currentIndex < array.length) {
			if(array[currentIndex] != ' ') {
				break;
			}
			++currentIndex;
		}
	}
}
