package hr.fer.zemris.java.webserver;

/**
 *	IDispatcher TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public interface IDispatcher {

	void dispatchRequest(String urlPath) throws Exception;
	
}
