package hr.fer.zemris.java.hw17.jvdraw.colorArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 *	Class which implements the {@link IColorProvider} interface and extend 
 *	{@link JComponent}. It's a 15x15 pixels square Swing component which enables
 *	the selection of {@link Color}s. It also displays the selected {@link Color}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class JColorArea extends JComponent implements IColorProvider{

	private static final long serialVersionUID = 1L;

	/**Color of this {@link JColorArea}.*/
	private Color selectedColor;
	
	/**List of all {@link ColorChangeListener}s.*/
	private List<ColorChangeListener> colorChangeListeners = new ArrayList<>();
	
	/**
	 * 	Constructs a new {@link JColorArea} of the selected {@link Color}.
	 * 	@param color {@link Color} to fill this {@link JColorArea} with.
	 */
	public JColorArea(Color color) {
		selectedColor = color;
		
		addMouseListener(new MouseAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Select new color", selectedColor);
				if(newColor != null) {
					Color oldColor = selectedColor;
					selectedColor = newColor;
					notifyAllColorChangeListeners(oldColor, newColor);
					repaint();
				}
			}
			
		});
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(selectedColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(15, 15);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.add(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorChangeListeners.remove(l);
	}
	
	/**
	 * 	Notifies all registered {@link ColorChangeListener}s.
	 * 	@param oldColor {@link Color} before the change.
	 * 	@param newColor {@link Color} after the change.
	 */
	public void notifyAllColorChangeListeners(Color oldColor, Color newColor) {
		for(var l : colorChangeListeners) {
			l.newColorSelected(this, oldColor, newColor);
		}
	}
	
}
