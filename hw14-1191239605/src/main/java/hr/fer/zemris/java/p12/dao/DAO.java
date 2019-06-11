package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.VotingCandidate;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	List<VotingCandidate> getAllVotingCandidates(long pollId) throws DAOException;
	
//	VotingCandidate getVotingCandidate(long id) throws DAOException;
	
	List<Poll> getAllPolls() throws DAOException;
	
	Poll getPoll(long id) throws DAOException;
	
	void addVote(long id) throws DAOException;
	
}