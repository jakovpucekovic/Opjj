package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link ListdShellCommand} which implements a {@link ShellCommand}
 *	and writes a list of all directories stored on the stack to the given 
 *	{@link Environment}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class ListdShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link ListdShellCommand}.
	 */
	public ListdShellCommand() {
		description = new ArrayList<>();
		description.add("Command which prints all the paths saved on the stack.");
		description.add("Usage: listd");
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
		if(stack == null || stack.isEmpty()) {
			env.writeln("The aren't any stored directories.");
			return ShellStatus.CONTINUE;						
		}
		
		/*Write in descending order.*/
		for(int i = stack.size() - 1; i >=0; --i) {
			env.writeln(stack.get(i).toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "listd";
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
