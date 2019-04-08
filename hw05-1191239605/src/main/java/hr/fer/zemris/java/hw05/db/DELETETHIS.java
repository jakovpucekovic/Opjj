package hr.fer.zemris.java.hw05.db;

public class DELETETHIS {

	public static void main(String[] args) {
		
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE);
	
		StudentRecord record = getSomehowOneRecord();
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		System.out.println(recordSatisfies);
	}
	
	
	public static StudentRecord getSomehowOneRecord() {
		return new StudentRecord("1234567890", "Peric", "Pero", 3);
	}
}
