package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link DropdShellCommand} which implements a {@link ShellCommand}
 *	and removes all saved directories from the stack. 
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class DropdShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link DropdShellCommand}.
	 */
	public DropdShellCommand() {
		description = new ArrayList<>();
		description.add("Command which removes all the saved directories from the stack.");
		description.add("Usage: Dropd");
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("This command doesn't take any arguments.");
			return ShellStatus.CONTINUE;
		}
		
		
		Stack<Path> stack = (Stack<Path>)env.getSharedData("cdstack");
		if(stack == null) {
			env.writeln("The aren't any stored directories.");
			return ShellStatus.CONTINUE;						
		}
		
		stack.clear();
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "dropd";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
