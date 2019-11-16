package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *	Example class which visualizes the given data in a bar chart.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 	Constructs a new {@link BarChartDemo} with the given arguments.
	 * 	@param path Path to the file from which the {@link BarChart} is constructed.
	 * 	@throws IllegalArgumentException If path or found data isn't valid, or an error
	 * 									 occurred while reading data.
	 */
	public BarChartDemo(String path) {
		
		List<String> list;
		try {
			list = read(path);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while reading file.");
		}
		
		BarChart chart;
		List<XYValue> values = Arrays.stream(list.get(2).strip().split(" ")).map(x->new XYValue(x)).collect(Collectors.toList());
		chart = new BarChart(values, 
							 list.get(0),
							 list.get(1), 
							 Integer.parseInt(list.get(3)), 
							 Integer.parseInt(list.get(4)), 
							 Integer.parseInt(list.get(5)));
		
		initGUI(chart, path);
		
	}
	
	/**
	 *	Private method which reads the first 6 lines from
	 *	the file at the given path.
	 *	@param path Path to the file which should be read.
	 *	@return {@link List} of things read in each line.	
	 *	@throws IOException If there is an error while reading. 
	 *	@throws IllegalArgumentException If there is less than 6 lines.
	 */
	private List<String> read(String path) throws IOException {
		List<String> list = new ArrayList<String>();
		Scanner sc = new Scanner(Paths.get(path));
		sc.useDelimiter("\n");
		try {
			for(int i = 0; i <= 5 ; i++) {
				list.add(sc.next());
			}
		} catch(NoSuchElementException ex) {
			sc.close();
			throw new IllegalArgumentException("There isn't 6 lines.");
		}
		sc.close();
		return list;
	}
	
	/**
	 * 	Initializes the GUI by adding the label and component.
	 */
	private void initGUI(BarChart chart, String path) {
		setSize(500,500);
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel label = new JLabel(path, SwingConstants.CENTER);
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		cp.add(label, BorderLayout.NORTH);
		
		JComponent jcomp = new BarChartComponent(chart);
		jcomp.setBackground(Color.WHITE);
		jcomp.setOpaque(true);
		cp.add(jcomp, BorderLayout.CENTER);
	}
	
	
	/**
	 * 	Main method which runs the example.	
	 * 	@param args Path to the file from which the chart configuration should be read.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Wrong number of arguments given. Give path to a file.");
			return;
		}
	
		BarChartDemo demo;
		try {
			demo = new BarChartDemo(Paths.get(args[0]).toAbsolutePath().normalize().toString());
		} catch(IllegalArgumentException ex) {
			System.out.println("Give path to a file with valid data.");
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = demo;
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		});
	}
	
}
