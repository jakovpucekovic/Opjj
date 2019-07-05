package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Scanner;

/**
 *	Main class which handles communication with the user and execution
 *	of different commands the user can submit.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Konzola {
	
	/**{@link Vokabular} with which we're working.*/
	private static Vokabular voc;

	/**Flag which signals if the first query has been entered.*/
	private static boolean queryEntered = false;
	
	/**Sorted list which holds the {@link Dokument}s and their similarity to the last executed query.*/
	private static List<Entry<Dokument,Double>> resultList;
	
	/**
	 * 	Main method which communicates with the user.
	 * 	@param args Path to directory which contains the documents based on which the {@link Vokabular}
	 * 				is built.
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Ocekivan je path do direktorija koji sadrzi clanke.");
			return;
		}
		
		try {
			voc = new Vokabular(args[0]);
			System.out.println(voc.getIDF());
			System.out.println("Velicina rjecnika je " + voc.getWordListSize() + " rijeci.");
		} catch(IOException ex) {
			System.out.println("Doslo je do pogreske pri ucitavanju vokabulara.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter command > ");
		while(sc.hasNextLine()) {
			String input = sc.nextLine();
			if(input.equals("exit")) {
				System.out.println("Bye.");
				sc.close();
				return;
			} else if(input.equals("results")) {
				results();
			} else if(input.matches("type \\d+")) {
				type(Integer.parseInt(input.split(" ")[1]));
			} else if(input.startsWith("query ")) {
				queryEntered = true;
				query(input.substring(5));
			} else {
				System.out.println("Nepoznata naredba.");
			}
			System.out.print("Enter command > ");
		}
		sc.close();
	}
	
	/**
	 * 	Executes the command <code>query</code> which prints the query, calculates the results
	 * 	and prints the best 10.
	 * 	@param input User input after the word query.	
	 */
	private static void query(String input) {
		String[] params = input.trim().split(" ");
		List<String> validParamWords = new ArrayList<>();
		for(var word : params) {
			if(voc.hasWord(word)) {
				validParamWords.add(word);
			}
		}
	
		//print query
		System.out.print("Query is: [");
		for(int i = 0, s = validParamWords.size(); i < s ; ++i) {
			System.out.print(validParamWords.get(i));
			if(i != s-1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
		
		Map<Dokument,Double> results = executeQuery(validParamWords);
		
		//create resultList
		resultList = new ArrayList<>(results.entrySet());
		resultList = resultList.stream()
				   .filter(x -> x.getValue() != 0)
				   .sorted((a,b) -> Double.compare(b.getValue(), a.getValue()))
				   .collect(Collectors.toList());
		
		//print results
		System.out.println("Najboljih 10 rezultata:");
		for(int i = 0, s = resultList.size(); i < s; ++i) {
			if(i == 10) {
				break;
			}
			System.out.println(String.format("[%d](%.4f) %s", i, resultList.get(i).getValue(), resultList.get(i).getKey().getName()));
		}
	}
	
	/**
	 *  Private method which executes the query and caluclates the results of the query.
	 *  @param queryWords {@link List} of the words in the query.
	 * 	@return {@link Map} of {@link Dokument} and their similarity with the given query.	
	 */
	private static Map<Dokument,Double> executeQuery(List<String> queryWords) {
		Dokument queryDoc = new Dokument(queryWords);
		queryDoc.computeTFIDF(voc.getIDF());
		
		double res;
		Map<Dokument,Double> results = new HashMap<>();
		for(var doc : voc.getDocuments()) {
			res = queryDoc.similarity(doc);
			if(res != 0) {
				results.put(doc, res);
			}
		}
		return results;
	}
	
	/**
	 * 	Executes the command <code>results</code> which prints the results of
	 * 	the query.
	 */
	private static void results() {
		if(!queryEntered) {
			System.out.println("Nije unesen query.");
			return;
		}
		
		for(int i = 0, s = resultList.size(); i < s; ++i) {
			System.out.println(String.format("[%d](%.4f) %s", i, resultList.get(i).getValue(), resultList.get(i).getKey().getName()));
		}
	}
	
	/**
	 *  Executes the command <code>type</code> which prints the contents
	 *  of the {@link Dokument} in result with the given index.
	 *  @param num Index of the {@link Dokument} to print.
	 */
	private static void type(int num) {
		if(!queryEntered) {
			System.out.println("Nije unesen query.");
			return;
		}
		if(num >= resultList.size()) {
			System.out.println("Ne postoji rezultat pod tim rednim brojem.");
			return;
		}
		
		System.out.println("Dokument: " + resultList.get(num).getKey().getName());
		System.out.println("-".repeat(30));
		resultList.get(num).getKey().write();
	}
	
}
