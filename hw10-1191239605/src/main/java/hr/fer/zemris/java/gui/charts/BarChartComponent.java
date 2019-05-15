package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

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
	}
	
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300); //TODO vracaj bolju dimeziju
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.WHITE);
		
		int topMargin = 10;
		int rightMargin = 10;
		int bottomMargin = 5;
		int leftMargin = 2;
		int textAxisDistance = 5;
		int textLabelDistance = 5;
		int width = getWidth();
		int height = getHeight();
		int fontHeight = g2d.getFontMetrics().getHeight();
		
		//draw bottom text
		g2d.drawString(chart.getxAxisDescription(), (width - g.getFontMetrics().stringWidth(chart.getxAxisDescription()))/2, height - bottomMargin);
		
		int xValuesStartHeight = height - bottomMargin - fontHeight - textLabelDistance;
	
		int xAxisHeight = height - bottomMargin - fontHeight -  textLabelDistance - fontHeight - textAxisDistance; 
	
		//draw left text
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(AffineTransform.getQuadrantRotateInstance(3));
		g2d.drawString(chart.getyAxisDescription(), -(height - g.getFontMetrics().stringWidth(chart.getyAxisDescription()))/2, leftMargin + fontHeight ); //TODO namjesti 1. argument
		g2d.setTransform(saveAT);
		
		int yValuesStartWidth = leftMargin + fontHeight + textLabelDistance;
		
		int yValueWidth = g2d.getFontMetrics().stringWidth(Integer.toString(chart.getMaxY()));
		
		int yAxisWidth = yValuesStartWidth + yValueWidth + textAxisDistance;
		
		//xAxis
		g2d.drawLine(yAxisWidth, xAxisHeight, width - rightMargin, xAxisHeight);
		//yAxis
		g2d.drawLine(yAxisWidth, xAxisHeight, yAxisWidth, topMargin);
		
		//draw rows
		int numberOfRows = (chart.getMaxY() - chart.getMinY()) / chart.getyFrequency();
		int rowHeight = (xAxisHeight - topMargin) / numberOfRows;
		for(int i = 1; i <= numberOfRows; ++i) {
			g2d.drawLine(yAxisWidth - 1, xAxisHeight - (i * rowHeight), width - rightMargin, xAxisHeight - (i * rowHeight));
		}
		
		//draw columns
		int numberOfColumns = chart.getValues().size();
		int columnWidth =(width - yAxisWidth - rightMargin) /numberOfColumns; 
		for(int i = 1; i <= numberOfColumns; ++i) {
			g2d.drawLine(yAxisWidth + (i * columnWidth), xAxisHeight, yAxisWidth + (i * columnWidth), topMargin);
		}
		
		//draw x values
		for(int i = 1; i <= numberOfColumns; ++i) {
			String number = Integer.toString(i);
			int offsetInColumn = (columnWidth - g2d.getFontMetrics().stringWidth(number))/2;
			g2d.drawString(number,yAxisWidth + ((i - 1) * columnWidth) + offsetInColumn , xValuesStartHeight);
		}
		
		//draw y values
		for(int i = 0; i <= numberOfRows; ++i) {
			String number = Integer.toString(chart.getMinY() + (i * chart.getyFrequency()));
			int stringWidth = g2d.getFontMetrics().stringWidth(number);
			g2d.drawString(number, yValuesStartWidth + (yValueWidth - stringWidth), xAxisHeight - (i * rowHeight));
		}
		
		//draw rectangles
		for(var value : chart.getValues()) {
			g2d.setColor(Color.ORANGE);
			g2d.fillRect(yAxisWidth + columnWidth * (value.getX() - 1) + 1,
						xAxisHeight - rowHeight * value.getY()/chart.getyFrequency() + 1,
						columnWidth - 1,
						rowHeight * value.getY()/chart.getyFrequency() - 1);
		}
	}
}
