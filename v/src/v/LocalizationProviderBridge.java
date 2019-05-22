package v;

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
	
	/**
	 * 	Constructs a new LocalizationProviderBridge.
	 * 	TODO javadoc
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
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

}
