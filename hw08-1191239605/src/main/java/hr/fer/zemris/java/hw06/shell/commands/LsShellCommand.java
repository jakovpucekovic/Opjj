package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link LsShellCommand} which implements a {@link ShellCommand}
 *	and writes a list of all files and directories to the given {@link Environment}.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.1
 */
public class LsShellCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link LsShellCommand}.
	 */
	public LsShellCommand() {
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
	 * 	@param env The {@link Environment} in which this {@link LsShellCommand} is executed.
	 * 	@param arguments Path to the directory.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
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
		} catch (IllegalArgumentException ex1) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		directory = env.getCurrentDirectory().resolve(directory);
		
		/*Check if directory exists.*/
		if(!Files.exists(directory)) {
			env.writeln("Directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		/*Ls only works on directories*/
		if(!Files.isDirectory(directory)) {
			env.writeln("Given argument is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try(Stream<Path> stream = Files.list(directory)){
			List<Path> list = stream.sorted((p1,p2)-> p1.compareTo(p2)).collect(Collectors.toList());
			for(var i : list) {
				env.writeln(createPrintableData(i));
			}
		} catch (IOException ex) {
			env.writeln("Files can't be read.");
			return ShellStatus.CONTINUE;
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 *	Private method which creates a row which contains all the information about the 
	 *	file/directory at the given path.
	 *	@param path The directory/file on which we need information.
	 *	@return {@link String} in format "flags size dateOfCreation timeOfCreation name". 
	 * 	@throws IOException If some data can't be read.
	 */
	private String createPrintableData(Path path) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		/*Get flags*/
		File file = path.toFile();
		sb.append(file.isDirectory() ? 'd' : '-');
		sb.append(file.canRead() 	? 'r' : '-');
		sb.append(file.canWrite()	? 'w' : '-');
		sb.append(file.canExecute()  ? 'x' : '-');
		sb.append(" ");
		
		/*Get size*/
		sb.append(String.format("%10d", file.length()));
		sb.append(" ");
		
		/*Get date and time*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes;
		attributes = faView.readAttributes();
		
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		sb.append(formattedDateTime);
		sb.append(" ");
		
		/*Get name*/
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
		return Collections.unmodifiableList(description);
	}

}
