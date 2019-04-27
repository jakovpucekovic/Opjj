package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	PushdShellCommand TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class PushdShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link PushdShellCommand}.
	 */
	public PushdShellCommand() {
		description = new ArrayList<>();
		description.add("Command which pushes the current working directory on a stack and switches to the given directory.");
		description.add("Usage: pushd path");
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		
		try{
			args = ParserUtil.parse(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args[0].isBlank()) {
			env.writeln("No arguments given.");
			return ShellStatus.CONTINUE;
		}
		if(args[1] != null) {
			env.writeln("This command takes only 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path directory;
		
		try {
			directory = Paths.get(args[0]);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(directory)) {
			env.writeln("Directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(directory)) {
			env.writeln("Given argument is not a directory");
			return ShellStatus.CONTINUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new Stack<Path>());
		}
		((Stack<Path>)env.getSharedData("cdstack")).push(env.getCurrentDirectory());

		new CdShellCommand().executeCommand(env, args[0]);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pushd";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
