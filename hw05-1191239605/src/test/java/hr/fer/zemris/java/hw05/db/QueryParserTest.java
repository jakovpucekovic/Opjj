package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.parser.utils.QueryParserException;

public class QueryParserTest {
	
	@Test
	public void testNotQuery() {
		assertThrows(QueryParserException.class , () -> new QueryParser("JMBAG = \"1234567890\"").isDirectQuery());
	}

	@Test
	public void testIsDirectQuery() {
		assertTrue(new QueryParser("jmbag = \"1234567890\"").isDirectQuery());
		assertFalse(new QueryParser("jmbag != \"1234567890\"").isDirectQuery());
		assertFalse(new QueryParser("jmbag <= \"1234567890\"").isDirectQuery());
		assertFalse(new QueryParser("jmbag LIKE \"1234567890\"").isDirectQuery());
		assertFalse(new QueryParser("jmbag = \"1234567890\" AND jmbag = \"1234567890\"").isDirectQuery());
		assertFalse(new QueryParser("firstName = \"Pero\"").isDirectQuery());
		assertFalse(new QueryParser("lastName = \"Peric\"").isDirectQuery());
	}
	
	@Test
	public void testGetQueriedJmbag() {
		assertEquals("1234567890", new QueryParser("jmbag = \"1234567890\"").getQueriedJMBAG());
		assertNotEquals("6666666666", new QueryParser("jmbag = \"1234567890\"").getQueriedJMBAG());
		assertThrows(IllegalStateException.class,()-> new QueryParser("jmbag != \"1234567890\"").getQueriedJMBAG());
	}
	
	@Test
	public void testGetSimpleQuery() {
		List<ConditionalExpression> list = new ArrayList<>();
		
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1234567890", ComparisonOperators.NOT_EQUALS));
		assertEquals(list, new QueryParser("jmbag != \"1234567890\"").getQuery());
		list.clear();
		
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1234567890", ComparisonOperators.LESS_OR_EQUAL));		 
		assertEquals(list, new QueryParser("jmbag <= \"1234567890\"").getQuery());
		list.clear();
		
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1234567890", ComparisonOperators.LIKE));
		assertEquals(list, new QueryParser("jmbag LIKE \"1234567890\"").getQuery());
		list.clear();
		
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pero", ComparisonOperators.EQUALS));
		assertEquals(list.get(0), new QueryParser("firstName = \"Pero\"").getQuery().get(0));
		list.clear();
		
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "Peric", ComparisonOperators.EQUALS));
		assertEquals(list, new QueryParser("lastName = \"Peric\"").getQuery());
	}                    
	@Test
	public void testGetMultipleQuery() {
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "1234567890", ComparisonOperators.EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Ab*cd", ComparisonOperators.LIKE));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "Stefano", ComparisonOperators.NOT_EQUALS));
		
		assertEquals(list, new QueryParser("jmbag = \"1234567890\" AND firstName LIKE \"Ab*cd\" anD lastName != \"Stefano\"").getQuery());
	}
	
}
