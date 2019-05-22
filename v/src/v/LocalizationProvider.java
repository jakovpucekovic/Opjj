package v;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	LocalizationProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	private Locale locale;
	private ResourceBundle bundle;
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
		setLanguage("en");
		bundle = ResourceBundle.getBundle("v.prijevodi", locale);
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("v.prijevodi", locale);
		fire();
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
}
