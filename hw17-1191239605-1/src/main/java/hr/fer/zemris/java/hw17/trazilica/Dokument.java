package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *	Class which represents a document.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Dokument {

	/**Path to the document*/
	private Path path;
	
	/**Term frequency map which maps each word to the number of times it's used.*/
	private Map<String,Integer> tf  = new HashMap<>();
	
	/**Term frequency-inverse document frequency list.*/
	private List<Double> tfidf = new ArrayList<>();
	
	/**
	 * 	Constructs a new {@link Dokument} with the following words, each word
	 * 	is put only once in the term frequency map.
	 * 	@param words {@link List} of words to contruct the {@link Dokument} with.
	 */
	public Dokument(List<String> words) {
		for(var w : words) {
			tf.put(w, 1);
		}
	}
	
	/**
	 * 	Constructs a new {@link Dokument} from a file on disk.
	 * 	@param pathtoFile {@link Path} to the file from which the {@link Dokument}	
	 * 					  should be read.
	 * 	@throws NullPointerException If path is <code>null</code>.
	 * 	@throws IllegalArgumentException If path points to non-existent file or if the
	 * 									 file is not readable.
	 * 	@throws IOException If there is an error while reading the file.
	 */
	public Dokument(Path pathtoFile) throws IOException {
		Objects.requireNonNull(pathtoFile);
		path = pathtoFile;
		if(Files.notExists(path) || !Files.isReadable(path)) {
			throw new IllegalArgumentException("Invalid path to document given");
		}
		read();
	}
	
	/**
	 *	Writes the contents of the {@link Dokument} on {@link System.out}. 
	 */
	public void write() {
		try(BufferedReader in = Files.newBufferedReader(path)){
			String line;
			while((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch(IOException ex) {
			System.out.println("Error while reading file " + path.toString());
		}
	}

	/**
	 *	Calculates the TFIDF list based on the {@link Vokabular}.
	 *	@param vokabular {@link Vokabular} based on which the TFIDF list is calculated. 
	 */
	public void computeTFIDF(Vokabular vokabular) {
		for(int i = 0, s = vokabular.getWords().size(); i < s; ++i) {
			if(tf.get(vokabular.getWords().get(i))== null) {
				tfidf.add(0.0);
			}else {
				tfidf.add(tf.get(vokabular.getWords().get(i)) * vokabular.getIDF().get(i));
			}
		}
	}
	
	/**
	 * 	Returns the term frequnecy map of this {@link Dokument}.	
	 * 	@return The term frequnecy map of this {@link Dokument}.	
	 */
	public Map<String,Integer> getTF(){
		return tf;
	} 
	
	/**
	 *	Returns the name(full path) of the {@link Dokument}.
	 *	@return The name of the {@link Dokument}.
	 */
	public String getName() {
		return path.toString();
	}
	
	/**
	 *	Private method which reads the file from memory and contructs
	 *	a term frequency map. Stop words from the {@link Vokabular} are ignored.
	 *	Also, words are separated by {@link Character}s for which <code>Character.isAlphabetic()</code>
	 *	returns <code>false</code>.
	 *	@throws IOException If there is an error while reading the file. 
	 */
	private void read() throws IOException{
		tf = new HashMap<>();
		BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		StringBuilder sb = new StringBuilder();
		int read = br.read();
		while(read != -1) {
			if(Character.isAlphabetic(read)) {
				sb.append((char)read);
			} else {
				String word = sb.toString().toLowerCase();
				if(!Vokabular.getStopWords().contains(word)) {
					tf.compute(word, (k,v)-> v == null ? 1 : ++v);
				}
				sb.setLength(0);
			}
			read = br.read();
		}
		br.close();		
	} 

	/**
	 * 	Calculates the similarity of this and the given {@link Dokument}.
	 * 	@param doc {@link Dokument} to calculate similarity with.
	 * 	@return The similarity between documents.
	 */
	public double similarity(Dokument doc) {
		double sim = 0.0, norm1 = 0.0, norm2 = 0.0;
		Double v1,v2;
		for(int i = 0, s = tfidf.size(); i < s; ++i) {
			v1 = tfidf.get(i);
			v2 = doc.tfidf.get(i);
			norm1 += v1*v1;
			norm2 += v2*v2;
			sim += v1*v2;
		}
		if(sim == 0) {
			return 0;
		}
		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);
		return sim / (norm1 * norm2);
	}
	
}
