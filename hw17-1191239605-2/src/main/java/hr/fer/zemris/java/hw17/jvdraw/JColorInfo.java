package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

/**
 *	JColorInfo TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class JColorInfo extends JLabel implements ColorChangeListener{

	private static final long serialVersionUID = 1L;
	
	/**Format of the message that is displayed on this label.*/
	private static final String format = "Foreground color: (%d, %d, %d), background color: (%d, %d, %d).";

	/**Foreground color provider*/
	private IColorProvider fgColorProvider;
	
	/**Background color provider*/
	private IColorProvider bgColorProvider;
	
	/**
	 * 	Constructs a new {@link JColorInfo} with the given {@link IColorProvider}s
	 * 	and registers this {@link JColorInfo} as listener on them.
	 * 	@param fgColorProvider Foreground color provider.
	 * 	@param bgColorProvider Background color provider.
	 */
	public JColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor();
		setText(String.format(format, fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(), bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
	}
	
}
