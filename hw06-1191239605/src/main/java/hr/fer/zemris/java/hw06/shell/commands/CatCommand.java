package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class MyShellCommand.
 * 	TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class CatCommand implements ShellCommand{

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+");
		if(args.length  != 1 && args.length != 2) {
			env.writeln("Wrong number of arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		Path directory;
		
		try {
			directory = Paths.get(arguments);
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		if(directory.toFile().isDirectory()) {
			env.writeln("Given argument is a directory.");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset = Charset.defaultCharset();
		if(args.length == 2 && Charset.isSupported(args[1])) {
			charset = Charset.forName(args[1]);
		}
		
		
		try {
			BufferedReader br = Files.newBufferedReader(directory, charset);
			String toWrite = br.readLine();
			while(toWrite != null) {
				env.writeln(toWrite);
				toWrite = br.readLine();
			}
		} catch (IOException e) {
			throw new ShellIOException("I think i throw"); //TODO
		}	
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}


}
