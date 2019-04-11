package hr.fer.zemris.java.hw05.db;

/**
 *  Class which implements {@link IComparisonOperator} used
 *  for comparing {@link String}s in {@link StudentDatabase}.
 *   
 *  @author Jakov Pucekovic
 *  @version 1.0
 */
public class ComparisonOperators {

	/**
	 *  {@link IComparisonOperator} which signals that the first {@link String}
	 *  is smaller than the second {@link String}.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> {return v1.compareTo(v2) < 0;};
	
	/**
	 *  {@link IComparisonOperator} which signals that the first {@link String}
	 *  is smaller than or equal to the second {@link String}.
	 */
	public static final IComparisonOperator LESS_OR_EQUAL = (v1, v2) -> {return v1.compareTo(v2) <= 0;};

	/**
	 *  {@link IComparisonOperator} which signals that the first {@link String}
	 *  is greater than the second {@link String}.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> {return v1.compareTo(v2) > 0;};
	
	/**
	 *  {@link IComparisonOperator} which signals that the first {@link String}
	 *  is greater than or equal to the second {@link String}.
	 */
	public static final IComparisonOperator GREATER_OR_EQUAL = (v1, v2) -> {return v1.compareTo(v2) >= 0;};
	
	/**
	 *  {@link IComparisonOperator} which signals that the first {@link String}
	 *  is equal to the second {@link String}.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> {return v1.compareTo(v2) == 0;};
	
	/**
	 *  {@link IComparisonOperator} which signals that the first {@link String}
	 *  is not equal to the second {@link String}.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> {return v1.compareTo(v2) != 0;};
	
	/**
	 *	{@link IComparisonOperator} which signals that the first {@link String}
	 * 	is like the second {@link String}. A {@link String} is like another {@link String}
	 * 	if it has the same characters at the same places except for "*" which can be any 
	 * 	number of any characters. E.g. "abcde" is like "ab*e", but "abcde" is not like "ab*a".
	 * 	Only 1 use of "*" in the second {@link String} is permitted.
	 * 	@throws IllegalArgumentException If the second {@link String} contains more than 1 "*".
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value1.equals(value2)) {
				return true;
			}
			if(value2.equals("")) {
				return false;
			}
			/*If the first and last occurrence of the * aren't the same,
			  there is more than 1 * character.*/
			if(value2.indexOf("*") != value2.lastIndexOf("*")) {
				throw new IllegalArgumentException("Cannot contain more than 1 wildcard character.");
			}
			
			return value1.length() >= value2.length() - 1 &&
				   value1.startsWith(value2.substring(0, value2.indexOf("*"))) &&
				   value1.endsWith(value2.substring(value2.indexOf("*") + 1));
		}
	};
	
}
