package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class {@link HexdumpCommand} which implements a {@link ShellCommand}
 *	and prints the hexdump of the given file when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class HexdumpCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link HexdumpCommand}.
	 */
	public HexdumpCommand() {
		description = new ArrayList<>();
		description.add("Command which prints contents of the given file in hexadecimal values.");
		description.add("Usage: hexdump directory");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print a hexdump of the given file;
	 * 	@param env The {@link Environment} in which this {@link HexdumpCommand} is executed.
	 * 	@param arguments Path to the directory.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid number of arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		Path file;
		
		try {
			file = Paths.get(arguments);
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		if(!file.toFile().isFile()) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file)); 
			byte[] readData = new byte[16];
			int counter = 0;
			while(bis.read(readData, 0, 16) != -1) {
				env.writeln(formatOutput(readData, counter++));
			}
			
			bis.close();
		} catch (IOException e) {
			throw new ShellIOException("I think i throw"); //TODO
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	Private method which formats the given data to print in one row.
	 * 	@param data byte[] of data to format
	 * 	@param n Number of the row to format.
 	 *  @return Formatted {@link String}.
	 */
	private String formatOutput(byte[] data, int n) {
		StringBuilder sb = new StringBuilder();
		StringBuilder text = new StringBuilder();
		String hexNumber = Integer.toHexString(n * 16);
		sb.append("0".repeat(8-hexNumber.length()));
		sb.append(hexNumber);
		sb.append(": ");
		
		for(int i = 0; i < 16; ++i) {
			if(i < data.length) {
				sb.append(getHex(data[i]));
				if(data[i] < 32 || data[i] > 127) {
					text.append(".");
				} else {
					text.append((char) data[i]);
				}
			} else {
				sb.append("  ");
			}
			if(i == 8) {
				sb.append("|");
			} else {
				sb.append(" ");
			}
		}
	
		sb.append("| ");
		sb.append(text);
		
		return sb.toString();
	}
	
	/**
	 * 	Private method which gets the {@link String} of length 2
	 * 	which in the hexadecimal representation of the given byte.
	 * 	@param b Byte to represent in {@link String}
	 * 	@return Hexadecimal {@link String} representation of the given byte.
	 */
	private String getHex(byte b) {
		byte[] bArray = new byte[1];
		bArray[0] = b;
		return Util.bytetohex(bArray);
	}
	
	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}


}
