package hr.fer.zemris.java.hw13.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *	Class which represents a candidate for which can be voted.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class VotingCandidate {

	/**Unique id of the candidate*/
	private int id;
	
	/**Name of the candidate*/
	private String name;
	
	/**Link to a song from the candidate*/
	private String songLink;
	
	/**Number of votes the candidate has recieved*/
	private int votes;
	
	/**
	 *	Constructs a new {@link VotingCandidate} from the given {@link String}.
	 *	@param s {@link String} which contains a valid {@link VotingCandidate}.
	 *	@throws IllegalArgumentException If a {@link VotingCandidate} can not be created
	 *									 from the given {@link String}. 
	 */
	public VotingCandidate(String s) {
		String[] splitted = s.split("\t+");
		if(splitted.length != 3) {
			throw new IllegalArgumentException();
		}
		try {
			this.id = Integer.parseInt(splitted[0]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		
		this.name = splitted[1];
		this.songLink = splitted[2];
	}
	
	/**
	 * 	Returns the id of the {@link VotingCandidate}.
	 * 	@return the id of the {@link VotingCandidate}.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 	Sets the id of the {@link VotingCandidate}.
	 * 	@param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 	Returns the name of the {@link VotingCandidate}.
	 * 	@return the name of the {@link VotingCandidate}.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 	Sets the name of the {@link VotingCandidate}.
	 * 	@param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 	Returns the songLink of the {@link VotingCandidate}.
	 * 	@return the songLink of the {@link VotingCandidate}.
	 */
	public String getSongLink() {
		return songLink;
	}

	/**
	 * 	Sets the songLink of the {@link VotingCandidate}.
	 * 	@param songLink The songLink to set.
	 */
	public void setSongLink(String songLink) {
		this.songLink = songLink;
	}

	/**
	 * 	Returns the votes of the {@link VotingCandidate}.
	 * 	@return the votes of the {@link VotingCandidate}.
	 */
	public int getVotes() {
		return votes;
	}
	
	/**
	 * 	Sets the votes of the {@link VotingCandidate}.
	 * 	@param votes The votes to set.
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + id + ", " + name + ", " + votes + ")";
	}
	
	/**
	 * 	Loads the candidates and results from the given paths and creates
	 * 	a {@link List} of {@link VotingCandidate}s.
	 * 	@param candidatesPath Path to file which contains candidate data.
	 * 	@param resultsPath Path to file which contains voting results data.
	 * 	@return {@link List} of {@link VotingCandidate}s.
	 */
	public static List<VotingCandidate> loadCandidatesAndResults(String candidatesPath, String resultsPath) throws IOException{	
		List<VotingCandidate> candidates = loadCandidates(candidatesPath);
		
		Path filePath = Paths.get(resultsPath);
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		List<String[]> votes = Files.readAllLines(filePath)
									 .stream()
									 .map(x->x.split("\t+"))
									 .collect(Collectors.toList());
		//add votes to VotingCandidates with valid id
		for(var vote : votes) {
			int voteId = Integer.parseInt(vote[0]);
			int voteNumber = Integer.parseInt(vote[1]);
			for(var candidate : candidates) {
				if(voteId == candidate.getId()) {
					candidate.setVotes(voteNumber);
					break;
				}
			}
		}
		
		return candidates;
	}
	
	/**
	 * 	Loads the candidates from the given paths and creates
	 * 	a {@link List} of {@link VotingCandidate}s.
	 * 	@param candidatesPath Path to file which contains candidate data.
	 * 	@return {@link List} of {@link VotingCandidate}s.
	 */
	public static List<VotingCandidate> loadCandidates(String candidatesPath) throws IOException{	
		List<VotingCandidate> candidates = Files.readAllLines(Paths.get(candidatesPath))
												.stream()
												.map(x->new VotingCandidate(x))
												.sorted((a,b)-> {return a.getId() - b.getId();})
												.collect(Collectors.toList());
		return candidates;
	}
	
}
