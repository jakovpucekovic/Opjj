package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class ExitShellCommand which implements {@link ShellCommand}
 *	and allows allows the termination of the {@link MyShell}.
 * 	
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ExitShellCommand implements ShellCommand{
	
	/**Stores the description of the command.*/
	private static List<String> description;
	
	/**
	 *  Constructs a new {@link ExitShellCommand}.
	 */
	public ExitShellCommand() {
		description = new ArrayList<>();
		description.add("By calling this command the program stop and exits.");
		description.add("Usage: exit");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which terminates the shell in the current {@link Environment}.
	 * 	@param env {@link Environment} in which this {@link ExitShellCommand} is executed.
	 * 	@param arguments No arguments.
	 * 	@return {@link ShellStatus} TERMINATE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "exit";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}
	
}
