package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {	
	
	@Test
	public void test1(){
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1111122222", ComparisonOperators.GREATER));
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "5555566666", ComparisonOperators.LESS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pe*", ComparisonOperators.LIKE));
				
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("2222233333\tPeric\tPero\t2")));
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("5555555555\tIvic\tPerica\t3")));

		assertFalse(new QueryFilter(list).accepts(new StudentRecord("0000000000\tPeric\tPero\t2")));
		assertFalse(new QueryFilter(list).accepts(new StudentRecord("5555566666\tPeric\tPetar\t5")));
	}
	
	@Test
	public void test2(){
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "5555566666", ComparisonOperators.LESS));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "*ic", ComparisonOperators.LIKE));
			
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("2222233333\tPeric\tPero\t2")));
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("5555555555\tIvic\tPerica\t3")));

		assertFalse(new QueryFilter(list).accepts(new StudentRecord("0000000000\tPero\tPero\t2")));
		assertFalse(new QueryFilter(list).accepts(new StudentRecord("5555566666\tPeric\tPetar\t5")));
	}

	@Test
	public void test3(){
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Ma*", ComparisonOperators.LIKE));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "*ro", ComparisonOperators.LIKE));
		
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("2222233333\tEro\tMarko\t2")));
		assertTrue(new QueryFilter(list).accepts(new StudentRecord("5555555555\tZadro\tMarija\t3")));

		assertFalse(new QueryFilter(list).accepts(new StudentRecord("0000000000\tPero\tPero\t2")));
		assertFalse(new QueryFilter(list).accepts(new StudentRecord("5555566666\tPeric\tMarko\t5")));		
	}
	
}