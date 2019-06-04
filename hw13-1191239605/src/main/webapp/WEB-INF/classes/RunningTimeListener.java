import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *	RunningTimeListener TODO javadoc
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
		sce.getServletContext().removeAttribute("startingTime");
	}

}
