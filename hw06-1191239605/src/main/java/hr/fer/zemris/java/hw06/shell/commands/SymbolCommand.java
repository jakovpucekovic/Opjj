package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class MyShellCommand.
 * 	TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class SymbolCommand implements ShellCommand{

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		char symbol;
		String[] inputArray = arguments.trim().split("\\s+");
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

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}


}
