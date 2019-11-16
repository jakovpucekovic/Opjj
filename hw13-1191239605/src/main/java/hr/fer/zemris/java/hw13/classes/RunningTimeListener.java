package hr.fer.zemris.java.hw13.classes;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *	Listener which registers the starting time of the current session.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebListener
public class RunningTimeListener implements ServletContextListener {

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startingTime", System.currentTimeMillis());
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
