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

/**
 *	MassrenameShellCommand TODO javadoc
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
	public MassrenameShellCommand() {//TODO
		description = new ArrayList<>();
		description.add("Command which /");
		description.add("Usage: massrename DIR1 DIR2 CMD MASK other");
		description.add("Valid CMD are: filter, groups, show");
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
		
		if(args.get(0).isBlank() || 
		   args.get(1).isBlank() || 
		   args.get(2).isBlank() || 
		   args.get(3).isBlank()) {//TODO vidi dal to grupirat s gornjom porukom
			env.writeln("No arguments given.");
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
		
		if(!Files.exists(dir1) || !Files.exists(dir2)) {
			env.writeln("Given directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
			env.writeln("Given path isn't a directory.");
			return ShellStatus.CONTINUE;
		}
		
		if(args.get(2).equals("filter")) {
			try {
				List<FilterResult> list = FilterResult.filter(dir1, args.get(3));
				list.stream().map(FilterResult::toString).filter(x -> !x.isBlank()).forEach(env::writeln);
			} catch (IOException e) {
				env.writeln("Error while reading files.");
				return ShellStatus.CONTINUE;
			}
		} else if(args.get(2).equals("groups")) {
			try {
				List<FilterResult> list = FilterResult.filter(dir1, args.get(3));
				list.forEach(new Consumer<FilterResult>() {

					@Override
					public void accept(FilterResult t) {
						/*If string is blank do nothing.*/
						if(t.toString().isBlank()) {
							return;
						}
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
				return ShellStatus.CONTINUE;
			}
		} else if(args.get(2).equals("show")) {
			if(args.size() != 5 || args.get(4).isBlank()) {
				env.writeln("Wrong number of arguments given.");
				return ShellStatus.CONTINUE;
			}
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
