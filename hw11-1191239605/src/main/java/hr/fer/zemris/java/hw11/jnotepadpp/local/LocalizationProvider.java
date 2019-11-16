package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *	Class which is an actual {@link LocalizationProvider} and provides the
 *	localization for everything in this project. Modeled as singleton, for
 *	optimizations sake.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	/**Current language*/
	private String language;
	/**Locale which does the localization*/
	private Locale locale;
	/**Bundle which loads the translation files.*/
	private ResourceBundle bundle;
	/**Static instance of this class as per singleton pattern.*/
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * 	Constructs a new {@link LocalizationProvider} and sets the default language to
	 * 	english and load bundle.
	 */
	private LocalizationProvider() {
		setLanguage("en");
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}

	/**
	 * 	Returns an instance of this class, as per singleton designer pattern.
	 * 	@return An instance of this class.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * 	Set the current language and notifies of the change.
	 * 	@param language New language.
	 */
	public void setLanguage(String language) {
		this.language = language;
		locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		fire();
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
