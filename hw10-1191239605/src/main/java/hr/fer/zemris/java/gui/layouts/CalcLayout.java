package hr.fer.zemris.java.gui.layouts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JPanel;

/**
 *	CalcLayout TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CalcLayout implements LayoutManager2 {

	private int preferredDistance;
	
	private static final int ROWS = 5;
	private static final int COLUMNS = 7;
	
	private Component[][] storedComponents;
	
	
	/**
	 * 	Constructs a new CalcLayout with the preferred
	 * 	distance of 0 pixels.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * 	Constructs a new CalcLayout with the given
	 * 	preferred distance.
	 * 	@param preferredDistance Distance in pixels
	 * 		   between rows and columns.
	 */
	public CalcLayout(int preferredDistance) {
		this.preferredDistance = preferredDistance;
		this.storedComponents = new Component[ROWS][COLUMNS];
	}
	
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		for(int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if(storedComponents[i][j] == comp) {
					storedComponents[i][j] = null;
					return;
				}
			}
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {	
		return layoutSize(parent, (Component c)-> {return c.getPreferredSize();});
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, (Component c) -> {return c.getMinimumSize();});
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {//TODO insets
		int parentWidth = Math.round((parent.getWidth() - 6 * preferredDistance) / (float)COLUMNS);
		int parentHeight = Math.round((parent.getHeight() - 4 * preferredDistance) / (float)ROWS);
		
		for(int i = 0; i < ROWS; ++i) {
			for(int j = 0; j < COLUMNS; ++j) {
				if(i == 0 && j < 5) {
					continue;
				}
				if(storedComponents[i][j] != null) {
					storedComponents[i][j].setSize(new Dimension(parentWidth, parentHeight));
					storedComponents[i][j].setBounds(parentWidth * j + preferredDistance * j,
													 parentHeight * i + preferredDistance * i, 
													 parentWidth, 
													 parentHeight);
				}
			}
		}
		if(storedComponents[0][0] != null) {
			storedComponents[0][0].setSize(new Dimension(parentWidth * 5 + 4 * preferredDistance, parentHeight));
			storedComponents[0][0].setBounds(0, 0, parentWidth * 5 + 4 * preferredDistance, parentHeight);
			
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.nonNull(comp); //TODO jel to okej pretpostavka?
		if(!constraints.getClass().equals(String.class) && !constraints.getClass().equals(RCPosition.class)) {
			throw new UnsupportedOperationException();
		}		
		
		if(constraints.getClass().equals(RCPosition.class)) {
			RCPosition rcPos = (RCPosition) constraints;
			if(rcPos.getRow() < 1 || rcPos.getRow() > 5 || rcPos.getColumn() < 1 || rcPos.getColumn() > 7 ||
			  (rcPos.getRow() == 1 && (rcPos.getColumn() > 1 && rcPos.getColumn() < 6 ))){
				   throw new CalcLayoutException("Illegal constraint given.");
			   }
			if(storedComponents[rcPos.getRow()-1][rcPos.getColumn()-1] != null) {
				throw new CalcLayoutException("Cannot set more than 1 Component on each space.");
			}
			
			storedComponents[rcPos.getRow()-1][rcPos.getColumn()-1] = comp;
		}
		
		if(constraints.getClass().equals(String.class)) {
			int row, column;
			String str = (String)constraints;
			try {
				row = Integer.parseInt(str.substring(0, str.indexOf(',')));
				column = Integer.parseInt(str.substring(str.indexOf(',')));
			} catch (NumberFormatException ex) {
				throw new UnsupportedOperationException("Constraint should be 2 integers.");
			}
			
			if(row < 1 || row > 5 || column < 1 || column > 7 ||
			  (row == 1 && (column > 1 && column < 6 ))){
				   throw new CalcLayoutException("Illegal constraint given.");
			   }
			if(storedComponents[row-1][column-1] != null) {
				throw new CalcLayoutException("Cannot set more than 1 Component on each space.");
			}
			
			storedComponents[row-1][column-1] = comp;
		}
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSize(target, (Component c)->{ return c.getMaximumSize();});
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {
//		prefWidth = 0;
//		prefHeight = 0;
	}

	
	/**
	 * 	Calculates the layout size of the given {@link Container} 
	 * 	based on the given {@link Function} which calculates the {@link Dimension}
	 * 	for each stored {@link Component}, e.g. if given getPreferredSize() {@link Function}
	 * 	this method will calculate the preferred layout size of the given {@link Container}.
	 * 	@param parent The {@link Container} to calculate the layout size of.
	 * 	@param funct {@link Function} which calculates the {@link Dimension} of each {@link Component}.
	 * 	@return Layout size of the given {@link Container}.
	 */
	private Dimension layoutSize(Container parent, Function<Component, Dimension> funct) {
		int maxHeight = 0;
		int maxWidth = 0;
		for(int i = 0; i < ROWS; ++i) {
			for(int j = 0; j < COLUMNS; ++j) {
				if(i == 0 && j < 5) {
					continue;
				}
				if(storedComponents[i][j] != null) {
					Dimension pref = funct.apply(storedComponents[i][j]);
					if(pref.width > maxWidth) {
						maxWidth = pref.width;
					}
					if(pref.height > maxHeight) {
						maxHeight = pref.height;
					}
				}
			}
		}
		if(storedComponents[0][0] != null) {
			Dimension pref = funct.apply(storedComponents[0][0]);
			if(5 * maxWidth + 4 * preferredDistance < pref.width) {
				maxWidth = pref.width / 5;
			}
			if(pref.height > maxHeight) {
				maxHeight = pref.height;
			}
		}
		return new Dimension(maxWidth * 7 + preferredDistance * 6, maxHeight * 5 + preferredDistance * 4);
	}
	
}
