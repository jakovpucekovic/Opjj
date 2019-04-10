package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
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

//		trueList.stream().forEach(System.out::println);
		
		mainloop();
		
	}
	
	private static void mainloop() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String userInput = sc.next();
			if(userInput.equals("end")) {
				System.out.println("Goodbye!");
				break;
			} else if(userInput.startsWith("query")) {
				System.out.println("process query"); //TODO
			} else {
				System.out.println("Unknown command given.");
			}
		}
		sc.close();
	}
	
	
	//TODO obraduje query
	private void obradiQuery() {
		
	}
	
	//TODO vraca formatirani output ili ispisuje
	private void formattedOutput() {
		
		
	}
	
	

}
