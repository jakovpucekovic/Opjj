package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 *	Interface which represents objects which can provide localization
 *	and notify their registered {@link ILocalizationListener}s about it.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface ILocalizationProvider {

	/**
	 * 	Adds the given {@link ILocalizationListener}.
	 * 	@param l {@link ILocalizationListener} to add.
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * 	Removes the given {@link ILocalizationListener}.
	 * 	@param l {@link ILocalizationListener} to remove.
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * 	Returns the current language.
	 * 	@return The current language.
	 */
	String getCurrentLanguage();
	
	/**
	 * 	Returns the localized {@link String} associated with the given key.
	 * 	@param key Key with which the localization value is locked.
	 */
	String getString(String key);
	
}
