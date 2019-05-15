package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *	BarChartDemo TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new BarChartDemo.
	 * 	TODO javadoc
	 */
	public BarChartDemo(BarChart chart, String path) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel label = new JLabel(path, SwingConstants.CENTER);
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		cp.add(label, BorderLayout.NORTH);
		
		
		JComponent jcomp = new BarChartComponent(chart);
		jcomp.setBackground(Color.RED);
		jcomp.setOpaque(true);
		jcomp.paintImmediately(50, 50, 100, 100);
		cp.add(jcomp, BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Wrong number of arguments given. Give path to a file.");
			return;
		}
		
		List<String> list = new ArrayList<String>();
		Scanner sc;
		try {
			sc = new Scanner(Paths.get(args[0]));
		} catch (IOException e) {
			System.out.println("File cannot be opened");
			return;
		}
		sc.useDelimiter("\n");
		for(int i = 0; i <= 5 ; i++) {
			list.add(sc.next());
		}
		sc.close();

		BarChart chart;
		try {
		List<XYValue> values = Arrays.stream(list.get(2).strip().split(" ")).map(x->new XYValue(x)).collect(Collectors.toList());
		chart = new BarChart(values, 
							 list.get(0),
							 list.get(1), 
							 Integer.parseInt(list.get(3)), 
							 Integer.parseInt(list.get(4)), 
							 Integer.parseInt(list.get(5)));

		} catch(Exception ex) {
			System.out.println("Some data is invalid.");
			return;
		}
		list.forEach(System.out::println);
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(chart, Paths.get(args[0]).toAbsolutePath().normalize().toString());
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		});
	}
	
}
