package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 *	Class which serves as a bridge between all classes which want to track
 *	{@link ILocalizationProvider} and the {@link ILocalizationProvider}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**Tracks whether is connected.*/
	private boolean connected;
	/**{@link ILocalizationProvider} which does the actual localization.*/
	private ILocalizationProvider parent;
	/**{@link ILocalizationListener} which is registered when connected.*/
	private ILocalizationListener listener;
	/**Current language of the {@link ILocalizationProvider}.*/
	private String currentLanguage;
	
	/**
	 * 	Constructs a new {@link LocalizationProviderBridge} with the given
	 * 	{@link ILocalizationProvider}.
	 * 	@param parent {@link ILocalizationProvider} which does the actual localization.
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
	
	/**
	 * 	Disconnects itself as listener from the given {@link ILocalizationProvider}.
	 */
	public void disconnect() {
		if(connected) {
		parent.removeLocalizationListener(listener);
		connected = false;
		}
	}
	
	/**
	 * 	Connects itself as listener to the given {@link ILocalizationProvider}
	 * 	so that all communication goes through this object.
	 */
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
