package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 *	IColorProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public interface IColorProvider {

	/**
	 *  Returns the current {@link Color} of this {@link IColorProvider}.
	 *  @return The current {@link Color}.
	 */
	public Color getCurrentColor();
	
	/**
	 * 	Adds the given {@link ColorChangeListener} to the listeners which
	 * 	are notified when the {@link Color} changes.
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * 	Removes the given {@link ColorChangeListener} from the listeners
	 * 	that are notified when the {@link Color} changes.
	 * 	@param l Listener to remove.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
	
}
