package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class SkipCommand implements Command{
	
	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}

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
