package hr.fer.zemris.java.gui.charts;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JFrame;

/**
 *	BarChartDemo TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
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
		List<XYValue> values = Arrays.stream(list.get(2).strip().split("")).map(x->new XYValue(x)).collect(Collectors.toList());
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
	}
	
}
