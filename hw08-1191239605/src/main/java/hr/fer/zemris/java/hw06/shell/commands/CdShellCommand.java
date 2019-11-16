package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.utils.ParserUtil;

/**
 *	Class {@link CdShellCommand} which implements a {@link ShellCommand}
 *	and sets the working directory to the given directory.
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
		description.add("Command sets the current directory to the given directory.");
		description.add("Usage: cd path");
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		
		try{
			args = ParserUtil.parse(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.size() > 1) {
			env.writeln("This command takes only 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		if(args.get(0).isBlank()) {
			env.writeln("No arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		Path directory;
		
		try {
			directory = Paths.get(args.get(0));
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		directory = env.getCurrentDirectory().resolve(directory);
		
		try {
			env.setCurrentDirectory(directory);
		} catch(IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
		}
		
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
