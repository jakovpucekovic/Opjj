package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 *	JNotepadPP TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class JNotepadPP extends JFrame {	
	
	/**
	 * 	Constructs a new JNotepadPP.
	 * 	TODO javadoc
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(10, 10);
		setSize(500, 500);
		
		initGUI();
		setClosingOperations();
	}
	
	
	//TODO javadoc
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		
		createActions();
		createMenus();
		cp.add(createToolbar(), BorderLayout.NORTH);
		
		cp.add(new DefaultMultipleDocumentModel(), BorderLayout.CENTER);
		
	}
	
	
	/**
	 * TODO javadoc
	 */
	private void createActions() {
		// TODO Auto-generated method stub
		
	}


	/**
	 *TODO javadoc
	 */
	private void createMenus() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @return //TODO javadoc
	 */
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton("New"));
		toolbar.add(new JButton("Open"));
		toolbar.add(new JButton("Save"));
		toolbar.add(new JButton("Save as"));
		toolbar.add(new JButton("Close"));
		toolbar.add(new JButton("Cut"));
		toolbar.add(new JButton("Copy"));
		toolbar.add(new JButton("Paste"));
		toolbar.add(new JButton("Info"));
		toolbar.add(new JButton("Exit"));
		
		return toolbar;
	}


	private void setClosingOperations() {
		//register closing listener
				addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						dispose();
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
					}
				});
	}
	
	
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(()->
				new JNotepadPP().setVisible(true));
		
	}
	
	
}
