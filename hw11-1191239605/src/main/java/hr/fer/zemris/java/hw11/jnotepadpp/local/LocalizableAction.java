package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 *	Class which sets and updates the name and short description of the
 *	Action when the localization changes.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public abstract class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new {@link LocalizableAction} with the help
	 * 	of the given {@link ILocalizationProvider}.
	 * 	@param key Key for the instance of this {@link LocalizableAction}.
	 * 	@param lp {@link ILocalizationProvider} which provides the localization.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {

		putValue(NAME, lp.getString(key));
		putValue(SHORT_DESCRIPTION, lp.getString(key + "ShortDesc"));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
				putValue(SHORT_DESCRIPTION, lp.getString(key + "ShortDesc"));
			}
		});
	}
	
}
