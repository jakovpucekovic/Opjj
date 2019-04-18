package hr.fer.zemris.java.hw06.shell;

import java.util.Map;
import java.util.SortedMap;

/**
 *	Class MyShell which represents a functional shell.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class MyShell {
	
	/**The {@link Environment} in which the shell operates.*/
	private static Environment env;
	
	/**{@link Map} of known commands.*/
	private static SortedMap<String, ShellCommand> commands;
	
	/**
	 * 	Method which models the actuall shell.
	 * 	@param args Not used.
	 */
	public static void main(String[] args) {
		env = new MyShellEnvironment();
		commands = env.commands();
		env.writeln("Welcome to MyShell v 1.0 ");
		
		ShellStatus status;
		
		try {
			do {
				String l = readLineOrLines();
				String commandName = l.split("\\s+")[0];
				String arguments = l.substring(commandName.length()).trim();
				if(!commands.containsKey(commandName)) {
					env.writeln("Unknown command.");
					status = ShellStatus.CONTINUE;
					continue;
				}
				ShellCommand command = commands.get(commandName);
				status = command.executeCommand(env, arguments);
			} while(status != ShellStatus.TERMINATE);
		} catch(ShellIOException ex) {
			System.exit(1);
		}
	}
	
	/**
	 * 	Private method which reads the input until it's finished, which
	 * 	means that it will read more than 1 line if the line ends with
	 * 	a valid morelines symbol.
	 * 	@return {@link String} containing the read input with morelines symbol removed.
	 */
	private static String readLineOrLines() {
		StringBuilder fullLine = new StringBuilder();
		env.write(env.getPromptSymbol().toString() + " ");
			
		String line = env.readLine();
		while(line.endsWith(env.getMorelinesSymbol().toString())) {
			fullLine.append(line.substring(0, line.length() - 1));
			env.write(env.getMultilineSymbol().toString() + " ");
			line = env.readLine();
		}	
			
		fullLine.append(line);	
		return fullLine.toString().trim();
	}	
	
	
}
