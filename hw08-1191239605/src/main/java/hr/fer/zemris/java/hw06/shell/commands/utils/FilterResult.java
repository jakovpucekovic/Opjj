package hr.fer.zemris.java.hw06.shell.commands.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *	FilterResult TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FilterResult {

	private Path path;
	private Pattern pat;
	private Matcher match;
	private boolean matches;
	
	public FilterResult(Path path, String pattern) {
		this.path = path;
		//TODO baca exception ako regex nije dobar
		pat = Pattern.compile(pattern, Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		match = pat.matcher(path.getFileName().toString());
		matches = match.matches();	
	}

	public static List<FilterResult> filter(Path dir, String pattern) throws IOException{
		List<FilterResult> list = (Files.list(dir)).filter(Files::isRegularFile).map(x -> new FilterResult(x, pattern)).filter(x -> !x.toString().isBlank()).collect(Collectors.toList());
		/*Sort list by length and then lexicaly*/
		list.sort((a,b) -> {int c = a.toString().length() - b.toString().length(); return c == 0 ? a.toString().compareTo(b.toString()) : c;});
		return list;
	}

	public String toString() {
		return matches ? path.getFileName().toString() : "";
	}
	
	public int numberOfGroups() {
		return matches ? match.groupCount() + 1 : 0;
	}
	
	public String group(int index) {
		return match.group(index); //baca IndexOutOfBounds TODO
	}
	
}
