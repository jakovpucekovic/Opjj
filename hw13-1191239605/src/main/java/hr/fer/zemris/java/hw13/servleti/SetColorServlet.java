package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	Servlet which chages the <code>pickedBgCol</code> attribute to the
 *	picked color.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="setcolor", urlPatterns={"/setcolor"})
public class SetColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bgColor = req.getParameter("bgcolor");
		req.getSession().setAttribute("pickedBgCol", bgColor);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
}
