package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	{@link HttpServlet} which redirects the default page to /servleti/index.html
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="index", urlPatterns={"/index.html"})
public class IndexServlet extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
	
}
