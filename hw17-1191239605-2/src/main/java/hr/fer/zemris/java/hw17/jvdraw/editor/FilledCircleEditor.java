package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;

/**
 *	{@link GeometricalObjectEditor} which allows the editing of a {@link FilledCircle}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
//TODO javadoc
	JTextField centerx;
	JTextField centery;
	JTextField radius;
	JTextField borderColorR;
	JTextField borderColorG;
	JTextField borderColorB;	
	JTextField fillColorR;
	JTextField fillColorG;
	JTextField fillColorB;
	
	private Color borderColor;
	private Color fillColor;
	private Point center;
	private int rad;
	
	private FilledCircle filledCircle;
	
	/**
	 * 	Constructs a new {@link FilledCircleEditor} which edits the given {@link FilledCircle}.
	 * 	@param filledCircle {@link FilledCircle} to edit.
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;	
		setLayout(new GridLayout(0, 2));
		
		centerx = new JTextField(String.valueOf(filledCircle.getCenter().x));
		centery = new JTextField(String.valueOf(filledCircle.getCenter().y));
		radius = new JTextField(String.valueOf(filledCircle.getRadius()));
		borderColorR = new JTextField(String.valueOf(filledCircle.getBorderColor().getRed()));
		borderColorG = new JTextField(String.valueOf(filledCircle.getBorderColor().getGreen()));
		borderColorB = new JTextField(String.valueOf(filledCircle.getBorderColor().getBlue()));
		fillColorR = new JTextField(String.valueOf(filledCircle.getFillColor().getRed()));
		fillColorG = new JTextField(String.valueOf(filledCircle.getFillColor().getGreen()));
		fillColorB = new JTextField(String.valueOf(filledCircle.getFillColor().getBlue()));
		
		add(new JLabel("X"));
		add(centerx);
		add(new JLabel("Y"));
		add(centery);		
		add(new JLabel("Radius"));
		add(radius);
		
		add(new JLabel("Red fill"));
		add(fillColorR);
		add(new JLabel("Green fill"));
		add(fillColorG);
		add(new JLabel("Blue fill"));
		add(fillColorB);
		
		add(new JLabel("Red border"));
		add(borderColorR);
		add(new JLabel("Green border"));
		add(borderColorG);
		add(new JLabel("Blue border"));
		add(borderColorB);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		try{
			fillColor = new Color(Integer.parseInt(fillColorR.getText()),
								 Integer.parseInt(fillColorG.getText()),
								 Integer.parseInt(fillColorB.getText()));
			center = new Point(Integer.parseInt(centerx.getText()), Integer.parseInt(centery.getText()));
			borderColor = new Color(Integer.parseInt(borderColorR.getText()),
							  		Integer.parseInt(borderColorG.getText()),
							  		Integer.parseInt(borderColorB.getText()));
			center = new Point(Integer.parseInt(centerx.getText()), Integer.parseInt(centery.getText()));
			rad = Integer.parseInt(radius.getText());
		} catch(Exception ex) {
			throw new RuntimeException("Wrong data");
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		filledCircle.setCenter(center);
		filledCircle.setRadius(rad);
		filledCircle.setBorderColor(borderColor);
		filledCircle.setFillColor(fillColor);
	}

}
