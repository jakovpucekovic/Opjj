package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 *	BlogEntriesServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet("/servleti/author/*")
public class BlogEntriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String path = req.getPathInfo();
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByName(path.substring(1));
		
		List<BlogEntry> list = DAOProvider.getDAO().getBlogEntriesByAuthor(user);
		req.setAttribute("blogEntries", list);
		
		req.getRequestDispatcher("/WEB-INF/pages/listBlogs.jsp").forward(req, resp);
		
		//TODO dodaj da ne mozes dodati blog ako nije pravi nick
		req.setAttribute("currentPageAuthor", path.substring(1)); //TODO vidi zas ne radi ovak
	}
	
}
