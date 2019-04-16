package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class MyShellCommand.
 * 	TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class HelpCommand implements ShellCommand{

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			Set<String> commands = env.commands().keySet();
			env.writeln("Supported commands are: ");
			for(var i : commands) {
				env.writeln(i);
			}
		} else {
			if(env.commands().containsKey(arguments)) {
				env.writeln("Description of command " + arguments);
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

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}


}
