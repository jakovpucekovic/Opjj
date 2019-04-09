package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 *  Class which implements a {@link Command} which
 *  rotates the turtle for the given angle.
 *  
 *  @author Jakov Pucekovic
 *  @version 1.0
 */
public class RotateCommand implements Command{

	/**The angle for which the turtle should be rotated.*/
	private double angle;

	/**
	 *  Constructs a new {@link RotateCommand} with the given value.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * 	Rotates the turtle for the given angle.
	 * 	@param ctx The current {@link Context}.
	 * 	@param painter Does nothing here.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
