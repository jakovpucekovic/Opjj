package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
		
		List<VotingCandidate> candidates = Files.readAllLines(Paths.get(definicijaFileName))
												.stream()
												.map(x->new VotingCandidate(x))
												.sorted((a,b)-> {return a.getId() - b.getId();})
												.collect(Collectors.toList());
		
		Path filePath = Paths.get(rezultatiFileName);
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
		
		candidates.sort((a,b)-> b.getVotes() - a.getVotes());
		
		req.setAttribute("votingResults", candidates);		
				
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}
