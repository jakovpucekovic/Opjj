package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link MkdirShellCommand} which implements a {@link ShellCommand}
 *	and creates the given directory structure when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class MkdirShellCommand implements ShellCommand{
	
	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link MkdirShellCommand}.
	 */
	public MkdirShellCommand() {
		description = new ArrayList<>();
		description.add("Command which creates the given directory structure.");
		description.add("Usage: mkdir directory");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print a creates the given directory structure.
	 * 	@param env The {@link Environment} in which this {@link MkdirShellCommand} is executed.
	 * 	@param arguments Path to the directory.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		/*Check if arguments is blank*/
		if(arguments.isBlank()) {
			env.writeln("No path given.");
			return ShellStatus.CONTINUE;
		}
		
		Path directory;
		
		try {
			directory = Paths.get(ParserUtil.parse(arguments));
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}
		
		/*Checks if directory already exists*/
		if(directory.toFile().exists()) {
			env.writeln("Directory already exists.");
			return ShellStatus.CONTINUE;
		}
		
		/*Create directories*/
		try {
			Files.createDirectories(directory);
		} catch (Exception e) {
			env.writeln("Directory cannot be created.");
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}
	
	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
