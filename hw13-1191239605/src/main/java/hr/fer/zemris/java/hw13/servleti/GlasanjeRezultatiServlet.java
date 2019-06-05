package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.classes.VotingCandidate;

/**
 *	GlasanjeRezultatiServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="glasanjeRezultati", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rezultatiFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String definicijaFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<VotingCandidate> candidates = VotingCandidate.loadCandidatesAndResults(definicijaFileName, rezultatiFileName);
		
		candidates.sort((a,b)-> b.getVotes() - a.getVotes());
		
		req.setAttribute("votingResults", candidates);		
				
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}
