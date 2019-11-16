package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class StudentDatabaseTest {
	
	private List<String> readFile () {
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
	
	@Test
	public void testForJMBAG() {
		StudentDatabase db = new StudentDatabase(readFile());
		assertEquals(new StudentRecord("0000000001	Akšamović	Marin	2"), db.forJMBAG("0000000001"));
	}
	
	@Test
	public void testQueryAllTrue() {
		List<String> list = readFile();
		StudentDatabase db = new StudentDatabase(list);
		
		List<String> listWithoutSpaces = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals("")) {
				continue;
			}
			listWithoutSpaces.add(list.get(i).replaceAll("\\s{2,}|\\t", " "));
		}
		
		List<StudentRecord> filteredList = db.filter( StudentRecord -> {return true;});
		assertEquals(listWithoutSpaces.size(), filteredList.size());
		for(int i = 0, s = filteredList.size(); i < s; i++) {
			assertEquals(listWithoutSpaces.get(i), filteredList.get(i).toString());			
		}
	}
	
	@Test
	public void testQueryAllFalse() {
		StudentDatabase db = new StudentDatabase(readFile());
		List<StudentRecord> filteredList = db.filter( StudentRecord -> {return false;});
		assertEquals(0, filteredList.size());
		
	}
}
