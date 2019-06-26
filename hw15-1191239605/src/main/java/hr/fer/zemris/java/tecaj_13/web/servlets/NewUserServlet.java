package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.RegisterUserForm;

/**
 *	NewUserServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

@WebServlet("/servleti/register")
public class NewUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser blogUser = new BlogUser();
		RegisterUserForm form = new RegisterUserForm();
		form.fillFromBlogUser(blogUser);
		
		req.setAttribute("userForm", form);
		
		req.getRequestDispatcher("/WEB-INF/pages/RegistrationForm.jsp").forward(req, resp);
	}
	//TODO vidi jel ovo moze u RegistrationServlet u doGet
}
