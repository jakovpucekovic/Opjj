package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 *	Class which represents a component that can be added
 *	as a {@link JComponent} to a {@link Container} to display
 *	a bar chart.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;

	/**{@link BarChart} which holds the information about the chart.*/
	private BarChart chart;

	/**
	 * 	Constructs a new {@link BarChartComponent} with the given {@link BarChart}.
	 * 	@param chart {@link BarChart} to draw.
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		Insets insets = getInsets();
		
		int topMargin = 10 + insets.top;
		int rightMargin = 10 + insets.right;
		int bottomMargin = 5 + insets.bottom;
		int leftMargin = 2 + insets.left;
		int textAxisDistance = 5;
		int textLabelDistance = 5;
		int width = getWidth();
		int height = getHeight();
		int fontHeight = g2d.getFontMetrics().getHeight();
		
		//draw bottom text
		g2d.drawString(chart.getxAxisDescription(), (width - g.getFontMetrics().stringWidth(chart.getxAxisDescription()))/2, height - bottomMargin);
				
		//draw left text
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(AffineTransform.getQuadrantRotateInstance(3));
		int textLen = g.getFontMetrics().stringWidth(chart.getyAxisDescription());
		g2d.drawString(chart.getyAxisDescription(), -textLen +(textLen - height)/2, leftMargin + fontHeight );
		g2d.setTransform(saveAT);
		
		//numbers on the y-axis start at this width
		int yValuesStartWidth = leftMargin + fontHeight + textLabelDistance;
		//width of the numbers on the y-axis
		int yValueWidth = g2d.getFontMetrics().stringWidth(Integer.toString(chart.getMaxY()));
		//width of the y-axis(distance from left border)
		int yAxisWidth = yValuesStartWidth + yValueWidth + textAxisDistance;
		//height of the x-axis
		int xAxisHeight = height - bottomMargin - fontHeight -  textLabelDistance - fontHeight - textAxisDistance; 
		//numbers under the x-axis starts at this height
		int xValuesStartHeight = height - bottomMargin - fontHeight - textLabelDistance;
		
		//draw xAxis
		g2d.drawLine(yAxisWidth, xAxisHeight, width - rightMargin, xAxisHeight);
		g2d.fillPolygon(new int[] {yAxisWidth, yAxisWidth - 5, yAxisWidth + 5}, 
					   new int[] {topMargin - 5, topMargin, topMargin}, 
					   3);
		//draw yAxis
		g2d.drawLine(yAxisWidth, xAxisHeight, yAxisWidth, topMargin);
		g2d.fillPolygon(new int[] {width - rightMargin + 5, width - rightMargin, width - rightMargin},
					   new int[] {xAxisHeight, xAxisHeight + 5, xAxisHeight - 5}, 
					   3);
		
		//draw rows
		int numberOfRows = (chart.getMaxY() - chart.getMinY()) / chart.getyFrequency();
		int rowHeight = (xAxisHeight - topMargin) / numberOfRows;
		for(int i = 1; i <= numberOfRows; ++i) {
			g2d.drawLine(yAxisWidth - 3, xAxisHeight - (i * rowHeight), width - rightMargin, xAxisHeight - (i * rowHeight));
		}
		
		//draw columns
		int numberOfColumns = chart.getValues().size();
		int columnWidth =(width - yAxisWidth - rightMargin) /numberOfColumns; 
		for(int i = 1; i <= numberOfColumns; ++i) {
			g2d.drawLine(yAxisWidth + (i * columnWidth), xAxisHeight, yAxisWidth + (i * columnWidth), topMargin);
		}	
		
		//draw x values
		for(int i = 1; i <= numberOfColumns; ++i) {
			String number = Integer.toString(chart.getValues().get(i-1).getX());
//			String number = Integer.toString(i);
			int offsetInColumn = (columnWidth - g2d.getFontMetrics().stringWidth(number))/2;
			g2d.drawString(number,yAxisWidth + ((i - 1) * columnWidth) + offsetInColumn , xValuesStartHeight);
		}
		
		//draw y values
		for(int i = 0; i <= numberOfRows; ++i) {
			String number = Integer.toString(chart.getMinY() + (i * chart.getyFrequency()));
			int stringWidth = g2d.getFontMetrics().stringWidth(number);
			g2d.drawString(number, yValuesStartWidth + (yValueWidth - stringWidth), xAxisHeight - (i * rowHeight) + g2d.getFontMetrics().getHeight()/5);
		}
		
		//draw rectangles
		g2d.setColor(Color.ORANGE);
		for(int i = 0; i < chart.getValues().size(); ++i) {
			g2d.fillRect(yAxisWidth + columnWidth * i + 1,
						xAxisHeight - rowHeight * chart.getValues().get(i).getY()/chart.getyFrequency() + 1,
						columnWidth - 1,
						rowHeight * chart.getValues().get(i).getY()/chart.getyFrequency() - 1);
		}
	}
}
