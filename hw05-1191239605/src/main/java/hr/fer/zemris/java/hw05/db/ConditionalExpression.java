package hr.fer.zemris.java.hw05.db;

/**
 *	Class which represents a conditional expression which can
 *	be executed to return <code>true</code> or <code>false</code>.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class ConditionalExpression {

	/**{@link IFieldValueGetter} used to get some data.*/
	private IFieldValueGetter fieldGetter;
	/**{@link String} to which the data which the 
	 * {@link IFieldValueGetter} returns should be compared.*/
	private String stringLiteral;
	/**{@link IComparisonOperator} which compares the two of the above.*/
	private IComparisonOperator comparisonOperator;
	
	/**
	 *  Constructs a new {@link ConditionalExpression} from the given values.
	 *  @param fieldGetter {@link IFieldValueGetter} to get data which should be compared.
	 *  @param stringLiteral Literal to compare.
	 *  @param comparisonOperator Operator used for comparing.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 *  Returns the {@link IFieldValueGetter}.
	 * 	@return The {@link IFieldValueGetter}.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 *  Returns the stringLiteral which should be compared.
	 *  @return The stringLiteral which should be compared.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * 	Returns the {@link IComparisonOperator} which does the comparing.
	 * 	@return The {@link IComparisonOperator} which does the comparing.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	
	
}
