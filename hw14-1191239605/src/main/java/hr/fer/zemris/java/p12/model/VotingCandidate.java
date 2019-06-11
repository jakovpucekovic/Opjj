package hr.fer.zemris.java.p12.model;

/**
 *	Class which represents a candidate for which can be voted.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class VotingCandidate {

	/**Unique id of the candidate*/
	private long id;
	
	/**Name of the candidate*/
	private String name;
	
	/**Link from the candidate*/
	private String link;
	
	/**Number of votes the candidate has recieved*/
	private long votes;
	

	/**
	 * 	Returns the id of the {@link VotingCandidate}.
	 * 	@return the id of the {@link VotingCandidate}.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * 	Sets the id of the {@link VotingCandidate}.
	 * 	@param id The id to set.
	 */
	public void setId(long id) {
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
	 * 	Returns the link of the {@link VotingCandidate}.
	 * 	@return the link of the {@link VotingCandidate}.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 	Sets the link of the {@link VotingCandidate}.
	 * 	@param link The link to set.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 	Returns the votes of the {@link VotingCandidate}.
	 * 	@return the votes of the {@link VotingCandidate}.
	 */
	public long getVotes() {
		return votes;
	}
	
	/**
	 * 	Sets the votes of the {@link VotingCandidate}.
	 * 	@param votes The votes to set.
	 */
	public void setVotes(long votes) {
		this.votes = votes;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + id + ", " + name + ", " + votes + ")";
	}
	
}
