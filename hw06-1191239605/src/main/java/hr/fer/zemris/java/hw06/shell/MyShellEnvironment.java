package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 *	Class MyShellEnvironment which gives an implementation of {@link Environment}.
 *	
 * 	@author Jakov Pucekovic
 * 	@version 1.0
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

	/**
	 * 	Constructs a new {@link MyShellEnvironment} and registers
	 * 	all known commands.
	 */
	public MyShellEnvironment(){
		commands = new TreeMap<>();
		commands.put("exit"		, new ExitShellCommand());
		commands.put("symbol"	, new SymbolShellCommand());
		commands.put("help"		, new HelpShellCommand());
		commands.put("tree"		, new TreeShellCommand());
		commands.put("mkdir"	, new MkdirShellCommand());
		commands.put("ls"		, new LsShellCommand());
		commands.put("cat"		, new CatShellCommand());
		commands.put("charsets"	, new CharsetsShellCommand());
		commands.put("hexdump"	, new HexdumpShellCommand());		
		commands.put("copy"		, new CopyShellCommand());		
		
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
		System.out.println(text);
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

}
