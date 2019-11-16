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

	/**
	 *	Returns all {@link VotingCandidate}s stored in the table POLLS for the
	 *	given pollID.
	 *	@param pollID Id of the poll for which the voting candidates should be returned.
	 *	@return {@link List} of all {@link VotingCandidate}s in the given poll.
	 *	@throws DAOException If something goes wrong.
	 */
	List<VotingCandidate> getAllVotingCandidates(long pollID) throws DAOException;
	
	/**
	 *	Returns the {@link VotingCandidate} from the table POLLOPTIONS with the given ID.
	 *	@param candidateID ID of the {@link VotingCandidate} to return.
	 *	@return {@link VotingCandidate} with the given ID.
	 *	@throws DAOException If something goes wrong.
	 */
	VotingCandidate getVotingCandidate(long candidateID) throws DAOException;
	
	/**
	 *	Returns all the available {@link Poll}s from the table POLLS.
	 *	@return {@link List} of all {@link Poll}s.
	 *	@throws DAOException If something goes wrong.
	 */
	List<Poll> getAllPolls() throws DAOException;
	
	/**
	 *	Returns the {@link Poll} from the table POLLS with the given ID.
	 *	@param pollID ID of the poll to return.
	 *	@return {@link Poll} with the given ID.
	 *	@throws DAOException If something goes wrong.
	 */
	Poll getPoll(long pollID) throws DAOException;
	
	/**
	 *	Updates <strong>an existing</strong> {@link VotingCandidate} to the
	 *	new {@link VotingCandidate}.
	 *	@param candidate The new updated {@link VotingCandidate}.
	 *	@throws DAOException If something goes wrong.
	 */
	void updateVotingCandidate(VotingCandidate candidate) throws DAOException;
		
}