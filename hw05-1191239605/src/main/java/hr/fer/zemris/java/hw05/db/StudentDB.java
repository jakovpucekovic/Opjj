package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB {

	public static void main(String[] args) {

		List<String> list;

		try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/database.txt"))) {
			/*Removes all lines with nothing in them and collects all lines to a List.*/
			list = stream.filter(str -> !str.equals("")).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Can't read file");
			return;
		}
		
		StudentDatabase db = new StudentDatabase(list);
		
		List<StudentRecord> trueList = db.filter(new IFilter(){
			
			@Override
			public boolean accepts(StudentRecord record) {
				return true;
			}
		});

		trueList.stream().forEach(System.out::println);
		
	}
	
	

}
