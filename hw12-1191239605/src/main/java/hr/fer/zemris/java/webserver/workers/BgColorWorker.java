package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 *	{@link IWebWorker} which changes the color on the index2.html page
 *	and generates a temporary page that leads to index2.html
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BgColorWorker implements IWebWorker{

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getParameter("bgcolor");
		if(bgColor == null || !isHex(bgColor)) {
			context.setMimeType("text/html");
			
			try {
				context.write("<html><body>");
				context.write("<p>Color has not been changed.</p>");
				context.write("<a href=\"http://www.localhost.com:5721/index2.html\">Go to index2.html</a>");
				context.write("</body></html>");
			} catch(IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else {
			context.setPersistentParameter("bgcolor", bgColor);
			context.setMimeType("text/html");
			
			try {
				context.write("<html><body>");
				context.write("<p>Background color has been changed to ");
				context.write(bgColor);
				context.write(".</p>");
				context.write("<a href=\"http://www.localhost.com:5721/index2.html\">Go to index2.html</a>");
				
				context.write("</body></html>");
			} catch(IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	/**
	 * 	Checks if the given {@link String} is a valid hex color code.
	 * 	@param color {@link String} to check.
	 * 	@return <code>true</code> if yes, <code>false</code> if no.
	 */
	private boolean isHex(String color) {
		try {
			Color.decode("0x" + color);
			return true;
		} catch(NumberFormatException ex) {
			return false;
		}
	}
	
}
