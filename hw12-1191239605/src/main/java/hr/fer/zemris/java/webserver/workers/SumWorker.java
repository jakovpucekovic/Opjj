package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 *	{@link IWebWorker} which adds the two given parameters 
 *	<code>a</code> and <code>b</code> if they exist and
 *	returns their sum. If any of the parameters don't exist
 *	their default values(1 for a and 2 for b) are taken. A simple
 *	html page with the result is generated and according if the sum
 *	was even or odd an image is displayed.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SumWorker implements IWebWorker{

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a = 1;
		int b = 2;
		try{
			a = Integer.parseInt(context.getParameter("a"));
			b = Integer.parseInt(context.getParameter("b"));
		} catch(NumberFormatException ex) {
		}
		
		int zbroj = a + b;
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		context.setTemporaryParameter("zbroj", Integer.toString(zbroj));
		if(zbroj % 2 == 0) {
			context.setTemporaryParameter("imgName", "http://www.localhost.com:5721/images/even.jpg");
		} else {
			context.setTemporaryParameter("imgName", "http://www.localhost.com:5721/images/odd.jpg");			
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}
	
}
