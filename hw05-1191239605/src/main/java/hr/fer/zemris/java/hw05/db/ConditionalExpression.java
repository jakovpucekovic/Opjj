package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

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

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(comparisonOperator, fieldGetter, stringLiteral);
	}

	/**
	 *	Indicates whether some other object is "equal to" this one.
	 *	Objects are considered equal if they have the same type of 
	 *	{@link ComparisonOperators}, {@link IFieldValueGetter}s and
	 *	{@link String} literals.
	 *	@param obj Object to check.
	 *	@return <code>true</code> if yes, <code>false</code> if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ConditionalExpression))
			return false;
		ConditionalExpression other = (ConditionalExpression) obj;
		return Objects.equals(comparisonOperator, other.comparisonOperator)
				&& Objects.equals(fieldGetter, other.fieldGetter) && Objects.equals(stringLiteral, other.stringLiteral);
	}

}
