package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *	Konzola TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Konzola {
	
	private static Vokabular voc;
	private static boolean queryEntered = false;
	private static Map<Dokument,Double> results = new HashMap<>();
	
	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Ocekivan je path do direktorija koji sadrzi clanke.");
			return;
		}
		
		try {
			voc = new Vokabular(args[0]);
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
			} else if(input.startsWith("query ")) { //TODO popraviti uvjet za query
				queryEntered = true;
				query(input.substring(5));
			} else {
				System.out.println("Nepoznata naredba.");
			}
			System.out.print("Enter command > ");
		}
		sc.close();
	}
	
	private static void query(String input) {
		String[] params = input.split(" ");
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
		
		executeQuery(validParamWords);
		
		//print results
		System.out.println("Najboljih 10 rezultata:");
		
	}
	
	
	private static void executeQuery(List<String> queryWords) {
		Dokument queryDoc = new Dokument(queryWords);
		queryDoc.computeTFIDF(voc.getIDF());
		
		double res;
		for(var doc : voc.getDocuments()) {
			res = queryDoc.similarity(doc);
			if(res != 0) {
				results.put(doc, res);
			}
		}
	}
	
	
	private static void results() {
		if(!queryEntered) {
			System.out.println("Nije unesen query.");
			return;
		}
	}
	
	private static void type(int num) {
		if(!queryEntered) {
			System.out.println("Nije unesen query.");
			return;
		}
		if(num >= results.size()) {
			System.out.println("Ne postoji rezultat pod tim rednim brojem.");
			return;
		}
		
//		System.out.println(results.get(num).getName());
//		System.out.println("-".repeat(30));
//		results.get(num).write();

	}
	
}
