package v;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 *	FormLocalizationProvider TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * 	Constructs a new FormLocalizationProvider.
	 * 	TODO javadoc
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
