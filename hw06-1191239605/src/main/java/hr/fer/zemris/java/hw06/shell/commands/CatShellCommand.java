package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
 *	Class {@link CatShellCommand} which implements a {@link ShellCommand}
 *	and prints content of the given file when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CatShellCommand implements ShellCommand{

	
	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link TreeShellCommand}.
	 */
	public CatShellCommand() {
		description = new ArrayList<>();
		description.add("Command which prints contents of the given file.");
		description.add("Usage: cat file charset(optional)");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print a tree structure of the given
	 * 	directory.
	 * 	@param env The {@link Environment} in which this {@link CatShellCommand} is executed.
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
			file = Paths.get(ParserUtil.parse(args[0]));
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		/*Check if file*/
		if(!file.toFile().isFile()) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		/*Get charset*/
		Charset charset = Charset.defaultCharset();
		if(args.length == 2) { 
			if(Charset.isSupported(args[1])) {
				charset = Charset.forName(args[1]);
			} else {
				env.writeln("Given charset isn't supported");
				return ShellStatus.CONTINUE;
			}
		}

		/*Read from file and write to screen*/
		try (BufferedReader br = new BufferedReader(
								new InputStreamReader(
								new BufferedInputStream(Files.newInputStream(file)), charset))){
			String toWrite = br.readLine();
			while(toWrite != null) {
				env.writeln(toWrite);
				toWrite = br.readLine();
			}
		} catch (IOException ex) {
			env.writeln("Cannot read file.");
			return ShellStatus.CONTINUE;
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
		return Collections.unmodifiableList(description);
	}

}
