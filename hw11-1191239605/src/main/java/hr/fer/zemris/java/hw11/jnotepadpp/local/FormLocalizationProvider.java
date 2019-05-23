package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 *	One concrete implementation of {@link LocalizationProviderBridge}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * 	Constructs a new {@link FormLocalizationProvider} with the given provider 
	 * 	and given frame.
	 * 	@param provider {@link ILocalizationProvider} which provides localizations.
	 * 	@param frame {@link JFrame} which uses the provider.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				connect();
			}
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				disconnect();
			}
			
		});
	}
	
}
