package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.VotingCandidate;

/**
 *	Servlet which does the actual voting.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="glasanjeGlasaj", urlPatterns={"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String newVote = req.getParameter("id");
		String pollID = req.getParameter("pollID");
		
		VotingCandidate candidate = DAOProvider.getDao().getVotingCandidate(Long.parseLong(newVote));
		candidate.setVotes(candidate.getVotes() + 1);
		DAOProvider.getDao().updateVotingCandidate(candidate);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}
}
