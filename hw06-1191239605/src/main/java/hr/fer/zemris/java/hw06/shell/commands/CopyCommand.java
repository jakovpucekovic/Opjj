package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipOutputStream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParserUtil;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link CopyCommand} which implements a {@link ShellCommand}
 *	and copies the given file to the specified directory when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CopyCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link CopyCommand}.
	 */
	public CopyCommand() {
		description = new ArrayList<>();
		description.add("Command which makes a copy of the given file.");
		description.add("Usage: copy file directory - makes a copy of file1 in directory");
		description.add("Usage: copy file1 file2 - makes a copy of file1 named file2");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which copies the given file.
	 * 	@param env The {@link Environment} in which this {@link CopyCommand} is executed.
	 * 	@param arguments String containing path to the file and directory or filename of where to copy.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid number of arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		String[] args = arguments.split("\\s+");
		if(args.length != 1 && args.length != 2) {
			env.writeln("Invalid number of arguments given.");
			return ShellStatus.CONTINUE;	
		}
		
		Path whatToCopy;
		Path whereToCopy;
		
		try {
			whatToCopy = Paths.get(ParserUtil.parse(args[0]));
			whereToCopy = Paths.get(ParserUtil.parse(args[1]));
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		if(!whatToCopy.toFile().isFile()) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		if(whereToCopy.toFile().isDirectory()) {
			whereToCopy = Path.of(whereToCopy.toString() + whatToCopy.getFileName());			
		}
		
//		try {
//			Files.createFile(whereToCopy);
//		} catch (IOException e) {
//			throw new ShellIOException("I think i throw"); //TODO
//		}	
		
		try {
			InputStream is = Files.newInputStream(whatToCopy);
			OutputStream os = Files.newOutputStream(whereToCopy);//vidi za transferTo TODO
			byte[] buffer = new byte[4000];
			int dataRead = is.read(buffer, 0, 4000);
			while(dataRead != -1) {
				os.write(buffer, 0, dataRead);
				dataRead = is.read(buffer, 0, 4000);
			}
			is.close();
			os.close();
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
		return "copy";
	}

	
	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}


}
