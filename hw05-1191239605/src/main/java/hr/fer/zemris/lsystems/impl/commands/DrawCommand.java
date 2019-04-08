package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class DrawCommand implements Command{

	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		painter.drawLine(
				currentState.getCurrentPosition().getX(),
				currentState.getCurrentPosition().getY(),
				currentState.getDirection().getX(),
				currentState.getDirection().getY(),
				currentState.getColor(),
				(float) (currentState.getLength() * step));
		
	}

}
