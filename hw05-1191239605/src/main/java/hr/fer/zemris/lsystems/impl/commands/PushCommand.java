package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * 	Class which implements a {@link Command} which
 * 	duplicates the current {@link TurtleState}.
 * 	
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class PushCommand implements Command{

	/**
	 * 	Duplicates the current {@link TurtleState}.
	 * 	@param ctx The current {@link Context}.	
	 * 	@param painter Does nothing here.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
