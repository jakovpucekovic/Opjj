package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipOutputStream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 *	Class MyShellCommand.
 * 	TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class CopyCommand implements ShellCommand{

	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("Invalid number of arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		String[] args = arguments.split("\\s+");
		if(args.length != 1 && args.length != 2) {
			env.writeln("Invalid number of arguments given.");
			return ShellStatus.CONTINUE;	
		}
		
		Path whatToCopy;
		Path whereToCopy;
		
		try {
			whatToCopy = Paths.get(args[0]);
			whereToCopy = Paths.get(args[1]);
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path given.");
			return ShellStatus.CONTINUE;
		}

		if(!whatToCopy.toFile().isFile()) {
			env.writeln("Given argument is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		if(whereToCopy.toFile().isDirectory()) {
			whereToCopy = Path.of(whereToCopy.toString() + whatToCopy.getFileName());			
		}
		
//		try {
//			Files.createFile(whereToCopy);
//		} catch (IOException e) {
//			throw new ShellIOException("I think i throw"); //TODO
//		}	
		
		try {
			InputStream is = Files.newInputStream(whatToCopy);
			OutputStream os = Files.newOutputStream(whereToCopy);//vidi za transferTo TODO
			byte[] buffer = new byte[4000];
			int dataRead = is.read(buffer, 0, 4000);
			while(dataRead != -1) {
				os.write(buffer, 0, dataRead);
				dataRead = is.read(buffer, 0, 4000);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			throw new ShellIOException("I think i throw"); //TODO
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}


}
