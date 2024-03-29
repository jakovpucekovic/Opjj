package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 *	Interface which represents a command that can be executed.
 *
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public interface Command {

	/**
	 *  Method which executes the command.
	 *  @param ctx	The {@link Context} in which we're currently working.
	 *	@param painter The {@link Painter} which paints the command if needed.  
	 */
	void execute(Context ctx, Painter painter);
	
}
