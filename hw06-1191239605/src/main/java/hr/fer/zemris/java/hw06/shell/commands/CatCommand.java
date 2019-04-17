package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link CatCommand} which implements a {@link ShellCommand}
 *	and prints content of the given file when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CatCommand implements ShellCommand{

	
	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link TreeCommand}.
	 */
	public CatCommand() {
		description = new ArrayList<>();
		description.add("Command which prints contents of the given file.");
		description.add("Usage: cat file charset(optional)");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print a tree structure of the given
	 * 	directory.
	 * 	@param env The {@link Environment} in which this {@link CatCommand} is executed.
	 * 	@param arguments String containing path to the file and {@link Charset} with which the file should be read.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		/*Must get 2 arguments*/
		String[] args = arguments.split("\\s+");
		if(args.length  != 1 && args.length != 2) {
			env.writeln("Wrong number of arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		Path file;
		
		try {
			file = Paths.get(arguments);
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		/*Check if directory*/
		if(file.toFile().isDirectory()) {
			env.writeln("Given argument is a directory.");
			return ShellStatus.CONTINUE;
		}
		
		/*Get charset*/
		Charset charset = Charset.defaultCharset();
		if(args.length == 2 && Charset.isSupported(args[1])) {
			charset = Charset.forName(args[1]);
		}
		
		/*Read from file and write to screen*/
		try (BufferedReader br = Files.newBufferedReader(file, charset)){
			String toWrite = br.readLine();
			while(toWrite != null) {
				env.writeln(toWrite);
				toWrite = br.readLine();
			}
		} catch (IOException e) {
			throw new ShellIOException("I think i throw"); //TODO
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}

	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
