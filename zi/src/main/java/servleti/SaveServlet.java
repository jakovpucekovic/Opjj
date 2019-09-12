package servleti;

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

/**
 *	SaveServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="save", urlPatterns={"/save"})
public class SaveServlet extends HttpServlet{

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Path imgs = Paths.get(req.getServletContext().getRealPath(("/WEB-INF/images")));
		if(!Files.exists(imgs)) {
			Files.createDirectory(imgs);
		}
		
		String title = (String) req.getAttribute("title");
		String text = (String) req.getAttribute("body");
		
		if(!title.endsWith(".jsp")) {
			//send error
		} 
		
		for(var c : title.substring(0, title.lastIndexOf(".")).toCharArray()) {
			if(!Character.isAlphabetic(c) || !Character.isDigit(c)) {
				//send error
			}
		}
		
		Files.createFile(imgs.resolve(title));
		Files.writeString(imgs.resolve(title), text);
		
		List<String> list = Files.walk(imgs).map(x->x.toString()).collect(Collectors.toList());
		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}
	
	
}
