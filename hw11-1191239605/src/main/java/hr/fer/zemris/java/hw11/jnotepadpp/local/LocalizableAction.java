package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 *	LocalizableAction TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public abstract class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new LocalizableAction.
	 * 	TODO javadoc
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
