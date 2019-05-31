package hr.fer.zemris.java.webserver;

/**
 *	Interface which allows dispatching requests to process
 *	url paths.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface IDispatcher {

	/**
	 *	Processes the request given by the url path.
	 *	@param urlPath Path to process.
	 *	@throws Exception if something goes wrong. 
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}
