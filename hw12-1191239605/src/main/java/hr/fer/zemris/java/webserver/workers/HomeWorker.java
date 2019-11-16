package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 *	{@link IWebWorker} which run the home.smscr smart script which 
 *	generates a basic home page which showcases different things we
 *	can do.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class HomeWorker implements IWebWorker{

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String bgColor = context.getPersistentParameter("bgcolor");
		if(bgColor == null) {
			bgColor = "7F7F7F";
		}
		
		context.setTemporaryParameter("background", bgColor);
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
		
	}
	
}
