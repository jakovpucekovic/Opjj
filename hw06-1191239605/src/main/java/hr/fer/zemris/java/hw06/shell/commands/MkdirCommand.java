package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class MyShellCommand.
 * 	TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class MkdirCommand implements ShellCommand{

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid argument given.");
			return ShellStatus.CONTINUE;
		}
		
		
		Path directory;
		
		try {
			directory = Paths.get(arguments);
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(directory);
		} catch (Exception e) {
			env.writeln("Directory cannot be created.");
		}	
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}


}
