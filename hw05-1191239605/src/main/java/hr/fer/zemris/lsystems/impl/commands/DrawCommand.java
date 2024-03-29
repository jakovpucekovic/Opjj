package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * 	Class which implements a {@link Command} to
 * 	draw a line.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class DrawCommand implements Command{

	/**The step of the line.*/
	private double step;
	
	/**
	 *  Constructs a new {@link DrawCommand} with the given value.
	 *  @param step The step with which the line should be drawn.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * 	Draws a line with the current step.
	 * 	@param ctx The current {@link Context}.
	 * 	@param painter The {@link Painter} which draws the line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D newPosition = currentState.getCurrentPosition().translated(currentState.getDirection().scaled(step * currentState.getLength()));
		painter.drawLine(
				currentState.getCurrentPosition().getX(),
				currentState.getCurrentPosition().getY(),
				newPosition.getX(),
				newPosition.getY(),
				currentState.getColor(),
				1f);
		currentState.setCurrentPosition(newPosition);
	}

}
