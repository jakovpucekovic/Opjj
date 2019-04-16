package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class CharsetsCommand.
 * 	TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Set<String> charsets = Charset.availableCharsets().keySet();
		for(var i : charsets) {
			env.writeln(i);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
