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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *	Class which represents all words that are used in all {@link Dokument}s and
 *	the stop words which are ignored.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Vokabular {

	/**Path to the folder which contains the {@link Dokument}s.*/
	private Path path;
	
	/**Path to the file which contains the stop words.*/
 	private static final String stopWordsFile = "src/main/resources/hrvatski_stoprijeci.txt";
	
 	/**Set which contains all stop words.*/
	private static Set<String> stopWords = null;
	
	/**List of words in the {@link Vokabular}*/
	private List<String> words = new ArrayList<>();

	/**Inverse document frequency list.*/
	private List<Double> idf = new ArrayList<>();
	
	/**{@link List} of all {@link Dokument}s read in the given folder.*/
	private List<Dokument> documents = new ArrayList<>();
	
	
	/**
	 * 	Constructs a new {@link Vokabular}.
	 * 	@param directory Path to the directory which contains files based on which
	 * 					 the {@link Vokabular} is created.
	 * 	@throws IllegalArgumentException If the given path isn't a directory or if
	 * 									 the directory doesn't exist.
	 * 	@throws IOException If there is an error while reading files.
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
			doc.computeTFIDF(this);
		}
	}
	
	/**
	 *	Calculates the IDF vector of all words in the {@link Vokabular}. 
	 */
	private void calculateIDF() {
		int numberOfDocs = documents.size();
		
		//go through all documents
		for(var doc : documents) {
			//go through all words in a document
			for(var word : doc.getTF().keySet()) {
				//add all words to words list
				if(!words.contains(word)) {
					words.add(word);
				}
			}
		}
		
		//calculate idf value for every word
		for(var word : words) {
			int numberOfDocsWithWord = 0;
			for(var doc : documents) {
				if(doc.getTF().containsKey(word)) {
					++numberOfDocsWithWord;
				}
			}
			idf.add(Math.log((double)numberOfDocs/numberOfDocsWithWord));
		}
	}
	
	/**
	 * 	Reads all the files in the given directory structure and contructs a
	 * 	{@link Dokument} for each file.
	 * 	@throws IOException If there is an error reading files.
	 */
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
	
	/**
	 * 	Checks if the given word is in the {@link Vokabular}.	
	 * 	@param word Word to check.
	 * 	@return <code>true</code> if yes, <code>false</code> if not.	
	 */
	public boolean hasWord(String word) {
		return words.contains(word);
	}
	
	/**
	 * 	Returns the number of words in this {@link Vokabular}.
	 * 	@return The number of words in this {@link Vokabular}.
	 */
	public int getWordListSize(){
		return words.size();
	}
	
	/**
	 * 	Returns the {@link List} of all {@link Dokument}s.
	 * 	@return The {@link List} of all {@link Dokument}s.
	 */
	public List<Dokument> getDocuments(){
		return documents;
	}

	/**
	 * 	Returns the IDF list.
	 * 	@return The IDF list.	
	 */
	public List<Double> getIDF(){
		return idf;
	}
	
	public List<String> getWords(){
		return words;
	}

	/**
	 * 	Returns the {@link Set} which contains all of the stop words.
	 * 	@return The {@link Set} which contains all of the stop words.
	 * 	@throws IOException If there is an error while reading file.
	 */
	public static Set<String> getStopWords() throws IOException{
		if(stopWords == null) {
			stopWords = new HashSet<>(Files.readAllLines(Paths.get(stopWordsFile), StandardCharsets.UTF_8));
		}
		return stopWords;
	}
}
