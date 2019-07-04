package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.util.function.Supplier;

/**
 *	CurrentTool TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class CurrentTool implements Supplier<Tool> {

	private Tool currentTool;
	
	
	/**
	 * 	Constructs a new CurrentTool.
	 * 	TODO javadoc
	 */
	public CurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}
	
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
