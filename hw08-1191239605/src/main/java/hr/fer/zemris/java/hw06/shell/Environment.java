package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 *	Interface Environment which represents an environment in
 *	which {@link MyShell} operates.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface Environment {

	/**
	 * 	Reads the next line.
	 * 	@return {@link String} containing the read line.
	 * 	@throws ShellIOException If next line can't be read.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * 	Writes the given text without newline at the end.
	 * 	@param text Text to write.
	 * 	@throws ShellIOException If the given text can't be written.
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * 	Writes the given text with a newline character at the end.
	 * 	@param text Text to write.
	 * 	@throws ShellIOException If the given text can't be written.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * 	Returns an unmodifiable {@link SortedMap} which contains all
	 * 	command as values and their names as keys.
	 * 	@return Unmodifiable {@link SortedMap} of commands and their names.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * 	Return the symbol for multiline.
	 * 	@return Symbol for multiline.
	 */
	Character getMultilineSymbol();
	
	/**
	 * 	Sets the multiline symbol.
	 * 	@param The new multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * 	Return the symbol for prompt.
	 * 	@return Symbol for prompt.
	 */
	Character getPromptSymbol();
	
	/**
	 * 	Sets the prompt symbol.
	 * 	@param The new prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * 	Return the symbol for morelines.
	 * 	@return Symbol for morelines.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * 	Sets the morelines symbol.
	 * 	@param The new morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);

}
