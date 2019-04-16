package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

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
		commands = new TreeMap<>();//TODO mapa koju vracas se ne smije moci mjenjati
		commands.put("exit"		, new ExitShellCommand());
		commands.put("symbol"	, new SymbolCommand());
		commands.put("help"		, new HelpCommand());
		commands.put("tree"		, new TreeCommand());
		commands.put("mkdir"	, new MkdirCommand());
		commands.put("ls"		, new LsCommand());
		commands.put("cat"		, new CatCommand());
		commands.put("charsets"	, new CharsetsCommand());
		commands.put("hexdump"	, new HexdumpCommand());		
		commands.put("copy"		, new CopyCommand());		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws ShellIOException {
		return sc.nextLine();//TODO exceptioni
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
