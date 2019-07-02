package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *	Dokument TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Dokument {

	private Path path;
	private Map<String,Double> tfidf = new HashMap<>();
	private Map<String,Integer> tf  = new HashMap<>();
	
	
	public Dokument(List<String> words) {
		for(var w : words) {
			tf.put(w, 1);
		}
	}
	
	/**
	 * 	Constructs a new Dokument.
	 * 	TODO javadoc
	 * @throws IOException 
	 */
	public Dokument(Path pathtoFile) throws IOException {
		Objects.requireNonNull(pathtoFile);
		path = pathtoFile;
		if(Files.notExists(path) || !Files.isReadable(path)) {
			throw new IllegalArgumentException("Invalid path to document given");
		}
		tf = read();
	}
	
	//ispise dokument na system.out
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

	public void computeTFIDF(Map<String,Double> idf) {
		for(var word : tf.keySet()) {
			tfidf.put(word, tf.get(word) * idf.get(word));
		}
	}
	
	public Map<String,Integer> getTF(){
		return tf;
	} 
	
	public String getName() {
		return path.toString();
	}
	
	//procita i vrati tf
	private Map<String,Integer> read() throws IOException{
		Map<String,Integer> tf = new HashMap<>();
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
		
		return tf;
	} 

	public double similarity(Dokument doc) {
		double sim = 0, norm1 = 0, norm2 = 0;
		Set<String> words = new HashSet<>(tfidf.keySet());
		words.addAll(doc.tfidf.keySet());
		
		Double v1,v2;
		for(var w : words) {
			v1 = tfidf.get(w);
			v2 = doc.tfidf.get(w);
			if(v1 == null || v2 == null) {
				continue;
			}
			sim += v1*v2;
			norm1 += v1*v1;
			norm2 += v2*v2;
		}
		
		return sim / (norm1 * norm2);
	}
	
}
