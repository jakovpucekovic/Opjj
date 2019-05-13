package hr.fer.zemris.java.gui.layouts;

/**
 *	RCPosition TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class RCPosition {

	private int row;
	private int column;
	
	/**
	 *	Constructs a new {@link RCPosition} with the given 
	 *	row and column.
	 *	@param row Row of the {@link RCPosition}. 
	 *	@param column Column of the {@link RCPosition}.
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * 	Returns the row of the {@link RCPosition}.
	 * 	@return the row of the {@link RCPosition}.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 	Returns the column of the {@link RCPosition}.
	 * 	@return the column of the {@link RCPosition}.
	 */
	public int getColumn() {
		return column;
	}
}
