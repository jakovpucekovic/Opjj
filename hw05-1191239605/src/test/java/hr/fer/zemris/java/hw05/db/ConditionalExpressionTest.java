package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {
	
	@Test
	public void testEquals() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.EQUALS);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.EQUALS);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.EQUALS);
		StudentRecord recordEquals = new StudentRecord("1111122222\tPeric\tPero\t3");
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordEquals), exprFirstName.getStringLiteral()));

		assertFalse(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertFalse(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertFalse(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	}
	
	@Test
	public void testNotEquals() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.NOT_EQUALS);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.NOT_EQUALS);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.NOT_EQUALS);
		StudentRecord recordEquals = new StudentRecord("1111122222\tPeric\tPero\t3");
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertFalse(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordEquals), exprJmbag.getStringLiteral()));
		assertFalse(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordEquals), exprLastName.getStringLiteral()));
		assertFalse(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordEquals), exprFirstName.getStringLiteral()));

		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	}
	
	@Test
	public void testLess() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.LESS);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.LESS);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.LESS);
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertFalse(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	}
	
	@Test
	public void testLessOrEqual() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.LESS_OR_EQUAL);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.LESS_OR_EQUAL);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.LESS_OR_EQUAL);
		StudentRecord recordEquals = new StudentRecord("1111122222\tPeric\tPero\t3");
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordEquals), exprFirstName.getStringLiteral()));
		
		assertFalse(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	}
	
	@Test
	public void testGreater() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.GREATER);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.GREATER);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.GREATER);
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertFalse(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertFalse(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	}
	
	@Test
	public void testGreaterOrEqual() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.GREATER_OR_EQUAL);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.GREATER_OR_EQUAL);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.GREATER_OR_EQUAL);
		StudentRecord recordEquals = new StudentRecord("1111122222\tPeric\tPero\t3");
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordEquals), exprFirstName.getStringLiteral()));
		
		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertFalse(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertFalse(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	}
	
	@Test
	public void testLike() {
		ConditionalExpression exprJmbag = new ConditionalExpression(FieldValueGetters.JMBAG, "11111222*", ComparisonOperators.LIKE);
		ConditionalExpression exprLastName = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Per*", ComparisonOperators.LIKE);
		ConditionalExpression exprFirstName = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pe*", ComparisonOperators.LIKE);
		StudentRecord recordEquals = new StudentRecord("1111122222\tPeric\tPero\t3");
		StudentRecord recordLike = new StudentRecord("1111122233\tPerisic\tPetra\t4");
		StudentRecord recordNotEquals = new StudentRecord("3333344444\tIvankovic\tIvan\t5");
		
		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordEquals), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordEquals), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordEquals), exprFirstName.getStringLiteral()));

		assertTrue(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordLike), exprJmbag.getStringLiteral()));
		assertTrue(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordLike), exprLastName.getStringLiteral()));
		assertTrue(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordLike), exprFirstName.getStringLiteral()));
		
		assertFalse(exprJmbag.getComparisonOperator().satisfied(exprJmbag.getFieldGetter().get(recordNotEquals), exprJmbag.getStringLiteral()));
		assertFalse(exprLastName.getComparisonOperator().satisfied(exprLastName.getFieldGetter().get(recordNotEquals), exprLastName.getStringLiteral()));
		assertFalse(exprFirstName.getComparisonOperator().satisfied(exprFirstName.getFieldGetter().get(recordNotEquals), exprFirstName.getStringLiteral()));
	
	}
	
}
