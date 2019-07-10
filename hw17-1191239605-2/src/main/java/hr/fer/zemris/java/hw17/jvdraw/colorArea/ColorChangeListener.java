package hr.fer.zemris.java.hw17.jvdraw.colorArea;

import java.awt.Color;

/**
 *	Interface which models listeners which wait on the color change and when 
 *	that happens their <code>newColorSelected()</code> method should be called.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface ColorChangeListener {
	
	/**
	 *	Method which is executed when the a new color is selected.
	 *	@param source {@link IColorProvider} which notified of the change.
	 *	@param oldColor Old {@link Color} of the component.
	 *	@param newColor New {@link Color} of the component.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}