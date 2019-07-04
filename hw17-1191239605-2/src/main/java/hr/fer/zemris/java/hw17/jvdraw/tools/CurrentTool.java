package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.util.function.Supplier;

/**
 *	CurrentTool TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CurrentTool implements Supplier<Tool> {

	/**Stores the currently used {@link Tool}.*/
	private Tool currentTool;
	
	/**
	 * 	Constructs a new {@link CurrentTool} and sets the currentTool
	 * 	to the given {@link Tool}.
	 * 	@param currentTool {@link Tool} to which the {@link CurrentTool} should be set to.
	 */
	public CurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}

	/**
	 *	Sets the {@link CurrentTool} to the given {@link Tool}.
	 *	@param newTool The {@link Tool} to which the {@link CurrentTool} should be set to.
	 */
	public void changeToolTo(Tool newTool) {
		currentTool = newTool;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Tool get() {
		return currentTool;
	}

}
