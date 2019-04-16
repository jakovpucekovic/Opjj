package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;

/**
 *	Class MyShell.
 *TODO javadoc 
 * 	@author Jakov Pucekovic
 */
public class MyShell {
	
	private static Environment env;
	
	public static void main(String[] args) {
		System.out.println("Welcome to MyShell v 1.0 ");
		env = new MyShellEnvironment();
		
		while(true) {
			env.write(env.getPromptSymbol().toString() + " ");
			
			String str = env.readLine();
			
			if(str.equals("exit")) {
				break;
			}
			
			StringBuilder sb = new StringBuilder();
			while(str.endsWith(env.getMorelinesSymbol().toString())) {
				sb.append(str.substring(0, str.length() - 1));
				str = env.readLine();
			}
				
				
		}
		
	
	}
	
	
}
