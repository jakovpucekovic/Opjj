package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.hw05.db.parser.utils.QueryParserException;

/**
 *	Class which reads data from current file
 *	named "database.txt". If there are duplicate
 *	jmbag's or if the final grade is not between 1 
 *	and 5 terminates with the appropriate message.
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class StudentDB {

	/**
	 *	Main method which reads data and communicates with
	 *	the user.
	 *	@param args No parameters. 
	 */
	public static void main(String[] args) {

		StudentDatabase db = new StudentDatabase(readDatabase());
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\n");
		
		while(true) {
			System.out.print("> ");
			String userInput = sc.next();
			if(userInput.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			} else if(userInput.startsWith("query")) {
				if(userInput.equals("query")) {
					System.out.println("Query is invalid.");
					continue;
				}
				try {
					processQuery(userInput.substring(6), db);
				} catch(QueryParserException ex) {
					System.out.println("Query is invalid.");
				} catch(IllegalStateException ex) {
					System.out.println("Query is invalid.");
				}
			} else {
				System.out.println("Unknown command given.");
			}
		}
		sc.close();
	}
	
	/**
	 * 	Private method which reads lines from database.txt.
	 * 	@return List<String> in which every element is a line from database.txt 
	 */
	private static List<String> readDatabase() {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(
				Paths.get("src/main/resources/database.txt"),
				StandardCharsets.UTF_8); 
		} catch (IOException e) {
			System.out.println("Can't read file");
			System.exit(-1);
		}
		return lines;
	}

	/**
	 * 	Private method which processes the given query.
	 * 	First it parses it and then executes it and prints
	 * 	the result to System.out.
	 * 	@param query Query to process.
	 * 	@param db {@link StudentDatabase} to use.
	 */
	private static void processQuery(String query, StudentDatabase db) {
		QueryParser parser = new QueryParser(query);
		List<String> toPrint;
		boolean usingIndex = false;
		if(parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			if(r == null) {
				System.out.println("Records selected: 0");
				return;
			}
			toPrint = formatList(new ArrayList<StudentRecord>(List.of(r)));
			usingIndex = true;
		} else {
			List<StudentRecord> listOfRecords = getFromDatabase(parser.getQuery(), db);
			toPrint = formatList(listOfRecords);
		}
		if(toPrint == null) {
			System.out.println("Records selected: 0");
			return;
		}
		if(usingIndex) {
			System.out.println("Using index for record retrieval.");
		}
		toPrint.stream().forEach(System.out::println);
		System.out.println("Records selected: " + (toPrint.size() - 2));
	}
	
	/**
	 * 	Private method which returns a List of {@link StudentRecord}s
	 * 	from the given {@link StudentDatabase} which satisfy the given
	 * 	{@link ConditionalExpression}s.
	 * 	@param exprList The {@link List} of {@link ConditionalExpression}.
	 * 	@param db The {@link StudentDatabase} to use.
	 * 	@return List of {@link StudentRecord}s which satisfy the exprList.
	 */
	private static List<StudentRecord> getFromDatabase(List<ConditionalExpression> exprList, StudentDatabase db){
		List<StudentRecord> records = new ArrayList<>();
		for(StudentRecord r : db.filter(new QueryFilter(exprList))) {
			records.add(r);
		}
		return records;
	}
	
	/**
	 *	Private method which transforms the given {@link List} of 
	 *	{@link StudentRecord} into a {@link List} of formatted {@link String}s
	 *	which can then be printed to Sysyem.out.
	 *	@param records {@link List} of {@link StudentRecord}s to format.
	 *	@return {@link List} of {@link String}s which contains formatted {@link StudentRecord}s.
	 */
	private static List<String> formatList(List<StudentRecord> records) {
		if(records.isEmpty()) {
			return null;
		}
		List<String> formattedList = new ArrayList<>();
		int maxFirstNameLength = 0, maxLastNameLength = 0;
		for(StudentRecord r : records) {
			maxLastNameLength = r.getLastName().length() > maxLastNameLength ? r.getLastName().length() : maxLastNameLength;
			maxFirstNameLength = r.getFirstName().length() > maxFirstNameLength ? r.getFirstName().length() : maxFirstNameLength;
		}
		
		formattedList.add(getTopAndBottomLine(maxLastNameLength, maxFirstNameLength));
		for(StudentRecord r : records) {
			formattedList.add(getStudentRecordLine(r, maxLastNameLength, maxFirstNameLength));
		}
		formattedList.add(getTopAndBottomLine(maxLastNameLength, maxFirstNameLength));
		
		return formattedList;
		
	}
	
	/**
	 *	Method which formats a given {@link StudentRecord}.
	 *	@param recordToFormat The {@link StudentRecord} to format.
	 *	@param lastNameLength Length to which the lastName should be formatted.
	 *	@param firstNameLength Length to which the firstName should be formatted.
	 *	@return {@link String} which contains the formatted {@link StudentRecord}. 
	 */
	private static String getStudentRecordLine(StudentRecord recordToFormat ,int lastNameLength, int firstNameLength) {
		StringBuilder record = new StringBuilder();
		record.append("| ");
		record.append(recordToFormat.getJmbag());
		record.append(" | ");
		record.append(recordToFormat.getLastName()).append(" ".repeat(lastNameLength - recordToFormat.getLastName().length()));
		record.append(" | ");
		record.append(recordToFormat.getFirstName()).append(" ".repeat(firstNameLength - recordToFormat.getFirstName().length()));;
		record.append(" | ");
		record.append(recordToFormat.getFinalGrade());
		record.append(" |");
		
		return record.toString();
	}
	
	/**
	 *	Method which creates the top and bottom line in the "table"
	 *	to which the {@link StudentRecord}s are formatted.
	 *	@param lastNameLength Length to which the lastName should be formatted.
	 *	@param firstNameLength Length to which the firstName should be formatted.
	 *	@return {@link String} which contains the formatted top or bottom line.	 
	 */
	private static String getTopAndBottomLine(int lastNameLength, int firstNameLength) {
		StringBuilder topAndBottom = new StringBuilder();
		topAndBottom.append("+============+");
		topAndBottom.append("=".repeat(lastNameLength + 2));
		topAndBottom.append("+");
		topAndBottom.append("=".repeat(firstNameLength + 2));
		topAndBottom.append("+===+");
		return topAndBottom.toString();
	}

}
