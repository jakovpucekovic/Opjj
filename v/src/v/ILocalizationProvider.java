package v;

/**
 *	ILocalizationProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public interface ILocalizationProvider {

	void addLocalizationListener(ILocalizationListener l);
	
	void removeLocalizationListener(ILocalizationListener l);
	
	String getString(String key);
	
}
