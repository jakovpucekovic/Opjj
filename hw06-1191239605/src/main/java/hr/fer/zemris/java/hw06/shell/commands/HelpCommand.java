package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link HelpCommand} which implements a {@link ShellCommand}
 *	and prints information on supported commands when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class HelpCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link HelpCommand}.
	 */
	public HelpCommand() {
		description = new ArrayList<>();
		description.add("Command which gives information on supported commands.");
		description.add("Usage: help - lists all supported commands");
		description.add("Usage: help command - gives the description of the given command");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print all supported commands or a description
	 * 	of given command.
	 * 	@param env The {@link Environment} in which this {@link HelpCommand} is executed.
	 * 	@param arguments Nothing or name of the command to give the description of.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		/*called without arguments*/
		if(arguments.isBlank()) {
			Set<String> commands = env.commands().keySet();
			env.writeln("Supported commands are: ");
			for(var i : commands) {
				env.writeln("\t" + i);
			}
		} else {
			/*print command info if command exist*/
			if(env.commands().containsKey(arguments)) {
				env.writeln("Description of command " + arguments + ":");
				List<String> commandDescritpion = env.commands().get(arguments).getCommandDescription();
				for(var i : commandDescritpion) {
					env.writeln(i);
				}
			} else {
				env.writeln("Command with name " + arguments + " doesn't exist.");
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}


}
