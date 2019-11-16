package hr.fer.zemris.java.p12.model;

/**
 *	Java-bean class which represents a {@link Poll} in POLLS table.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Poll {

	/**ID of the {@link Poll}*/
	private long id;
	
	/**Title of the {@link Poll}*/
	private String title;
	
	/**Message that describes the {@link Poll}*/
	private String message;

	/**
	 * 	Returns the id of the {@link Poll}.
	 * 	@return the id of the {@link Poll}.
	 */
	public long getId() {
		return id;
	}

	/**
	 * 	Sets the id of the {@link Poll}.
	 * 	@param id the id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 	Returns the title of the {@link Poll}.
	 * 	@return the title of the {@link Poll}.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 	Sets the title of the {@link Poll}.
	 * 	@param title the title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 	Returns the message of the {@link Poll}.
	 * 	@return the message of the {@link Poll}.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 	Sets the message of the {@link Poll}.
	 * 	@param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
