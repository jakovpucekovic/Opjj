package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.parser.utils.QueryLexer;
import hr.fer.zemris.java.hw05.db.parser.utils.QueryLexerException;
import hr.fer.zemris.java.hw05.db.parser.utils.QueryParserException;
import hr.fer.zemris.java.hw05.db.parser.utils.QueryToken;
import hr.fer.zemris.java.hw05.db.parser.utils.QueryTokenType;

/**
 *	Class which represents a simple query parser.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class QueryParser {

	
	
	/**Lexer used for grouping of character.*/
	private QueryLexer lexer;
	/**ArrayList used to store grouped tokens.*/
	private ArrayList<QueryToken> array;
	
	/**
	 *	Constructs a new {@link QueryParser} with the given value.
	 *	@param queryString {@link String} which needs to be parsed. 
	 */
	public QueryParser(String queryString) {
		array = new ArrayList<>();
		lexer = new QueryLexer(queryString.stripTrailing());
		/*Fill the array with the QueryTokens of the given queryString.*/
		try {
			while(lexer.hasNext()) {
				array.add(lexer.nextToken());
			}
		} catch(QueryLexerException ex) {
			throw new QueryParserException("Given query cannot be parsed.");
		}
	}
	
	/**
	 *	Checks if the query is a direct query. Direct queries
	 *  are made only of attribute name "jmbag", operator "=" and a 
	 *  string literal.
	 *  @return <code>true</code> if the query is a direct query, <code>false</code> if not.
	 */
	public boolean isDirectQuery() {
		if(array.size() != 3) {
			return false;
		}
		if((array.get(0).getType() == QueryTokenType.ATTRIBUTE_NAME) && (array.get(0).getContent().equals("jmbag")) &&
		   array.get(1).getContent().equals("=") &&
		   array.get(2).getType() == QueryTokenType.STRING_LITERAL) {
			return true;
		}
		return false;
	}
	
	/**
	 *	Returns the jmbag given in the direct query.
	 *	@return The string literal given in the direct query which equals jmbag.
	 *	@throws IllegalStateException If the query is not a direct query. 
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Query isn't a direct query.");
		}
		return array.get(2).toString();
	}
	/**
	 * 	Method creates a {@link List} of {@link ConditionalExpression}s which
	 * 	represent the parsed query.
	 * 	@return {@link List} of {@link ConditionalExpression}s.
	 * 	@throws QueryParserException If {@link ConditionalExpression}s can't be 
	 * 								 made from the parsed query.
	 */
	public List<ConditionalExpression> getQuery(){
		List<ConditionalExpression> list = new ArrayList<>();
		int i = 0;
		/*Each expression has 3 parts.*/
		while(i + 2 < array.size()) {
			var name = array.get(i);
			var operator = array.get(i + 1);
			var literal = array.get(i + 2);
			
			if(name.getType() == QueryTokenType.ATTRIBUTE_NAME &&
			   operator.getType() == QueryTokenType.OPERATOR &&
			   literal.getType() == QueryTokenType.STRING_LITERAL) {
				
				IFieldValueGetter getter;
				switch (name.getContent()) {
				case "jmbag"   : getter = FieldValueGetters.JMBAG; 		break;
				case "lastName": getter = FieldValueGetters.LAST_NAME;  break;
				case "firstName": getter = FieldValueGetters.FIRST_NAME; break;
				default  	   : throw new QueryParserException();		
				}
				
				IComparisonOperator comparisonOperator;
				switch(operator.getContent()) {
					case "="   : comparisonOperator = ComparisonOperators.EQUALS;			break;
					case "!="  : comparisonOperator = ComparisonOperators.NOT_EQUALS; 	  	break;
					case "<"   : comparisonOperator = ComparisonOperators.LESS; 			break;
					case "<="  : comparisonOperator = ComparisonOperators.LESS_OR_EQUAL;	break;
					case ">"   : comparisonOperator = ComparisonOperators.GREATER; 	      	break;
					case ">="  : comparisonOperator = ComparisonOperators.GREATER_OR_EQUAL;	break;
					case "LIKE": comparisonOperator = ComparisonOperators.LIKE; 		  	break;
					default	   : throw new QueryParserException();
				}

				list.add(new ConditionalExpression(getter, literal.toString(), comparisonOperator));
				i +=3;
				if(i >= array.size() || array.get(i).getType() != QueryTokenType.AND) {
					break;
				}
				i++;
			}
		}
		if(list.size() == 0 || array.get(array.size() - 1).getType() == QueryTokenType.AND) {
			throw new QueryParserException("Cannot parse query.");
		}
		return list;
	}
	
}
