package hr.fer.zemris.java.hw17.jvdraw.graphicalObjects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.editor.FilledTriangleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	FilledTriangle TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FilledTriangle extends GeometricalObject {

	private Point a;
	private Point b;
	private Point c;
	
	private Color borderColor;
	private Color fillColor;
	
	
	/**
	 * 	Constructs a new FilledTriangle.
	 * 	TODO javadoc
	 */
	public FilledTriangle(Point a, Point b, Point c, Color borderColor, Color fillColor) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.borderColor = borderColor;
		this.fillColor = fillColor;
	}
	
	
	
	/**
	 * 	Returns the a of the FilledTriangle.
	 * 	@return the a of the FilledTriangle.
	 */
	public Point getA() {
		return a;
	}



	/**
	 * 	Sets the a of the FilledTriangle.
	 * 	@param a the a to set.
	 */
	public void setA(Point a) {
		this.a = a;
		notifyListeners();
	}



	/**
	 * 	Returns the b of the FilledTriangle.
	 * 	@return the b of the FilledTriangle.
	 */
	public Point getB() {
		return b;
	}



	/**
	 * 	Sets the b of the FilledTriangle.
	 * 	@param b the b to set.
	 */
	public void setB(Point b) {
		this.b = b;
		notifyListeners();

	}



	/**
	 * 	Returns the c of the FilledTriangle.
	 * 	@return the c of the FilledTriangle.
	 */
	public Point getC() {
		return c;
	}



	/**
	 * 	Sets the c of the FilledTriangle.
	 * 	@param c the c to set.
	 */
	public void setC(Point c) {
		this.c = c;
		notifyListeners();

	}



	/**
	 * 	Returns the borderColor of the FilledTriangle.
	 * 	@return the borderColor of the FilledTriangle.
	 */
	public Color getBorderColor() {
		return borderColor;
	}



	/**
	 * 	Sets the borderColor of the FilledTriangle.
	 * 	@param borderColor the borderColor to set.
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		notifyListeners();

	}



	/**
	 * 	Returns the fillColor of the FilledTriangle.
	 * 	@return the fillColor of the FilledTriangle.
	 */
	public Color getFillColor() {
		return fillColor;
	}



	/**
	 * 	Sets the fillColor of the FilledTriangle.
	 * 	@param fillColor the fillColor to set.
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		notifyListeners();

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
		return new FilledTriangleEditor(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Rectangle getBoundingBox() {
		Rectangle rec = new Rectangle();
		int minX = Math.min(Math.min(a.x, b.x), c.x);
		int	minY = Math.min(Math.min(a.y, b.y), c.y);
		int maxX = Math.max(Math.max(a.x, b.x), c.x);
		int maxY = Math.max(Math.max(a.y, b.y), c.y);
		
		rec.x = minX;
		rec.y = minY;
		rec.width = maxX - minX;
		rec.height = maxY - minY;
		
		return rec;
	}

	public int getN() {
		if(a == null) return 0;
		if(b == null) return 1;
		if(c == null) return 2;
		return 3;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("Filled triangle (%d,%d),(%d,%d),(%d,%d), #%02X%02X%02X)", 
				a.x,
				a.y,
				b.x,
				b.y,
				c.x,
				c.y,
				fillColor.getRed(),
				fillColor.getGreen(),
				fillColor.getBlue());
	}
	
}
