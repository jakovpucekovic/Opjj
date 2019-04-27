package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
 *	Class {@link CopyShellCommand} which implements a {@link ShellCommand}
 *	and copies the given file to the specified directory when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.1
 */
public class CopyShellCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link CopyShellCommand}.
	 */
	public CopyShellCommand() {
		description = new ArrayList<>();
		description.add("Command which makes a copy of the given file.");
		description.add("Usage: copy file directory - makes a copy of file1 in directory");
		description.add("Usage: copy file1 file2 - makes a copy of file1 named file2");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which copies the given file.
	 * 	@param env The {@link Environment} in which this {@link CopyShellCommand} is executed.
	 * 	@param arguments String containing path to the file and directory or filename of where to copy.
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
		
		if(args[1] == null) {
			env.writeln("This command takes 2 arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path whatToCopy;
		Path whereToCopy;
		
		/*Parse arguments and get Paths*/
		try {
			whatToCopy = Paths.get(args[0]);
			whereToCopy = Paths.get(args[1]);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		/*Check if file exists.*/
		if(!Files.exists(whatToCopy)) {
			env.writeln("File doesn't exist.");
			return ShellStatus.CONTINUE;	
		}
		
		/*If whatToCopy is not a file*/
		if(!Files.isRegularFile(whatToCopy)) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		/*If where to copy is a directory add the file name from whatToCopy*/
		if(Files.isDirectory(whereToCopy)) {
			whereToCopy = Paths.get(whereToCopy.toString(), whatToCopy.getFileName().toString());			
		}

		/*Cannot copy to itself*/
		if(Files.exists(whereToCopy)) {
			try {
				if(whereToCopy.toRealPath().equals(whatToCopy.toRealPath())) {
					env.writeln("Cannot copy \"" + whatToCopy.toString() + "\" to itself.");
					return ShellStatus.CONTINUE;
				}
			} catch (IOException e1) {
				env.writeln("Cannot resolve paths.");
				return ShellStatus.CONTINUE;
			}
		}
		
		/*If file already exists ask for further instructions*/
		if(Files.exists(whereToCopy)) {
			env.writeln("File exists, do you want to overwrite it (Y/n) ?");
			env.write(env.getPromptSymbol() + " ");
			String answer = env.readLine().toLowerCase();
			if(!answer.equals("n") && !answer.equals("y") && !answer.equals("")) {
				env.writeln("Invalid argument recieved.");
				return ShellStatus.CONTINUE;
			} else if(answer.equals("n")){
				env.writeln("Copy aborted.");
				return ShellStatus.CONTINUE;
			}
		}
			
		/*Read from one file and write to the other.*/
		try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(whatToCopy));
			BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(whereToCopy))){
			byte[] buffer = new byte[1024];
			int dataRead = bis.read(buffer, 0, 1024);
			while(dataRead != -1) {
				bos.write(buffer, 0, dataRead);
				dataRead = bis.read(buffer, 0, 1024);
			}
		} catch (IOException e) {
			env.writeln("Cannot read or write files.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}
	
	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
