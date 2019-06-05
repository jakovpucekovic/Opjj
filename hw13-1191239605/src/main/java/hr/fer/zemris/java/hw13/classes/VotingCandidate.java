package hr.fer.zemris.java.hw13.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *	VotingCandidate TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class VotingCandidate {

	private int id;
	
	private String name;
	
	private String songLink;
	
	private int votes;
	
	
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
	 * 	Returns the id of the VotingCandidate.
	 * 	@return the id of the VotingCandidate.
	 */
	public int getId() {
		return id;
	}
	/**
	 * 	Sets the id of the VotingCandidate.
	 * 	@param id the id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 	Returns the name of the VotingCandidate.
	 * 	@return the name of the VotingCandidate.
	 */
	public String getName() {
		return name;
	}
	/**
	 * 	Sets the name of the VotingCandidate.
	 * 	@param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 	Returns the songLink of the VotingCandidate.
	 * 	@return the songLink of the VotingCandidate.
	 */
	public String getSongLink() {
		return songLink;
	}



	/**
	 * 	Sets the songLink of the VotingCandidate.
	 * 	@param songLink the songLink to set.
	 */
	public void setSongLink(String songLink) {
		this.songLink = songLink;
	}



	/**
	 * 	Returns the votes of the VotingCandidate.
	 * 	@return the votes of the VotingCandidate.
	 */
	public int getVotes() {
		return votes;
	}
	/**
	 * 	Sets the votes of the VotingCandidate.
	 * 	@param votes the votes to set.
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
//TODO prikazi linkove samo pobjednicima
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + id + ", " + name + ", " + votes + ")";
	}
	
	public static List<VotingCandidate> loadCandidates(String candidatesPath, String resultsPath) throws IOException{	
		List<VotingCandidate> candidates = Files.readAllLines(Paths.get(candidatesPath))
												.stream()
												.map(x->new VotingCandidate(x))
												.sorted((a,b)-> {return a.getId() - b.getId();})
												.collect(Collectors.toList());
		
		Path filePath = Paths.get(resultsPath);
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		List<String[]> votes = Files.readAllLines(filePath)
									 .stream()
									 .map(x->x.split("\t+"))
									 .collect(Collectors.toList());
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
	
}
