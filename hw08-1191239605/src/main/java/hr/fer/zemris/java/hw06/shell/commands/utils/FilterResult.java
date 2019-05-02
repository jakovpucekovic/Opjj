package hr.fer.zemris.java.hw06.shell.commands.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

/**
 *	Class which is used for filtering out {@link Path}s
 *	which do not match with the given regular expression.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class FilterResult {

	
	/**Path which is matched.*/
	private Path path;

	/**Pattern to match.*/
	private Pattern pat;
	
	/**Matcher which does the matching.*/
	private Matcher match;
	
	/**Flag which tells whether the file name matches the pattern.*/
	private boolean matches;
	
	/**
	 *	Constructs a new {@link FilterResult} with the given 
	 *	{@link Path} and pattern to match.
	 *	@param path {@link Path} to the file.
	 *	@param pattern Regular expression to match.
	 *	@throws IllegalArgumentException If the given pattern is not valid. 
	 */
	public FilterResult(Path path, String pattern) {
		this.path = path;
		try {
		pat = Pattern.compile(pattern, Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		} catch(PatternSyntaxException ex) {
			throw new IllegalArgumentException("Given pattern is not valid");
		}
		match = pat.matcher(path.getFileName().toString());
		matches = match.matches();
	}

	/**
	 * 	Walks through the given directory and returns a {@link List}
	 * 	of {@link FilterResult}s which match the given pattern.
	 * 	@param dir {@link Path} to a directory.
	 * 	@param pattern Regular expression to match.
	 * 	@throws IllegalArgumentException If the given path is not a 
	 * 									 directory or the pattern is not valid.
	 * 	@throws IOException If files can't be read.
	 */
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException{
		if(!Files.isDirectory(dir)) {
			throw new IllegalArgumentException("Given path is not a directory.");
		}
		List<FilterResult> list = (Files.list(dir)).filter(Files::isRegularFile).map(x -> new FilterResult(x, pattern)).filter(x -> !x.toString().isBlank()).collect(Collectors.toList());
		/*Sort list by length and then lexicaly*/
		list.sort((a,b) -> {int c = a.toString().length() - b.toString().length(); return c == 0 ? a.toString().compareTo(b.toString()) : c;});
		return list;
	}

	/**
	 * 	Returns the filename stored in the {@link FilterResult}.
	 * 	@return {@link String} containing the filename.
	 */
	@Override
	public String toString() {
		return matches ? path.getFileName().toString() : "";
	}
	
	/**
	 * 	Returns the number of groups which match the given filename.
	 * 	@param Number of groups which match the given filename.
	 */
	public int numberOfGroups() {
		return matches ? match.groupCount() + 1 : 0;
	}
	
	/**
	 *	Returns the group which at the given index.
	 *	@param index The index of the group to return.
	 *	@return The group at the given index.
	 *	@throws IndexOutOfBoundsException If the given index is < 0 or
	 *									  > numberOfGroups()
	 */
	public String group(int index) {
		return match.group(index);
	}
	
}
