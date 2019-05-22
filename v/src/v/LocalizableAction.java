package v;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 *	LocalizableAction TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public abstract class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private String key;
	ILocalizationProvider prov;
	
	/**
	 * 	Constructs a new LocalizableAction.
	 * 	TODO javadoc
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;

		putValue(NAME, prov.getString(key));
		
		prov.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, prov.getString(key));
			}
		});
	}
	
}
