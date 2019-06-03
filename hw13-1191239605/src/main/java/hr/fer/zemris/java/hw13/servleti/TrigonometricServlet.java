package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.classes.TrigonometricContainer;

/**
 *	TrigonometricServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="trigonometric", urlPatterns={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
		 *	{@inheritDoc}
		 */
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String a = req.getParameter("a");
			String b = req.getParameter("b");
			//TODO sto ako su a i b double
			int varA = 0;
			int varB = 360;
			
			try {
				if(a != null) {
					varA = Integer.valueOf(a);
				}
			} catch(Exception ignorable) {
			}
			
			try {
				if(b != null) {
				varB = Integer.valueOf(b);
				}
			} catch(Exception ignorable) {
			}
			
			if(varA > varB) {
				int temp = varB;
				varB = varA;
				varA = temp;
			}
			
			if(varB > varA + 720) {
				varB = varA + 720;
			}
			
			List<TrigonometricContainer> trigList = new ArrayList<>();
			
			for(int i = varA; i <= varB; ++i) {
				trigList.add(new TrigonometricContainer(i));
			}
			
			req.setAttribute("varA", varA);
			req.setAttribute("varB", varB);
			req.setAttribute("trigList", trigList);
			
			req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		}
	
}
