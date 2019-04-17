package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParserUtil;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link LsCommand} which implements a {@link ShellCommand}
 *	and writes a list of all files and directories to the given {@link Environment}.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LsCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link LsCommand}.
	 */
	public LsCommand() {
		description = new ArrayList<>();
		description.add("Command which prints the information on the directories and files contained in the given directory.");
		description.add("Given informations are flags, size of the files and date and time of creation.");
		description.add("Flag meaning:");
		description.add("	d - directory");
		description.add("	r - readable");
		description.add("	w - writeable");
		description.add("	x - exectuable");
		description.add("Usage: ls directory");
	}
	
	
	/**
	 * 	Executes this {@link ShellCommand} which writes information of the contents of the given directory.
	 * 	@param env The {@link Environment} in which this {@link LsCommand} is executed.
	 * 	@param arguments Path to the directory.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid number of arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		Path directory;
		
		try {
			directory = Paths.get(ParserUtil.parse(arguments));
		} catch (IllegalArgumentException ex1) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		if(!directory.toFile().isDirectory()) {
			env.writeln("Given argument is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {//TODO ne ispisivati trenutni direktorij
			Stream<Path> stream = Files.walk(directory, 1, FileVisitOption.FOLLOW_LINKS);
			stream.sorted((p1,p2)-> p1.compareTo(p2)).map(path -> createPrintableData(path)).forEach(str -> env.writeln(str));
			stream.close();
		} catch (IOException e) {
			throw new ShellIOException("I think i throw"); //TODO
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 *	Private method which creates a row which contains all the information about the 
	 *	file/directory at the given path.
	 *	@param path The directory/file on which we need information.
	 *	@return {@link String} in format "flags size dateOfCreation timeOfCreation name". 
	 */
	private String createPrintableData(Path path) {
		StringBuilder sb = new StringBuilder();
		
		File file = path.toFile();
		sb.append(file.isDirectory() ? 'd' : '-');
		sb.append(file.canRead() 	? 'r' : '-');
		sb.append(file.canWrite()	? 'w' : '-');
		sb.append(file.canExecute()  ? 'x' : '-');
		sb.append(" ");
		
		sb.append(String.format("%10d", file.getTotalSpace()));
		sb.append(" ");
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			 return ""; //TODO
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		sb.append(formattedDateTime);
		sb.append(" ");
		
		sb.append(path.getFileName());
		
		return sb.toString();
	}
	
	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}


}
