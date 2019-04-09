package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 *  Class which implements a {@link Command} which
 *  scales the length of the current TurtleState.
 *  
 *  @author Jakov Pucekovic
 *  @version 1.0
 */
public class ScaleCommand implements Command{

	/**The factor for which the length should be scaled.*/
	private double factor;
	
	/**
	 *  Constructs a new {@link ScaleCommand} with the given value.
	 *  @param factor The factor for which the length should be scaled.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 *  Scales the length of the current {@link TurtleState}.
	 *  @param ctx The current {@link Context}.
	 *  @param painter Does nothing here.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {

		double currentLenght = ctx.getCurrentState().getLength();
		ctx.getCurrentState().setLength(currentLenght * factor);
		
	}

}
