package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.utils.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.utils.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.utils.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.commands.utils.ParserUtil;

/**
 *	Class {@link MassrenameShellCommand} which implements a {@link ShellCommand}
 *	and copies/renames all files which satisfy the given regular expression from 
 *	a given directory to another directory.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class MassrenameShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link MassrenameShellCommand}.
	 */
	public MassrenameShellCommand() {
		description = new ArrayList<>();
		description.add("Command which is used to copy/rename more files at once.");
		description.add("Usage: massrename DIR1 DIR2 CMD MASK other");
		description.add("Valid commands are:");
		description.add("\tfilter - prints all the files that satisfy the given MASK");
		description.add("\tgroups - prints all the files that satisfy the given MASK with all their groups");
		description.add("\tshow - prints a preview of renamed/moved files");
		description.add("\texecute - does the copying/renaming as previewed in show command ");
		description.add("Commands show and execute expect a renaming expression argument.");
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = ParserUtil.parse(arguments);
		
		if(args.size() != 4 && args.size() != 5) {
			env.writeln("This command expects 4 or 5 arguments.");
			return ShellStatus.CONTINUE;
		}
		
		/*Because ParserUtil.parse returns "" as an element in List<String>*/
		if(args.get(0).isBlank() || 
		   args.get(1).isBlank() || 
		   args.get(2).isBlank() || 
		   args.get(3).isBlank()) {
			env.writeln("This command expects 4 or 5 arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path dir1;
		Path dir2;
		
		try {
			dir1 = Paths.get(args.get(0));
			dir2 = Paths.get(args.get(1));
		} catch(InvalidPathException ex) {
			env.writeln("Invalid path given");
			return ShellStatus.CONTINUE;
		}
		
		dir1 = env.getCurrentDirectory().resolve(dir1);
		dir2 = env.getCurrentDirectory().resolve(dir2);
		
		/*Check if directories exist.*/
		if(!Files.exists(dir1) || !Files.exists(dir2)) {
			env.writeln("Given directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		/*Check if directories are actually directories.*/
		if(!Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
			env.writeln("Given path isn't a directory.");
			return ShellStatus.CONTINUE;
		}
		
		if(args.get(2).equals("filter")) {
			return filter(dir1, dir2, env, args.get(3));
		} else if(args.get(2).equals("groups")) {
			return groups(dir1, dir2, env, args.get(3));
		} else if(args.get(2).equals("show")) {
			if(args.size() != 5 || args.get(4).isBlank()) {
				env.writeln("Renaming expression not given.");
				return ShellStatus.CONTINUE;
			}
			
			return show(dir1, dir2, env, args.get(3), args.get(4));
		} else if(args.get(2).equals("execute")) {
			if(args.size() != 5 || args.get(4).isBlank()) {
				env.writeln("Renaming expression not given.");
				return ShellStatus.CONTINUE;
			}
		
			return execute(dir1, dir2, env, args.get(3), args.get(4));
		} else {
			env.writeln("Invalid command given.");
			return ShellStatus.CONTINUE;
		}
		
	}

	/**
	 * 	Private method which executes the command filter
	 * 	which prints all the files that satisfy the given
	 * 	MASK.
	 * 	@param dir1 Directory from which the files are read.
	 * 	@param dir2 Directory to which the files are moved.	
	 *	@param env {@link Environment} in which we are working.
	 *	@param reg Regular expression for selection of files.
	 *	@return {@link ShellStatus} continue;
	 */
	private ShellStatus filter(Path dir1, Path dir2, Environment env, String reg) {
		try {
			List<FilterResult> list = FilterResult.filter(dir1, reg);
			list.stream().map(FilterResult::toString).forEach(env::writeln);
		} catch (IOException e) {
			env.writeln("Error while reading files.");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * 	Private method which executes the command groups
	 * 	which print all the files that satisfy the given 
	 * 	MASK and all it's groups.
	 * 	@param dir1 Directory from which the files are read.
	 * 	@param dir2 Directory to which the files are moved.	
	 *	@param env {@link Environment} in which we are working.
	 *	@param reg Regular expression for selection of files.
	 *	@param expression Expression which describes how to rename the files. 
	 *	@return {@link ShellStatus} continue;
	 */
	private ShellStatus groups(Path dir1, Path dir2, Environment env, String reg) {
		try {
			List<FilterResult> list = FilterResult.filter(dir1, reg);
			list.forEach(new Consumer<FilterResult>() {

				@Override
				public void accept(FilterResult t) {
				
					/*Print all groups*/
					StringBuilder sb = new StringBuilder(t.toString());
					for(int i = 0; i < t.numberOfGroups(); ++i) {
						sb.append(" ");
						sb.append(i);
						sb.append(": ");
						sb.append(t.group(i));
					}
					env.writeln(sb.toString());
				}
				
			});
		} catch (IOException e) {
			env.writeln("Error while reading files.");
		}
		return ShellStatus.CONTINUE;
	}
	
	
	/**
	 * 	Private method which executes the command show which
	 * 	prints the preview of renaming the files which satisfy
	 * 	the given MASK.
	 * 	@param dir1 Directory from which the files are read.
	 * 	@param dir2 Directory to which the files are moved.	
	 *	@param env {@link Environment} in which we are working.
	 *	@param reg Regular expression for selection of files.
	 *	@param expression Expression which describes how to rename the files. 
	 *	@return {@link ShellStatus} continue;
	 */
	private ShellStatus show(Path dir1, Path dir2, Environment env, String reg, String expression) {
		try {
			List<FilterResult> files = FilterResult.filter(dir1, reg);
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder builder = parser.getNameBuilder();
			for(FilterResult file: files) {
				StringBuilder sb = new StringBuilder();
				builder.execute(file, sb);
				String newName = sb.toString();
		
				env.writeln(file.toString() + " => " + newName);
			}
			
		} catch (IOException e) {
			env.writeln("Error while reading files.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	Private method which executes the command execute
	 * 	which moves/renames the files which satisfy the given MASK.
	 * 	@param dir1 Directory from which the files are read.
	 * 	@param dir2 Directory to which the files are moved.	
	 *	@param env {@link Environment} in which we are working.
	 *	@param reg Regular expression for selection of files.
	 *	@param expression Expression which describes how to rename the files. 
	 *	@return {@link ShellStatus} continue;
	 */
	private ShellStatus execute(Path dir1, Path dir2, Environment env, String reg, String expression) {
		try {
			List<FilterResult> files = FilterResult.filter(dir1, reg);
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder builder = parser.getNameBuilder();
			for(FilterResult file: files) {
				StringBuilder sb = new StringBuilder();
				builder.execute(file, sb);
				String newName = sb.toString();
				Path resolved1 = dir1.resolve(file.toString());
				Path resolved2 = dir2.resolve(newName);
				env.writeln(resolved1.toString() + " => " + resolved2.toString());
				Files.move(resolved1, resolved2);
			}
			
		} catch (IOException e) {
			env.writeln("Error while reading files.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "massrename";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
