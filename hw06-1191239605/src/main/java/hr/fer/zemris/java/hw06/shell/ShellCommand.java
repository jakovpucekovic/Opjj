package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 *	Interface which represents a command which can be executed
 *	in the chosen {@link Environment} with the given parameters.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface ShellCommand {

	
	/**
	 * 	Executes this {@link ShellCommand}.
	 * 	@param env The {@link Environment} in which the {@link ShellCommand} is executed.
	 * 	@param arguments A {@link String} containing the arguments which this command takes.
	 * 					 Arguments should be divided by spaces.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	
	/**
	 * 	Returns the name of this {@link ShellCommand}.
	 * 	@return The name of this {@link ShellCommand}.
	 */
	String getCommandName();
	
	
	/**
	 * 	Returns a read-only {@link List} of {@link String} which 
	 * 	contains the detailed description of what this {@link ShellCommand}
	 * 	does and how to use it.
	 * 	@return Read-only {@link List} of {@link String} containing the description.
	 */
	List<String> getCommandDescription();
}
