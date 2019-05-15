package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.function.Function;

/**
 *	A layout manager which allows the placing of components
 *	anywhere on a grid whose size is specified by the ROWS and COLUMNS
 *	constants.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class CalcLayout implements LayoutManager2 {

	/**Distance between components in pixels.*/
	private int preferredDistance;
	
	/**Number of rows in the layout.*/
	private static final int ROWS = 5;
	
	/**Number of columns in the layout.*/
	private static final int COLUMNS = 7;
	
	/**Stores the components which are added to the layout.*/
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
	@Override//TODO napisi ljepse
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int width = Math.round((parent.getWidth() - 6 * preferredDistance - insets.left - insets.right) / (float)COLUMNS);
		int height = Math.round((parent.getHeight() - 4 * preferredDistance - insets.top - insets.bottom) / (float)ROWS);
		
		int actualWidth = width * COLUMNS + 6 * preferredDistance + insets.left + insets.right;
		int actualHeight = height * ROWS + 4 * preferredDistance + insets.top + insets.bottom;
		
		int realExcessWidth = parent.getWidth() - actualWidth;
		int excessHeight = parent.getHeight() - actualHeight;
				
		int heightPos = insets.top;
		for(int i = 0; i < ROWS; ++i) {
		
			int heightToSet = height;
			if(i % 2 == 0 && excessHeight != 0) {
				heightToSet += excessHeight > 0 ? 1 : -1;
				if(excessHeight < 0) excessHeight++;
				if(excessHeight > 0) excessHeight--;
			}

			int widthPos = insets.left;
			int excessWidth = realExcessWidth;
			for(int j = 0; j < COLUMNS; ++j) {
				int widthToSet = width;
				if(j % 2 == 0 && excessWidth != 0) {
					widthToSet += excessWidth > 0 ? 1 : -1;
					if(excessWidth < 0) excessWidth++;
					if(excessWidth > 0) excessWidth--;
				}
				if(i == 0 && j < 4) {
					widthPos += widthToSet + preferredDistance;
					continue;
				}
				if(i == 0 && j == 5) {
					if(storedComponents[0][0] != null) {
						storedComponents[0][0].setSize(new Dimension(widthPos - preferredDistance, heightToSet));
						storedComponents[0][0].setBounds(0, 0, widthPos - preferredDistance, heightToSet);	
					}
				}
				
				if(storedComponents[i][j] != null) {
					storedComponents[i][j].setSize(new Dimension(widthToSet, heightToSet));
					storedComponents[i][j].setBounds(widthPos,
													 heightPos,
													 widthToSet, 
													 heightToSet);
				}
				widthPos += widthToSet + preferredDistance;
			}
			heightPos += heightToSet + preferredDistance;
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints.getClass().equals(String.class)) {
			try {
				addLayoutComponent(comp, new RCPosition((String) constraints));
			} catch(IllegalArgumentException ex) {
				throw new UnsupportedOperationException("Given constraint isn't RCPosition.");
			}
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
			return;
		}
		throw new UnsupportedOperationException("Given constraint isn't RCPosition.");
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
		Insets insets = parent.getInsets();
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
			int actualWidth = (pref.width - 4 * preferredDistance) / 5;
			if(actualWidth > maxWidth) {
				maxWidth = actualWidth;
			}
			if(pref.height > maxHeight) {
				maxHeight = pref.height;
			}
		}
		return new Dimension(maxWidth * 7 + preferredDistance * 6 + insets.left + insets.right, maxHeight * 5 + preferredDistance * 4 + insets.bottom + insets.top);
	}
	
}
