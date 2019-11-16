package hr.fer.zemris.java.hw16.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	{@link HttpServlet} which send pictures. If the parameter is "name" it writes
 *	the original picture with the given name. If the parameter is "tag" it writes
 *	a thumbnail of the picture with the given name.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(urlPatterns = {"/pic"})
public class PicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/jpg");
		
		Path path = Paths.get(req.getServletContext().getRealPath("/WEB-INF"));
		if(req.getParameter("name") != null) {
			path = path.resolve("slike").resolve(req.getParameter("name"));
			ImageIO.write(ImageIO.read(path.toFile()), "jpg", resp.getOutputStream());
		} else if(req.getParameter("tag") != null) {
			path = path.resolve("thumbnails").resolve(req.getParameter("tag"));			
			ImageIO.write(ImageIO.read(path.toFile()), "jpg", resp.getOutputStream());
		}
		return;
	}
	
	
	
}
