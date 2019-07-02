package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *	Vokabular TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Vokabular {

	private Path path;
	private static final String stopWordsFile = "src/main/resources/hrvatski_stoprijeci.txt";
	private static Set<String> stopWords = null;
	
	//mapa svih rijeci i broja dokumenata u kojima se ta rijec pojavljuje najmanje jednom
	private Map<String,Double> idf = new HashMap<>();
	private List<Dokument> documents = new ArrayList<>();
	
	
	/**
	 * 	Constructs a new Vokabular.
	 * 	TODO javadoc
	 * @throws IOException 
	 */
	public Vokabular(String directory) throws IOException {
		path = Paths.get(directory);
		
		if(Files.notExists(path)) {
			throw new IllegalArgumentException("Directory doesn't exist.");
		} else if(!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Given argument isn't a directory.");
		}
		if(stopWords == null) {
			stopWords = new HashSet<>(Files.readAllLines(Paths.get(stopWordsFile), StandardCharsets.UTF_8));
		}
		readDir();
		calculateIDF();
		for(var doc : documents) {
			doc.computeTFIDF(idf);
		}
	}
	
	private void calculateIDF() {
		int numberOfDocs = documents.size();
		
		//go through all documents
		for(var doc : documents) {
			//go through all words in a document
			for(var word : doc.getTF().keySet()) {
				//add all words to idf map
				if(!idf.containsKey(word)) {
					idf.put(word, null);
				}
			}
		}
		
		//calculate idf value for every word
		for(var word : idf.keySet()) {
			int numberOfDocsWithWord = 0;
			for(var doc : documents) {
				if(doc.getTF().containsKey(word)) {
					++numberOfDocsWithWord;
				}
			}
			idf.put(word, Math.log((double)numberOfDocs/numberOfDocsWithWord));
		}
	}
	
	private void readDir() throws IOException {
		Files.walkFileTree(path, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if(file != null) {
					documents.add(new Dokument(file));
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				System.err.println("Failed visiting file " + file.toString());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	public boolean hasWord(String word) {
		return idf.containsKey(word);
	}
	
	public int getWordListSize(){
		return idf.keySet().size();
	}
	
	public static Set<String> getStopWords() throws IOException{
		if(stopWords == null) {
			stopWords = new HashSet<>(Files.readAllLines(Paths.get(stopWordsFile), StandardCharsets.UTF_8));
		}
		return stopWords;
	}
	
	public List<Dokument> getDocuments(){
		return documents;
	}
	
	public Map<String,Double> getIDF(){
		return idf;
	}
}
