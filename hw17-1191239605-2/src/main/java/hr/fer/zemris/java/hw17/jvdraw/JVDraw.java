package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.colorarea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.colorarea.JColorInfo;

/**
 *	JVDraw TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * 	Constructs a new {@link JVDraw}.
	 */
	public JVDraw() {
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(10, 10);		
		
//		addListeners();
		initGUI();
		setClosingOperations();
	
	}
	
	/**
	 *  Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
//		createActions();
//		createMenu();
		
		JPanel panel = new JPanel();
		
//		cp.add(createToolbar(), BorderLayout.NORTH);
		cp.add(panel, BorderLayout.CENTER);
		
		JColorArea fgColorArea = new JColorArea(Color.RED);
		JColorArea bgColorArea = new JColorArea(Color.BLUE);
		JColorInfo bottomColorInfo = new JColorInfo(fgColorArea, bgColorArea);
		cp.add(bottomColorInfo, BorderLayout.SOUTH);
	}
	
	
	
	
	
	
	private void exit() {
		dispose();
	}
	
	/**
	 * 	Sets closing operation which exits the program when user clicks
	 * 	the x button.
	 */
	private void setClosingOperations() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});		
	}
	
	
	/**
	 *  Main method which runs the program.
	 * 	@param args None.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new JVDraw().setVisible(true));
	}
	
}
