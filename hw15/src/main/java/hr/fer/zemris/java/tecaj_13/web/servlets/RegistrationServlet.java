package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.BlogUserForm;

/**
 *	RegistrationServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String metoda = req.getParameter("method");
		if(!"Register".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		BlogUserForm userForm = new BlogUserForm();
		userForm.fillFromHttpRequest(req);
		userForm.validate();
		
		if(userForm.hasErrors()) {
			req.setAttribute("userForm", userForm);
			req.getRequestDispatcher("/WEB-INF/pages/RegistrationForm.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		userForm.fillBlogUser(user);
		
		DAOProvider.getDAO().saveBlogUser(user);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
