package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 *	Interface ShellCommand. //TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 */
public interface ShellCommand {

	ShellStatus executeCommand(Environment env, String arguments);
	
	String getCommandName();
	
	List<String> getCommandDescription();

}
