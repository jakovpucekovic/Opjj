package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 *	Class which sets and updates the name and short description of the
 *	{@link JMenu} when the localization changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LocalizableMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new {@link LocalizableMenu} with the help
	 * 	of the given {@link ILocalizationProvider}.
	 * 	@param key Key for the instance of this {@link LocalizableMenu}.
	 * 	@param lp {@link ILocalizationProvider} which provides the localization.
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
