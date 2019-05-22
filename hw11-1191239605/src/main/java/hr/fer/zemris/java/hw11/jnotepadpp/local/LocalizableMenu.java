package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 *	LocalizableLabel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class LocalizableMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new LocalizableLabel.
	 * 	TODO javadoc
	 */
	public LocalizableMenu(String key, ILocalizationProvider lp) {

		setText(lp.getString(key));		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));		
			}
		});
	}
	
}
