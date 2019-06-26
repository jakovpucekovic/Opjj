package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.LoginUserForm;

/**
 *	Servlet which does the user login process.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!"Login".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		LoginUserForm userForm = new LoginUserForm();
		userForm.fillFromHttpRequest(req);
		userForm.validate();
		
		if(userForm.hasErrors()) {
			req.setAttribute("userForm", userForm);
			req.getRequestDispatcher("/servleti/main").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		userForm.fillBlogUser(user);
		user = DAOProvider.getDAO().getBlogUserByName(user.getNick());
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
