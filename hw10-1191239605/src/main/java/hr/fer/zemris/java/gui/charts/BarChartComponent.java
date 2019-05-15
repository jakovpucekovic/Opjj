package hr.fer.zemris.java.gui.charts;

import javax.swing.JComponent;

/**
 *	BarChartComponent TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;

	private BarChart chart;

	/**
	 * 	Constructs a new {@link BarChartComponent} with the given {@link BarChart}.
	 * 	@param chart {@link BarChart} to draw.
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
		
		initGUI();
	}
	
	private void initGUI() {
		
	}
	
	
	
}
