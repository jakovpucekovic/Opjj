package hr.fer.zemris.java.hw06.shell.commands.utils;

/**
 *	Class which provides some implementations of {@link NameBuilder}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class DefaultNameBuilders {

	/**
	 * 	Returns a {@link NameBuilder} which appends 
	 * 	the given {@link String} to the {@link StringBuilder}.
	 */
	public static NameBuilder text(String txt) {return (FilterResult fr, StringBuilder sb) -> sb.append(txt);};

	/**
	 * 	Returns a {@link NameBuilder} which appends the group with
	 * 	the given index to the {@link StringBuilder}.
	 */
	public static NameBuilder group(int index) {return (FilterResult fr, StringBuilder sb) -> sb.append(fr.group(index));};
	
	/**
	 * 	Returns a {@link NameBuilder} which appends the group with
	 * 	the given index to the {@link StringBuilder} applying the given
	 * 	padding if the length of the group is smaller than the given length.
	 */
	public static NameBuilder group(int index, char padding, int minWidth) { return new NameBuilder() {
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			for(int i = 0; i < minWidth - result.group(index).length(); ++i) {
				sb.append(padding);
			}
			sb.append(result.group(index));
		}
	};}
	
	/**
	 * 	Returns a {@link NameBuilder} which does nothing.
	 */
	public static NameBuilder initialize() {return (FilterResult fr, StringBuilder sb)-> {};};
	
	
}
