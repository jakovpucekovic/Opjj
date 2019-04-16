package hr.fer.zemris.java.hw06.shell;

/**
 *	Enum which lists every status that {@link MyShell} can have.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public enum ShellStatus {

	/**Signals the shell to keep working.*/
	CONTINUE,
	
	/**Signals the shell to stop working.*/
	TERMINATE
}
