package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 *	AbstractLocalizationProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	private List<ILocalizationListener> listeners;
	
	/**
	 * 	Constructs a new {@link AbstractLocalizationProvider}.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 *	Notifies all registered listeners that a change happened. 
	 */
	public void fire() {
		for(var l : listeners) {
			l.localizationChanged();
		}
	}

	
}
