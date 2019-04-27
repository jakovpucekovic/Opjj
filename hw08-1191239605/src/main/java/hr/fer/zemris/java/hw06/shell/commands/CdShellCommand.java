package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	CdShellCommand TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class CdShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link CdShellCommand}.
	 */
	public CdShellCommand() {
		description = new ArrayList<>();
		description.add("Command sets the current directory to the given path.");
		description.add("Usage: cd path");
	}
	
	/**
	 *	{@inheritDoc}
	 */
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
		
		/*Check if directory exists.*/
		if(!Files.exists(directory)) {
			env.writeln("Directory doesn't exist");
			return ShellStatus.CONTINUE;
		}
		
		env.setCurrentDirectory(env.getCurrentDirectory().resolve(directory));
		
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
