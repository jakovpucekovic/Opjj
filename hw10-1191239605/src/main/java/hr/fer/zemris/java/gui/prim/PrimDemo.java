package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *	PrimDemo TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class PrimDemo extends JFrame{

	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("PrimDemo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> left = new JList<Integer>(model);
		JList<Integer> right = new JList<Integer>(model);
		
		JButton button = new JButton("Slijedeci");
		button.addActionListener(e->model.next());
		
		JPanel middle = new JPanel(new GridLayout(1,0));
		middle.add(new JScrollPane(left));
		middle.add(new JScrollPane(right));
		
		cp.add(middle, BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
	}
	
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(()->{
			JFrame frame = new PrimDemo();
			frame.setVisible(true);
		});
	}
	
	
	
	
}
