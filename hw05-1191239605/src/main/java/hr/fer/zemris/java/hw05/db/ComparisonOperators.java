package hr.fer.zemris.java.hw05.db;

public class ComparisonOperators {

	public static final IComparisonOperator LESS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) < 0;
		}
	};
	
	public static final IComparisonOperator LESS_OR_EQUAL = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) <= 0;
		}
	};

	public static final IComparisonOperator GREATER = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) > 0;
		}
	};
	
	public static final IComparisonOperator GREATER_OR_EQUAL = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) >= 0;
		}
	};
	
	public static final IComparisonOperator EQUALS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) == 0;
		}
	};
	
	public static final IComparisonOperator NOT_EQUALS = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			return value1.compareTo(value2) != 0;
		}
	};
	
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
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
