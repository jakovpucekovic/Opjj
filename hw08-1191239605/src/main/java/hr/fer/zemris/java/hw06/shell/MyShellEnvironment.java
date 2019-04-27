package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 *	Class MyShellEnvironment which gives an implementation of {@link Environment}.
 *	
 * 	@author Jakov Pucekovic
 * 	@version 1.1
 */
public class MyShellEnvironment implements Environment{

	/**Current prompt symbol.*/
	private static char PROMPTSYMBOL = '>';
	
	/**Current morelines symbol.*/
	private static char MORELINESSYMBOL = '\\';
	
	/**Current multiline symbol.*/
	private static char MULTILINESYMBOL = '|';
	
	/**Scanner used for reading input.*/
	private Scanner sc = new Scanner(System.in);
	
	/**Stores known commands.*/
	private SortedMap<String, ShellCommand> commands;

	/**Path to the current directory.*/
	private Path currentDirectory;
	
	/**Map of shared data.*/
	private Map<String, Object> sharedData;
	
	/**
	 * 	Constructs a new {@link MyShellEnvironment} and registers
	 * 	all known commands.
	 */
	public MyShellEnvironment(){
		commands = new TreeMap<>();
		commands.put(new CatShellCommand()		.getCommandName(), new CatShellCommand());
		commands.put(new CdShellCommand()		.getCommandName(), new CdShellCommand());
		commands.put(new CharsetsShellCommand()	.getCommandName(), new CharsetsShellCommand());
		commands.put(new CopyShellCommand()		.getCommandName(), new CopyShellCommand());		
		commands.put(new DropdShellCommand()	.getCommandName(), new DropdShellCommand());
		commands.put(new ExitShellCommand()		.getCommandName(), new ExitShellCommand());
		commands.put(new HelpShellCommand()		.getCommandName(), new HelpShellCommand());
		commands.put(new HexdumpShellCommand()	.getCommandName(), new HexdumpShellCommand());		
		commands.put(new ListdShellCommand()	.getCommandName(), new ListdShellCommand());
		commands.put(new LsShellCommand()		.getCommandName(), new LsShellCommand());
		commands.put(new MkdirShellCommand()	.getCommandName(), new MkdirShellCommand());
		commands.put(new PopdShellCommand()		.getCommandName(), new PopdShellCommand());
		commands.put(new PushdShellCommand()	.getCommandName(), new PushdShellCommand());
		commands.put(new PwdShellCommand()		.getCommandName(), new PwdShellCommand());
		commands.put(new SymbolShellCommand()	.getCommandName(), new SymbolShellCommand());
		commands.put(new TreeShellCommand()		.getCommandName(), new TreeShellCommand());
		
		sharedData = new HashMap<>();
		currentDirectory = Paths.get(".").toAbsolutePath().normalize();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			return sc.nextLine();
		} catch (NoSuchElementException | IllegalStateException ex) {
			throw new ShellIOException(ex.getMessage());
		}
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		write(text + "\n");
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		if(!Files.exists(path)) {//TODO provjeri koji exception bacas
			throw new ShellIOException("Path doesn't exist.");
		}
		currentDirectory = path.toAbsolutePath().normalize();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}

}
