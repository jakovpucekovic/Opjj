package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 *	ILocalizationProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public interface ILocalizationProvider {

	void addLocalizationListener(ILocalizationListener l);
	
	void removeLocalizationListener(ILocalizationListener l);
	
	String getCurrentLanguage();
	
	String getString(String key);
	
}
