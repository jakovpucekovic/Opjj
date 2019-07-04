package hr.fer.zemris.java.hw17.jvdraw.graphicalObjects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	Class which represents a line which is a {@link GeometricalObject}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Line extends GeometricalObject {

	/**Starting point of the line.*/
	private Point start;

	/**Ending point of the line.*/
	private Point end;

	/**{@link Color} of the line.*/
	private Color color;
	
	/**
	 * 	Constructs a new {@link Line} of the given {@link Color} with the 
	 * 	given starting and ending points.
	 * 	@param start Starting point of the line.
	 * 	@param end Ending point of the line.
	 * 	@param color {@link Color} of the line.
	 */
	public Line(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}
	
	/**
	 * 	Returns the start point of the {@link Line}.
	 * 	@return the start point of the {@link Line}.
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * 	Returns the end point of the {@link Line}.
	 * 	@return the end point of the {@link Line}.
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * 	Returns the {@link Color} of the Line.
	 * 	@return the {@link Color} of the Line.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Rectangle getBoundingBox() {
		int x = start.x < end.x ? start.x : end.x;
		int y = start.y < end.y ? start.y : end.y;
		int width = start.x - end.x;
		int height = start.y - end.y;
		return new Rectangle(x,
							 y,
							 width > 0 ? width : -width,
 							 height > 0 ? height : - height);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", start.x, start.y, end.x, end.y);
	}

}
