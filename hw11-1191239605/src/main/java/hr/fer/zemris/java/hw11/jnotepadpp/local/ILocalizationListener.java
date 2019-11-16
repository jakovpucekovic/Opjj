package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 *	Interface which represents objects capable of listening to
 *	a {@link ILocalizationProvider}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface ILocalizationListener {
	
	/**
	 * 	Method to call when the localization changes. 
	 */
	void localizationChanged();
}
