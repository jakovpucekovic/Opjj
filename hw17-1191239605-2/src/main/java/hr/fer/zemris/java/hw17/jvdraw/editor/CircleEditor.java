package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;

/**
 *	{@link GeometricalObjectEditor} which allows the editing of a {@link Circle}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	/**X coordinate of circle center.*/
	JTextField centerx;
	
	/**Y coordinate of circle center.*/
	JTextField centery;
	
	/**Radius of the circle.*/
	JTextField radius;
	
	/**Red value of the RGB color scheme for border color.*/
	JTextField colorR;
	
	/**Green value of the RGB color scheme for the border color.*/
	JTextField colorG;
	
	/**Blue value of the RGB color scheme for the border color.*/
	JTextField colorB;	
	
	/**New border color.*/
	private Color color;
	
	/**New center of the circle.*/
	private Point center;
	
	/**New radius of the circle.*/
	private int rad;
	
	/**Circle which is being edited.*/
	private Circle circle;
	
	/**
	 * 	Constructs a new {@link CircleEditor} which edits the given {@link Circle}.
	 * 	@param circle {@link Circle} to edit.
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;	
		setLayout(new GridLayout(0, 2));
		
		centerx = new JTextField(String.valueOf(circle.getCenter().x));
		centery = new JTextField(String.valueOf(circle.getCenter().y));
		radius = new JTextField(String.valueOf(circle.getRadius()));
		colorR = new JTextField(String.valueOf(circle.getBorderColor().getRed()));
		colorG = new JTextField(String.valueOf(circle.getBorderColor().getGreen()));
		colorB = new JTextField(String.valueOf(circle.getBorderColor().getBlue()));
		
		add(new JLabel("X"));
		add(centerx);
		add(new JLabel("Y"));
		add(centery);		
		add(new JLabel("Radius"));
		add(radius);
		
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
		circle.setCenter(center);
		circle.setRadius(rad);
		circle.setBorderColor(color);
	}

}
