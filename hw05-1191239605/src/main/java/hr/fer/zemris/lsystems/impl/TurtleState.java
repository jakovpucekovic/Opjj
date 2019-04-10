package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 *  Class which represents the state of the turtle.
 *  
 *  @author Jakov Pucekovic
 *  @version 1.0
 */
public class TurtleState {
 
	/**The current position of the turtle.*/
	private Vector2D currentPosition;
	/**The direction of the turtle.*/
	private Vector2D direction;
	/**The color of the turtle.*/
	private Color color;
	/**The initial length the turtle goes.*/
	private double length;
	
	/**
	 *  Constructs a new {@link TurtleState} with the given values.
	 *  @param currentPosition The current position of the turtle.
	 *  @param direction The direction where the turtle is facing.
	 *  @param color The color of the turtle.
	 *  @param The length the turtle will be going.
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double length) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.length = length;
	}
	
	/**
	 *  Construct a new {@link TurtleState} with the default values. 
	 *  Turtle starts in position (0,0), faces right, is black and its
	 *  length is 1.
	 */
	public TurtleState() {
		this(new Vector2D(0, 0), new Vector2D(1, 0), Color.black, 1.0);
	}

	/**
	 * 	Returns the current position of the turtle.
	 * 	@return The current position of the turtle.
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * 	Sets the current position of the turtle.
	 * 	@param currentPosition The new position.
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 *  Returns the direction of the turtle.
	 *  @return The direction of the turtle.
	 */
	public Vector2D getDirection() {
		return currentPosition.scaled(length);
	}

	/**
	 *  Sets the direction of the turtle.
	 *  @param direction The new direction.
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * 	Returns the color of the turtle.
	 * 	@return The color of the turtle.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 	Sets the color of the turtle.
	 * 	@param color The new color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 *  Returns the length of the turtle.
	 *  @return The length of the turtle.
	 */
	public double getLength() {
		return length;
	}

	/**
	 *  Sets the length of the turtle.rgb
	 *  @param length The new length.
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * 	Returns a copy of the current {@link TurtleState}.
	 * 	@return A copy of the current {@link TurtleState}.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), new Color(color.getRGB()) , length);
	}
	
}
