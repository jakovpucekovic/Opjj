package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 *	LocalizationProviderBridge TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationProvider parent;
	private ILocalizationListener listener;
	private String currentLanguage;
	
	/**
	 * 	Constructs a new LocalizationProviderBridge.
	 * 	TODO javadoc
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.currentLanguage = parent.getCurrentLanguage();
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	public void disconnect() {
		if(connected) {
		parent.removeLocalizationListener(listener);
		connected = false;
		}
	}
	
	public void connect() {
		if(!connected) {
			parent.addLocalizationListener(listener);
			if(currentLanguage != parent.getCurrentLanguage()) {
				currentLanguage = parent.getCurrentLanguage();
				fire();
			}
			connected = true;
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return currentLanguage;
	}

}
