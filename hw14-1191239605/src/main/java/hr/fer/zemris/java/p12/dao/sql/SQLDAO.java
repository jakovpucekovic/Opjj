package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.VotingCandidate;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<VotingCandidate> getAllVotingCandidates(long pollId) throws DAOException {
		List<VotingCandidate> candidates = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id,optionTitle,optionLink,votesCount from PollOptions where pollID=?");
			pst.setLong(1, pollId);
			
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						VotingCandidate candidate = new VotingCandidate();
						candidate.setId(rs.getLong(1));
						candidate.setName(rs.getString(2));
						candidate.setLink(rs.getString(3));
						candidate.setVotes(rs.getLong(4));
						candidates.add(candidate);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return candidates;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public VotingCandidate getVotingCandidate(long candidateID) throws DAOException {
		VotingCandidate candidate = new VotingCandidate();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id,optionTitle,optionLink,votesCount from PollOptions where ID=?");
			pst.setLong(1, candidateID);
			
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						candidate.setId(rs.getLong(1));
						candidate.setName(rs.getString(2));
						candidate.setLink(rs.getString(3));
						candidate.setVotes(rs.getLong(4));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return candidate;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<Poll> getAllPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id,title,message from Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste anketa.", ex);
		}
		return polls;
	
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Poll getPoll(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		Poll poll = new Poll();
		try {
			pst = con.prepareStatement("select id,title,message from Polls where id=?");
			pst.setLong(1, id);
			
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata ankete.", ex);
		} 
		return poll;
	
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void updateVotingCandidate(VotingCandidate candidate) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update PollOptions set optionTitle=?, optionLink=?, votesCount=? where id=?");
			pst.setString(1, candidate.getName());
			pst.setString(2, candidate.getLink());
			pst.setLong(3, candidate.getVotes());
			pst.setLong(4, candidate.getId());
			
			try {				
				pst.executeUpdate();
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom updatea kandidata.", ex);
		}	
	}

}
