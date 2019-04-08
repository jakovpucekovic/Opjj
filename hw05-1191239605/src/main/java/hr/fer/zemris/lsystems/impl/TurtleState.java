package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

public class TurtleState {
 
	private Vector2D currentPosition;
	private Vector2D direction;
	private Color color;
	private double length;
	
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double length) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.length = length;
	}
	
	public TurtleState() {
		this(new Vector2D(0, 0), new Vector2D(1, 0), new Color(0xffffff), 1.0);
	}

	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public TurtleState copy() {
		return new TurtleState(currentPosition, direction, color, length);
	}
	
}
