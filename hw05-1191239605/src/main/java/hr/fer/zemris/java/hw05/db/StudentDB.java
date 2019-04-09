package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		List<String> lines;
		try {
			lines = Files.readAllLines(
				Paths.get("src/main/resources/database.txt"),
				StandardCharsets.UTF_8); 
		} catch (IOException e) {
			System.out.println("Can't read file");
			return;
		}
		
		
		StudentDatabase db = new StudentDatabase(lines);
		
		List<StudentRecord> trueList = db.filter(new IFilter(){
			
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		});

		trueList.stream().forEach(System.out::println);
		
	}
	
	

}
