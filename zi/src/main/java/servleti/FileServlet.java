package servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 *	FileServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="file", urlPatterns={"/show"})
public class FileServlet extends HttpServlet {

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String file = (String) req.getAttribute("id");
		
		List<String> text = Files.readAllLines(Paths.get(req.getServletContext().getRealPath("WEB-INF/images")).resolve(file));
		int line = 0, circle = 0, fcircle = 0, triangle = 0;
		for(var l : text) {
			if(l.startsWith("LINE")) {
				line++;
			} else if(l.startsWith("CIRCLE")) {
				circle++;
			} else if(l.startsWith("FCIRCLE")) {
				fcircle++;
			} else if(l.startsWith("FTRIANGLE")) {
				triangle++;
			} else {
				continue;
			}
		}
		
		req.setAttribute("title", file);
		req.setAttribute("line", line);
		req.setAttribute("circle", circle);
		req.setAttribute("fcircle", fcircle );
		req.setAttribute("ftriange", triangle);
		
		
		
		req.getRequestDispatcher("/WEB-INF/show.jsp").forward(req, resp);
	
	}
	
}
