package hr.fer.zemris.java.webserver;

/**
 *	Interface which represents an object capable of processing
 *	requests. It should process the request, create content and
 *	write it to through {@link RequestContext}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface IWebWorker {

	/**
	 * 	Method which processes a request within the given {@link RequestContext}.
	 * 	@throws Exception In case something goes wrong.
	 */
	void processRequest(RequestContext context) throws Exception;
	
}
