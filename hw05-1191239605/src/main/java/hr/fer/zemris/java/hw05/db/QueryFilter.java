package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 *	Class QueryFilter which decides if a {@link StudentRecord} 
 *	satisfies the given {@link ConditionalExpression}s.
 * 
 * 	@author Jakov Pucekovic
 *	@version 1.0
 */
public class QueryFilter implements IFilter{

	/**
	 * List of {@link ConditionalExpression}s used to determine
	 * whether a {@link StudentRecord} is accepted.
	 */
	private List<ConditionalExpression> list;
	
	/**
	 *	Constructs a new {@link QueryFilter} with the given 
	 *	{@link ConditionalExpression}s.
	 *	@param list List of {@link ConditionalExpression}s. 
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	/**
	 * 	Method which decides if a {@link StudentRecord} satisfies
	 * 	the given {@link ConditionalExpression}s.
	 * 	@param record The {@link StudentRecord} to evaluate.	
	 * 	@return <code>true</code> if yes, <code>false</code> if not.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(var condExpression : list) {
			if(!condExpression.getComparisonOperator().satisfied(
					condExpression.getFieldGetter().get(record),
					condExpression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}
	
}
