package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 *	Class which models a bar chart.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BarChart {

	/**Stores the values of the chart.*/
	private List<XYValue> values;
	
	/**Description of the x axis.*/
	private String xAxisDescription;
	
	/**Description of the y axis.*/
	private String yAxisDescription;
	
	/**Starting point for the y axis.*/
	private int minY;
	
	/**Ending point for the y axis.*/
	private int maxY;
	
	/**Frequency of the points on the y axis.*/
	private int yFrequency;

	/**
	 * 	Constructs a new {@link BarChart} with the given parameters.
	 * 	@param values {@link List} of values on the chart.
	 * 	@param xAxisDescription Text describing the x axis.
	 * 	@param yAxisDescription Text describing the y axis.
	 * 	@param minY Starting point for the y axis.
	 * 	@param maxY Ending point for the y axis.
	 * 	@param yFrequency Frequency of the points on the y axis.
	 * 	@throws IllegalArgumentException If minY < 0, maxY <= minY or values contains
	 * 									 a value whose y coordinate is < minY.
	 */
	public BarChart(List<XYValue> values, String xAxisDescription, String yAxisDescription, int minY, int maxY,
			int yFrequency) {
		if(minY < 0 || maxY <= minY) {
			throw new IllegalArgumentException();
		}
		for(var value : values) {
			if(value.getY() < minY) {
				throw new IllegalArgumentException();
			}
		}
		this.values = values;
		this.xAxisDescription = xAxisDescription;
		this.yAxisDescription = yAxisDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.yFrequency = yFrequency;
	}

	/**
	 * 	Returns the values of the {@link BarChart}.
	 * 	@return the values of the {@link BarChart}.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * 	Returns the xAxisDescription of the {@link BarChart}.
	 * 	@return the xAxisDescription of the {@link BarChart}.
	 */
	public String getxAxisDescription() {
		return xAxisDescription;
	}

	/**
	 * 	Returns the yAxisDescription of the {@link BarChart}.
	 * 	@return the yAxisDescription of the {@link BarChart}.
	 */
	public String getyAxisDescription() {
		return yAxisDescription;
	}

	/**
	 * 	Returns the minY of the {@link BarChart}.
	 * 	@return the minY of the {@link BarChart}.
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * 	Returns the maxY of the {@link BarChart}.
	 * 	@return the maxY of the {@link BarChart}.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * 	Returns the yFrequency of the {@link BarChart}.
	 * 	@return the yFrequency of the {@link BarChart}.
	 */
	public int getyFrequency() {
		return yFrequency;
	}	
	
}
