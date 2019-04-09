package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 *  Class which implements a {@link Command} which
 *  moves the turtle in the direction, but doesn't
 *  draw a line.
 *  
 *  @author Jakov Pucekovic
 *  @version 1.0 
 */
public class SkipCommand implements Command{
	
	/**The step of the line.*/
	private double step;
	
	/**
	 *  Constructs a new {@link SkipCommand} with the given value.
	 *  @param step The step with which the turtle should move.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * 	Moves the turtle with the given step, not drawing a line.
	 * 	@param ctx The current {@link Context}.
	 * 	@param painter The {@link Painter} which moves the turtle.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Color color = new Color(0, 0, 0, 255);
		painter.drawLine(
				currentState.getCurrentPosition().getX(),
				currentState.getCurrentPosition().getY(),
				currentState.getDirection().getX(),
				currentState.getDirection().getY(),
				color,
				(float) (currentState.getLength() * step));
		
	}

}
