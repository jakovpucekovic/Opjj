package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * 	Class which implements the {@link Command}
 * 	which sets a new {@link Color} to the current
 * 	{@link TurtleState}.
 */
public class ColorCommand implements Command{

	/**Color to set*/
	private Color color;
	
	/**
	 *  Constructs a new {@link ColorCommand} with the given value.
	 *  @param color Color to be set.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * 	Sets the color of the current {@link TurtleState}.
	 * 	@param ctx The current {@link Context}.
	 * 	@param painter Does nothing here.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
