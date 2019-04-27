package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link TreeShellCommand} which implements a {@link ShellCommand}
 *	and prints a tree structure of the given directory when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.1
 */
public class TreeShellCommand implements ShellCommand{

	/**
	 * 	Private Class which implements {@link FileVisitor} and
	 * 	writes the given structure to the {@link Environment}.
	 */
	private static class TreePrint implements FileVisitor<Path>{
		
		/**Distance from the directory for which we are making the tree.*/
		private int level;
		
		/**{@link Environment} in which we're operating.*/
		private Environment env;
		
		/**
		 * 	Constructs a new {@link TreePrint} which writes to the
		 * 	given {@link Environment}.
		 * 	@param env {@link Environment} to which we write to.
		 */
		public TreePrint(Environment env){
			this.env = env;
		}
		
		/**
		 * 	Writes the name of the directory before visiting it.
		 * 	@param dir {@link Path} to the directory.
		 * 	@param attrs Not used here.
		 * 	@return Signal to continue.
		 * 	@throws IOException Never.
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.write("  ".repeat(level));
			env.writeln(level == 0 ? dir.toAbsolutePath().toString() : dir.getFileName().toString());
			level++;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * 	Writes the name of the file when visiting it.
		 * 	@param file {@link Path} to the file.
		 * 	@param attrs Not used here.
		 * 	@return Signal to continue.
		 * 	@throws IOException Never.
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.write("  ".repeat(level));
			env.writeln(file.getFileName().toString());
			return FileVisitResult.CONTINUE;
		}

		/**
		 * 	Continues in case of failed visit.
		 * 	@param file {@link Path} to the file.
		 * 	@param exc Not used here.
		 * 	@return Signal to continue.
		 * 	@throws IOException Never.
		 */
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * 	Continues after visiting a directory.
		 * 	@param directory {@link Path} to the directory.
		 * 	@param exc Not used here.
		 * 	@return Signal to continue.
		 * 	@throws IOException Never.
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
	}
	
	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link TreeShellCommand}.
	 */
	public TreeShellCommand() {
		description = new ArrayList<>();
		description.add("Command which prints the structure of the given directory in a tree like structure.");
		description.add("Usage: tree directory");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print a tree structure of the given
	 * 	directory.
	 * 	@param env The {@link Environment} in which this {@link TreeShellCommand} is executed.
	 * 	@param arguments Path to the directory.
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
		/*Tree only works on directories*/
		if(!Files.isDirectory(directory)) {
			env.writeln("Given argument is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(directory, new TreePrint(env));
		} catch (IOException e) {
			env.writeln("Error while listing directories occured.");
			return ShellStatus.CONTINUE;
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
