package v;

import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private FormLocalizationProvider flp;
	
	public Prozor() throws HeadlessException {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setTitle("Demo");
		initGUI();
		pack();
		
	}
	
	
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
			
		JMenuBar mb = new JMenuBar();
		JMenu lang = new JMenu("Languages");
		mb.add(lang);
		
		JMenuItem hr = new JMenuItem(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		});
		JMenuItem en = new JMenuItem(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
		JMenuItem de = new JMenuItem(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		});
		en.setText("en");
		hr.setText("hr");
		de.setText("de");
		
		lang.add(en);
		lang.add(hr);
		lang.add(de);
		
		getContentPane().add(mb, BorderLayout.NORTH);
		
		
//		JButton gumb = new JButton(
//			flp.getString("login")
//		);
		JButton gumb = new JButton(
			new LocalizableAction("login", flp) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//napravi prijavu ...
				}
			}
		);
		getContentPane().add(gumb, BorderLayout.CENTER);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				gumb.setText(LocalizationProvider.getInstance().getString("login"));
			}
		});
		
//		gumb.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//			// Napravi prijavu...
//			}
//		});
	}
	

	
	public static void main(String[] args) {
		if(args.length != 1) {
		System.err.println("Očekivao sam oznaku jezika kao argument!");
		System.err.println("Zadajte kao parametar hr ili en.");
		System.exit(-1);
		}
		final String jezik = args[0];
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage(jezik);
				new Prozor().setVisible(true);
			}
		});
	}
	
	
}
