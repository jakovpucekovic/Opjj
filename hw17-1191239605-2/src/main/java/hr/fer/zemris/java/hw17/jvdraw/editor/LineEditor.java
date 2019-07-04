package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

/**
 *	{@link GeometricalObjectEditor} which allows the editing of a {@link Line}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LineEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	private JTextField startx;
	private JTextField starty;
	private JTextField endx;
	private JTextField endy;
	private JTextField colorR;
	private JTextField colorG;
	private JTextField colorB;
	//TODO javadoc
	private Color color;
	private Point start;
	private Point end;
	
	private Line line;
	
	/**
	 * 	Constructs a new {@link LineEditor} which edits the given {@link Line}.
	 * 	@param line {@link Line} to edit.
	 */
	public LineEditor(Line line) {
		this.line = line;
		
		setLayout(new GridLayout(0, 2));
		
		startx = new JTextField(String.valueOf(line.getStart().x));
		starty = new JTextField(String.valueOf(line.getStart().y));
		endx = new JTextField(String.valueOf(line.getEnd().x));
		endy = new JTextField(String.valueOf(line.getEnd().y));
		colorR = new JTextField(String.valueOf(line.getColor().getRed()));
		colorG = new JTextField(String.valueOf(line.getColor().getGreen()));
		colorB = new JTextField(String.valueOf(line.getColor().getBlue()));
		
		add(new JLabel("X1"));
		add(startx);
		add(new JLabel("Y1"));
		add(starty);		
		add(new JLabel("X2"));
		add(endx);
		add(new JLabel("Y2"));
		add(endy);
		
		add(new JLabel("Red"));
		add(colorR);
		add(new JLabel("Green"));
		add(colorG);
		add(new JLabel("Blue"));
		add(colorB);
	}
	
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		try{
			color = new Color(Integer.parseInt(colorR.getText()),
							  Integer.parseInt(colorG.getText()),
							  Integer.parseInt(colorB.getText()));
			start = new Point(Integer.parseInt(startx.getText()), Integer.parseInt(starty.getText()));
			end = new Point(Integer.parseInt(endx.getText()), Integer.parseInt(endy.getText()));
		} catch(Exception ex) {
			throw new RuntimeException("Wrong data");
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		line.setStart(start);
		line.setEnd(end);
		line.setColor(color);
	}

}
