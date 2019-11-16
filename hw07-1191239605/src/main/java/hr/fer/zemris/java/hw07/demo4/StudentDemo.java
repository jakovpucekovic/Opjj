package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *	Class StudentDemo which is used for practicing
 *	stream api by doing different filtering of a {@link List}
 *	of {@link StudentRecord}s.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class StudentDemo {

	/**
	 * 	Main method which solves the given problems and
	 * 	outputs the results to the console.
	 * 	@param args Not used here.
	 */
	public static void main(String[] args) {
		
		List<String> inputList = readStudenti();
		List<StudentRecord> records = convert(inputList);
		
		System.out.println("Zadatak 1");
		System.out.println("=========");
		System.out.println(vratiBodovaViseOd25(records));
		
		System.out.println("Zadatak 2");
		System.out.println("=========");
		System.out.println(vratiBrojOdlikasa(records));
		
		System.out.println("Zadatak 3");
		System.out.println("=========");
		List<StudentRecord> listaOdlikasa = vratiListuOdlikasa(records);
		listaOdlikasa.forEach(System.out::println);
		
		System.out.println("Zadatak 4");
		System.out.println("=========");
		List<StudentRecord> sortiranaListaOdlikasa = vratiSortiranuListuOdlikasa(records);
		sortiranaListaOdlikasa.forEach(System.out::println);
		
		System.out.println("Zadatak 5");
		System.out.println("=========");
		List<String> listaNepolozenih = vratiPopisNepolozenih(records);
		listaNepolozenih.forEach(System.out::println);
		
		System.out.println("Zadatak 6");
		System.out.println("=========");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		Set<Integer> keyset1 = mapaPoOcjenama.keySet();
		for(var key : keyset1) {
			System.out.println(key);
			for(var j : mapaPoOcjenama.get(key)) {
				System.out.println(j);
			}
		}
		
		System.out.println("Zadatak 7");
		System.out.println("=========");
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		Set<Integer> keyset2 = mapaPoOcjenama2.keySet();		
		for(var key : keyset2) {
			System.out.printf("%d ", key);
			System.out.println(mapaPoOcjenama2.get(key));
		}
	
		System.out.println("Zadatak 8");
		System.out.println("=========");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("true");
		for(var i : prolazNeprolaz.get(true)) {
			System.out.println(i);			
		}
		System.out.println("false");
		for(var i : prolazNeprolaz.get(false)) {
			System.out.println(i);			
		}	
	}
	
	/**
	 *	Method which calculates the number of students which scored more than
	 *	25 points in the midterm exam, final exam and exercises combined.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return Number of students which scored more than 25 points. 
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> list) {
		return list.stream()
			.mapToDouble(StudentRecord::getPointsSum)
			.filter(s -> s > 25)
			.count();
	}
	
	/**
	 *	Method which calculates the number of students which the grade 5.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return Number of students which got grade 5.
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> list) {
		return list.stream()
			.mapToLong(s -> s.getGrade())
			.filter(s -> s == 5)
			.count();
	}
	
	/**
	 *	Method which returns a {@link List} of {@link StudentRecord} of student
	 *	which got the grade 5.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return {@link List} of students which got grade 5.
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> list){
		return list.stream()
				   .filter(s -> s.getGrade() == 5)
				   .collect(Collectors.toList());
	}
	
	/**
	 *	Method which returns a {@link List} of {@link StudentRecord} of student
	 *	which got the grade 5 sorted so that the student with the highest points is
	 *	first and with the lowest points is last
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return {@link List} of students which got grade 5 sorted by points.
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> list){
		return list.stream()
				   .filter(s -> s.getGrade() == 5)
				   .sorted((s1,s2) -> Math.round(s2.getPointsSum() - s1.getPointsSum())) 
				   .collect(Collectors.toList());
	}
	
	/**
	 *	Method which returns a {@link List} of jmbags of students who got the 
	 *	grade 1.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return {@link List} of jmbags of students which got grade 1.
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> list){
		return list.stream()
				   .filter(s -> s.getGrade() == 1)
				   .map(StudentRecord::getJmbag)
				   .collect(Collectors.toList());
	}
	
	/**
	 *	Method which returns a {@link Map} which maps grades with {@link List}s
	 *	of {@link StudentRecord}s with that grade.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return {@link Map} of grades and {@link List}s of students with that grade.
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> list){
		return list.stream()
				   .collect(Collectors.groupingBy(StudentRecord::getGrade, Collectors.toList()));
	}
	
	/**
	 *	Method which returns a {@link Map} which maps grades with number of
	 *	students with that grade.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return {@link Map} of grades and number of students with that grade.
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> list){
		return list.stream()
				   .collect(Collectors.toMap(StudentRecord::getGrade, s -> 1, (a,b) -> a + b));
	}
	
	/**
	 *	Method which returns a {@link Map} which tells which students passed
	 * 	and which didn't.
	 *	@param list {@link List} of {@link StudentRecord}s.
	 *	@return {@link Map} of {@link List} of {@link StudentRecord}s which passed and didn't pass.
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> list){
		return list.stream()
				   .collect(Collectors.partitioningBy(s -> s.getGrade() > 1));	   
	}
	
	/**
	 * 	Private method which reads lines from studenti.txt
	 * 	@return List<String> in which every element is a line from studenti.txt 
	 */
	private static List<String> readStudenti() {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(
				Paths.get("src/main/resources/studenti.txt"),
				StandardCharsets.UTF_8); 
		} catch (IOException e) {
			System.out.println("Can't read file");
			System.exit(-1);
		}
		return lines;	
	}
	
	/**
	 * 	Method which constructs a new {@link List} of {@link StudentRecord}s
	 * 	from the given {@link List} of {@link String}s containing valid {@link StudentRecord}
	 * 	data.
	 * 	@param inputList {@link List} of {@link String} from which {@link StudentRecord} data can be extracted.
	 * 	@return {@link List} of {@link StudentRecord}s.
	 * 	@throws IllegalArgumentException If invalid data is given.
	 */
	private static List<StudentRecord> convert(List<String> inputList){
		List<StudentRecord> list = new ArrayList<>();
		for(var i : inputList) {
			if(i.equals("")) {
				continue;
			}
			list.add(new StudentRecord(i));
		}
		return list;
	}
	
}
