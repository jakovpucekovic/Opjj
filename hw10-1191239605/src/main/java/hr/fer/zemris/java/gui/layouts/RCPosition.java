package hr.fer.zemris.java.gui.layouts;

/**
 *	Class which models a position in {@link CalcLayout}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class RCPosition {

	/**Stores the row value of the position.*/
	private int row;
	/**Stores the column value of the position.*/
	private int column;
	
	/**
	 *	Constructs a new {@link RCPosition} from the given {@link String}.
	 *	@param str String in format "row,column".
	 *	@throws IllegalArgumentException If the string doesn't contain
	 *									 a valid {@link RCPosition}.
	 */
	public RCPosition(String str) {
		try {
			row = Integer.parseInt(str.substring(0, str.indexOf(',')));
			column = Integer.parseInt(str.substring(str.indexOf(',') + 1));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("String doesn't contain a valid position.");
		}
		if(row < 1 || column < 1) {
			throw new IllegalArgumentException("Row and column must be > 0.");
		}
	}
	
	/**
	 *	Constructs a new {@link RCPosition} with the given 
	 *	row and column.
	 *	@param row Row of the {@link RCPosition}. 
	 *	@param column Column of the {@link RCPosition}.
	 *	@throws IllegalArgumentException If the row or column are < 0.
	 */
	public RCPosition(int row, int column) {
		if(row < 1 || column < 1) {
			throw new IllegalArgumentException("Position must be > 0.");
		}
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
