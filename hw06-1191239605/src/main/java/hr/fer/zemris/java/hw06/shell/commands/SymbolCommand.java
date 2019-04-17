package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link SymbolCommand} which implements a {@link ShellCommand}
 *	and prints and sets special shell symbols when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class SymbolCommand implements ShellCommand{

	
	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link SymbolCommand}.
	 */
	public SymbolCommand() {
		description = new ArrayList<>();
		description.add("Command which prints and sets shell symbols.");
		description.add("Usage: symbol symbolName - prints the current symbol for the symbolName");
		description.add("Usage: symbol symbolName newSymbol - sets the newSymbol for the symbolName");
		description.add("Supported symbol names: PROMPT, MULTILINE, MORELINES");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which prints and sets shell symbols.
	 * 	@param env The {@link Environment} in which this {@link SymbolCommand} is executed.
	 * 	@param arguments {@link String} containing name of symbol and new symbol if it should be changed.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] inputArray = arguments.trim().split("\\s+");
		if(inputArray.length > 2) {
			env.writeln("Wrong symbol command.");
			return ShellStatus.CONTINUE;
		}

		/*Get stored symbol*/
		char symbol;
		switch (inputArray[0]) {
			case "PROMPT" : {
				symbol = env.getPromptSymbol();
				break;
			}
			case "MULTILINE" : {
				symbol = env.getMultilineSymbol();
				break;
			}
			case "MORELINES" : {
				symbol = env.getMorelinesSymbol();
				break;
			}
			default : {
				env.writeln("Unknown symbol name.");
				return ShellStatus.CONTINUE;
			}	
		}
		
		if(inputArray.length == 1) {
			env.writeln("Symbol for " + inputArray[0] + " is \'" + symbol +"\'.");
		} else if(inputArray.length == 2 && inputArray[1].length() == 1) {
			env.writeln("Symbol for " + inputArray[0] + " changed from \'" + symbol +"\' to \'" + inputArray[1] +"\'.");
			/*Set symbol.*/
			switch (inputArray[0]) {
				case "PROMPT" : {
					env.setPromptSymbol(inputArray[1].charAt(0));
					break;
				}
				case "MULTILINE" : {
					env.setMultilineSymbol(inputArray[1].charAt(0));
					break;
				}
				case "MORELINES" : {
					env.setMorelinesSymbol(inputArray[1].charAt(0));
					break;
				}
				default : {
					env.writeln("Unknown symbol name.");
					return ShellStatus.CONTINUE;
				}
			}
		} else {
			env.writeln("Wrong symbol command.");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}


}
