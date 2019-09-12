package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledTriangle;

/**
 *	FilledTriangleEditor TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FilledTriangleEditor extends GeometricalObjectEditor {

	private JTextField ax;
	private JTextField ay;
	private JTextField bx;
	private JTextField by;
	private JTextField cx;
	private JTextField cy;
	
	private JColorArea fillColor;
	private JColorArea borderColor;
	
	private FilledTriangle filledTriangle;
	
	private Point a;
	private Point b;
	private Point c;
	
	/**
	 * 	Constructs a new FilledTriangleEditor.
	 * 	TODO javadoc
	 */
	public FilledTriangleEditor(FilledTriangle filledTriangle) {
		this.filledTriangle = filledTriangle;
		
		ax = new JTextField(String.valueOf(filledTriangle.getA().x));
		ay = new JTextField(String.valueOf(filledTriangle.getA().y));
		bx = new JTextField(String.valueOf(filledTriangle.getB().x));
		by = new JTextField(String.valueOf(filledTriangle.getB().y));
		cx = new JTextField(String.valueOf(filledTriangle.getC().x));
		cy = new JTextField(String.valueOf(filledTriangle.getC().y));
		
		fillColor = new JColorArea(filledTriangle.getFillColor());
		borderColor = new JColorArea(filledTriangle.getBorderColor());
		
		setLayout(new GridLayout(0, 2));
		
		add(new JLabel("a.x"));
		add(ax);
		add(new JLabel("a.y"));
		add(ay);
		add(new JLabel("b.x"));
		add(bx);
		add(new JLabel("b.y"));
		add(by);
		add(new JLabel("c.x"));
		add(cx);
		add(new JLabel("c.y"));
		add(cy);
		add(new JLabel("border color"));
		add(borderColor);
		add(new JLabel("fill color"));
		add(fillColor);
		
	}
	
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		try{
			a = new Point(Integer.parseInt(ax.getText()),Integer.parseInt(ay.getText()));
			b = new Point(Integer.parseInt(bx.getText()),Integer.parseInt(by.getText()));
			c = new Point(Integer.parseInt(cx.getText()),Integer.parseInt(cy.getText()));
		} catch(Exception ex) {
			throw new RuntimeException("Wrong data");
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		filledTriangle.setA(a);
		filledTriangle.setB(b);
		filledTriangle.setC(c);
		filledTriangle.setFillColor(fillColor.getCurrentColor());
		filledTriangle.setBorderColor(borderColor.getCurrentColor());
	}

	
	
}
