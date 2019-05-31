package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 *	{@link IWebWorker} which returns the given parameters in a simple
 *	html table.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class EchoParams implements IWebWorker{

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		
		try {
			context.write("<html><body>");
			context.write("<table>");
			context.write("<tr><th>Parameter name</th><th>Parameter value</th>");
			for(var key : context.getParameterNames()) {
				context.write("<tr><td>");
				context.write(key);
				context.write("</td><td>");
				context.write(context.getParameter(key));
				context.write("</tr>");
			}
			context.write("</table>");	
			context.write("</body></html>");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

	
	
	
}
