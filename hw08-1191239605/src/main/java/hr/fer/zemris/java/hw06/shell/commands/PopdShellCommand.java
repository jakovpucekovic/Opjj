package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	PophdShellCommand TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class PopdShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link PopdShellCommand}.
	 */
	public PopdShellCommand() {
		description = new ArrayList<>();
		description.add("Command which changes the current working directory to the one which is on top of the stack.");
		description.add("Usage: popd");
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("This command doesn't take any arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path path;
		
		try {
			path =((Stack<Path>)env.getSharedData("cdstack")).pop();
		} catch(EmptyStackException | NullPointerException ex) {
			env.writeln("The aren't any stored directories.");
			return ShellStatus.CONTINUE;			
		}
		
		if(!Files.exists(path)) {
			env.writeln("The saved directory doesn't exist anymore.");
			return ShellStatus.CONTINUE;
		}
		
		new CdShellCommand().executeCommand(env, path.toString());
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "popd";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
