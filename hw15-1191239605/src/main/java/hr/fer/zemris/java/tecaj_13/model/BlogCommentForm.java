package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 *	{@link BlogCommentForm} is a class which allows for the registration
 *	of new users.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BlogCommentForm {
	
	/**Unique id of the user.*/
	private String id;
	
	/**Blog entry to which this comment is attached.*/
	private String blogEntry;
	
	/**Email of the person who posted the comment.*/
	private String usersEMail;
	
	/**The message of the comment.*/
	private String message;
	
	/**Time the comment was posted on.*/
	private String postedOn;


	/**{@link Map} of errors created during validation.*/
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * 	Should be called after validation.
	 * 	@param name Name of the error.
	 * 	@return The error with the given name.
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 *  Should be called after validation.
	 *  @return <code>true</code> if there are errors, <code>false</code> if not.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 *	Should be called after validation.
	 *	@param name Name of the error.
	 *	@return <code>true</code> if there is such an error. 
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 *	Fills this {@link LoginUserForm} with the data given in the
	 *	{@link HttpServletRequest}.
	 *	@param req {@link HttpServletRequest} which holds the data. 
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.id = setup(req.getParameter("id"));
		this.blogEntry = setup(req.getParameter("blogEntry"));
		this.usersEMail = setup(req.getParameter("usersEMail"));
		this.message = setup(req.getParameter("message"));
		this.postedOn = setup(req.getParameter("postedOn"));
	}

	/**
	 *	Fills this {@link BlogCommentForm} with the data in the given {@link BlogComment}. 
	 *	@param user {@link BlogComment} which provides the data.
	 */
	public void fillFromBlogComment(BlogComment comment) {
		if(comment.getId()==null) {
			this.id = "";
		} else {
			this.id = comment.getId().toString();
		}
		this.message = comment.getMessage();
		this.usersEMail = comment.getUsersEMail();
	}

	/**
	 * 	Fills the given {@link BlogComment} with the data stored in this
	 * 	{@link BlogCommentForm}.
	 * 	@param user {@link BlogComment} to fill.
	 */
	public void fillBlogComment(BlogComment comment) {
		if(this.id.isEmpty()) {
			comment.setId(null);
		} else {
			comment.setId(Long.valueOf(this.id));
		}
		
		comment.setUsersEMail(this.usersEMail);
		comment.setMessage(this.message);
		
	}
	
	/**
	 * 	Checks if this {@link LoginUserForm} contains valid data
	 * 	and fills the errors map if there is data which is not valid.
	 */
	public void validate() {
		errors.clear();
		
		if(this.usersEMail.isEmpty()) {
			errors.put("usersEMail", "EMail is mandatory!");
		}else {
			int l = usersEMail.length();
			int p = usersEMail.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("usersEMail", "EMail has wrong format.");
			}
		}
		
		if(this.message.isEmpty()) {
			errors.put("message", "Message cannot be empty!");
		}
		
	}
	
	/**
	 * 	Helper method which converts <code>null</code> string into empty strings.
	 * 	@param s string
	 * 	@return The same string if received <code>non-null</code> string, empty string otherwise.
	 */
	private String setup(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	/**
	 * 	Returns the id of the {@link BlogCommentForm}.
	 * 	@return the id of the {@link BlogCommentForm}.
	 */
	public String getId() {
		return id;
	}

	/**
	 * 	Sets the id of the {@link BlogCommentForm}.
	 * 	@param id the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 	Returns the blogEntry of the {@link BlogCommentForm}.
	 * 	@return the blogEntry of the {@link BlogCommentForm}.
	 */
	public String getBlogEntry() {
		return blogEntry;
	}

	/**
	 * 	Sets the blogEntry of the {@link BlogCommentForm}.
	 * 	@param blogEntry the blogEntry to set.
	 */
	public void setBlogEntry(String blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * 	Returns the usersEMail of the {@link BlogCommentForm}.
	 * 	@return the usersEMail of the {@link BlogCommentForm}.
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * 	Sets the usersEMail of the {@link BlogCommentForm}.
	 * 	@param usersEMail the usersEMail to set.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * 	Returns the message of the {@link BlogCommentForm}.
	 * 	@return the message of the {@link BlogCommentForm}.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 	Sets the message of the {@link BlogCommentForm}.
	 * 	@param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 	Returns the postedOn of the {@link BlogCommentForm}.
	 * 	@return the postedOn of the {@link BlogCommentForm}.
	 */
	public String getPostedOn() {
		return postedOn;
	}

	/**
	 * 	Sets the postedOn of the {@link BlogCommentForm}.
	 * 	@param postedOn the postedOn to set.
	 */
	public void setPostedOn(String postedOn) {
		this.postedOn = postedOn;
	}

}
