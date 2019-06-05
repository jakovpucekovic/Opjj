package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	GlasanjeGlasajServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="glasanjeGlasaj", urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
	
		List<String[]> votes = Files.readAllLines(filePath)
									 .stream()
									 .map(x->x.split("\t+"))
									 .collect(Collectors.toList());
		String newVote = req.getParameter("id");
		
		boolean added = false;
		if(newVote != null) {
			for(var candidate : votes) {
				if(candidate[0].equals(newVote)) {
					candidate[1] = Integer.toString(Integer.parseInt(candidate[1]) + 1);
					added = true;
					break;
				}
			}
			
			if(!added) {
				votes.add(new String[] {newVote, "1"});
			}
		}
		
		Files.writeString(filePath, "");
		for(var candidate : votes) {
			Files.writeString(filePath, candidate[0] + "\t" + candidate[1] + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
