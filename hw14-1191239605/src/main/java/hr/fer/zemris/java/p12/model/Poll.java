package hr.fer.zemris.java.p12.model;

/**
 *	Poll TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Poll {

	private long id;
	
	private String title;
	
	private String message;

	/**
	 * 	Returns the id of the Poll.
	 * 	@return the id of the Poll.
	 */
	public long getId() {
		return id;
	}

	/**
	 * 	Sets the id of the Poll.
	 * 	@param id the id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 	Returns the title of the Poll.
	 * 	@return the title of the Poll.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 	Sets the title of the Poll.
	 * 	@param title the title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 	Returns the message of the Poll.
	 * 	@return the message of the Poll.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 	Sets the message of the Poll.
	 * 	@param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
