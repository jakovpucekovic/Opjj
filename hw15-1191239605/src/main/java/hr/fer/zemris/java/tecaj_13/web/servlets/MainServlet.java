package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 *	{@link HttpServlet} which points to main.jsp page.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
		req.setAttribute("blogUsers", users);
		
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
		req.setAttribute("blogUsers", users);
		
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
	
}
