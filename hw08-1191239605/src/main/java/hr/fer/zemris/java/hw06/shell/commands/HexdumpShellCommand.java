package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.utils.ParserUtil;

/**
 *	Class {@link HexdumpShellCommand} which implements a {@link ShellCommand}
 *	and prints the hexdump of the given file when executed.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.1
 */
public class HexdumpShellCommand implements ShellCommand{

	/**{@link List} of {@link String} which contains the description of the command.*/
	private static List<String> description;
	
	/**
	 * 	Constructs a new {@link HexdumpShellCommand}.
	 */
	public HexdumpShellCommand() {
		description = new ArrayList<>();
		description.add("Command which prints contents of the given file in hexadecimal values.");
		description.add("Usage: hexdump file");
	}
	
	/**
	 * 	Executes this {@link ShellCommand} which print a hexdump of the given file;
	 * 	@param env The {@link Environment} in which this {@link HexdumpShellCommand} is executed.
	 * 	@param arguments Path to the directory.
	 * 	@return {@link ShellStatus} which signals to continue with the work.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		
		try{
			args = ParserUtil.parse(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.size() > 1) {
			env.writeln("This command takes only 1 argument.");
			return ShellStatus.CONTINUE;
		}

		if(args.get(0).isBlank()) {
			env.writeln("No argument given.");
			return ShellStatus.CONTINUE;
		}
		
		Path file;
		
		try {
			file = Paths.get(args.get(0));	
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}
		
		file = env.getCurrentDirectory().resolve(file);

		/*Check if file exists.*/
		if(!Files.exists(file)) {
			env.writeln("File doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		/*Hexdump only works on files*/
		if(!Files.isRegularFile(file)) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		/*Read file format it and print*/
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file))){
			byte[] readData = new byte[16];
			int counter = 0;
			int bytesRead = bis.read(readData, 0, 16);
			while(bytesRead != -1) {
				env.writeln(formatOutput(readData, bytesRead ,counter++));
				bytesRead = bis.read(readData, 0, 16);
			}			
		} catch (IOException e) {
			env.writeln("File can't be read.");
			return ShellStatus.CONTINUE;
		}	
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * 	Private method which formats the given data to print in one row.
	 * 	@param data byte[] of data to format
	 * 	@param writeThisMany Number of bytes from data to write.
	 * 	@param n Number of the row to format.
 	 *  @return Formatted {@link String}.
	 */
	private String formatOutput(byte[] data, int writeThisMany, int n) {
		StringBuilder sb = new StringBuilder();
		StringBuilder text = new StringBuilder();
		
		/*append memory location*/
		String hexNumber = Integer.toHexString(n * 16);
		sb.append("0".repeat(8-hexNumber.length()));
		sb.append(hexNumber);
		sb.append(": ");
		
		/*append hex chars to sb and text in these chars to text*/
		for(int i = 0; i < 16; ++i) {
			if(i < writeThisMany) {
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
		return bytetohex(bArray);
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
		return Collections.unmodifiableList(description);
	}

	/**
	 *	Method which converts the given byte array to hex string representation
	 *	where each byte is converted to 2 characters in hex.
	 *	@param bytearray Array which needs to be converted to String.
	 *	@return String value of the byte array. 
	 */
	private static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		
		for(var i : bytearray) {
			String toAppend = Integer.toString(i & 0xff, 16);
			sb.append(toAppend.length() == 1 ? "0" + toAppend : toAppend);
		}
		
		return sb.toString().toUpperCase();
	}
}
