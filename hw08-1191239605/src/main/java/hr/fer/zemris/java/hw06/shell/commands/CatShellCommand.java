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
 * 	@version 1.1
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
		
		Path file;
		
		try {
			file = Paths.get(args[0]);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		/*Check if file exists.*/
		if(!Files.exists(file)) {
			env.writeln("File doesn't exist.");
			return ShellStatus.CONTINUE;	
		}
		
		/*Check if file*/
		if(!Files.isRegularFile(file)) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		/*Get charset*/
		Charset charset = Charset.defaultCharset();
		if(args[1] != null) { 
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
