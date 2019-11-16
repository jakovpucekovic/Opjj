package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link CharsetsShellCommand} which implements a {@link ShellCommand}
 *	and prints a list of all supported {@link Charset}s when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.1
 */
public class CharsetsShellCommand implements ShellCommand {

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link CharsetsShellCommand}.
	 */
	public CharsetsShellCommand() {
		description = new ArrayList<>();
		description.add("Command which prints all available charsets.");
		description.add("Usage: charsets");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print all available {@link Charset}s.
	 * 	@param env The {@link Environment} in which this {@link CharsetCommand} is executed.
	 * 	@param arguments Nothing here.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("This command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		Set<String> charsets = Charset.availableCharsets().keySet();
		charsets.forEach(env::writeln);
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}
	
}
