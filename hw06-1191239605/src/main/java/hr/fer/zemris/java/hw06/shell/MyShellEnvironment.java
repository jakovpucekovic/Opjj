package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;

/**
 *	Class MyShellEnvironment.
 * TODO javadoc
 * 	@author Jakov Pucekovic
 */
public class MyShellEnvironment implements Environment{

	private static char PROMPTSYMBOL = '>';
	private static char MORELINESSYMBOL = '\\';
	private static char MULTILINESYMBOL = '|';
	private Scanner sc = new Scanner(System.in);
	
//	public MyShellEnvironment(){
//		
//	}
	
	@Override
	public String readLine() throws ShellIOException {
		return sc.nextLine();
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
	}


}
