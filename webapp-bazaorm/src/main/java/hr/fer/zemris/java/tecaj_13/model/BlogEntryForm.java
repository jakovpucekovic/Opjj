package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.servlets.util.Crypto;

/**
 *	{@link BlogEntryForm} is used for creating and editing blogs.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class BlogEntryForm {

	private String id;
	private List<BlogComment> comments = new ArrayList<>();
	private Date createdAt;
	private Date lastModifiedAt;
	private String title;
	private String text;
	private BlogUser creator;
	
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
	 *	Fills this {@link BlogEntryForm} with the data given in the
	 *	{@link HttpServletRequest}.
	 *	@param req {@link HttpServletRequest} which holds the data. 
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.id = setup(req.getParameter("id"));
		this.title = setup(req.getParameter("title"));
		this.text = setup(req.getParameter("text"));
//		this.creator = setup(req.getParameter("creator")); TODO??
//		this.comments = setup(req.getParameter("comments"));
//		this.createdAt = setup(req.getParameter("createdAt"));
//		this.lastModifiedAt = setup(req.getParameter("lastModifiedAt"));
		
	}

	/**
	 *	Fills this {@link BlogEntryForm} with the data in the given {@link BlogEntry}. 
	 *	@param entry {@link BlogEntry} which provides the data.
	 */
	public void fillFromBlogEntry(BlogEntry entry) {
		if(entry.getId()==null) {
			this.id = "";
		} else {
			this.id = entry.getId().toString();
		}
		
		this.creator = entry.getCreator();
		this.createdAt = entry.getCreatedAt();
		this.lastModifiedAt = entry.getLastModifiedAt();
		this.title = entry.getTitle();
		this.text = entry.getText();
		this.comments = entry.getComments();
	}

	/**
	 * 	Fills the given {@link BlogEntry} with the data stored in this
	 * 	{@link BlogEntryForm}.
	 * 	@param entry {@link BlogEntry} to fill.
	 */
	public void fillBlogEntry(BlogEntry entry) {
		if(this.id.isEmpty()) {
			entry.setId(null);
		} else {
			entry.setId(Long.valueOf(this.id));
		}
		
		entry.setComments(this.comments);
		entry.setCreatedAt(this.createdAt);
		entry.setCreator(this.creator);
		entry.setLastModifiedAt(this.lastModifiedAt);
		entry.setText(this.text);
		entry.setTitle(this.title);
		
	}
	
	/**
	 * 	Checks if this {@link BlogEntryForm} contains valid data
	 * 	and fills the errors map if there is data which is not valid.
	 */
	public void validate() {
		errors.clear();
		
		if(!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch(NumberFormatException ex) {
				errors.put("id", "Id not valid");
			}
		}
//	TODO validacija
//		if(this.nick.isEmpty()) {
//			errors.put("nick", "Nick is mandatory!");
//		} else if(this.nick.length() > 20) {
//			errors.put("nick", "Nick cannot be longer than 20 characters!");
//		} else if(!usernameExists(this.nick)) {
//			errors.put("nick", "Username doesn't exist!");
//		}
//	
		
		
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
	 * 	Returns the id of the {@link BlogEntryForm}.
	 * 	@return the id of the {@link BlogEntryForm}.
	 */
	public String getId() {
		return id;
	}

	/**
	 * 	Sets the id of the {@link BlogEntryForm}.
	 * 	@param id the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 	Returns the comments of the {@link BlogEntryForm}.
	 * 	@return the comments of the {@link BlogEntryForm}.
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * 	Sets the comments of the {@link BlogEntryForm}.
	 * 	@param comments the comments to set.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * 	Returns the createdAt of the {@link BlogEntryForm}.
	 * 	@return the createdAt of the {@link BlogEntryForm}.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * 	Sets the createdAt of the {@link BlogEntryForm}.
	 * 	@param createdAt the createdAt to set.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 	Returns the lastModifiedAt of the {@link BlogEntryForm}.
	 * 	@return the lastModifiedAt of the {@link BlogEntryForm}.
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * 	Sets the lastModifiedAt of the {@link BlogEntryForm}.
	 * 	@param lastModifiedAt the lastModifiedAt to set.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * 	Returns the title of the {@link BlogEntryForm}.
	 * 	@return the title of the {@link BlogEntryForm}.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 	Sets the title of the {@link BlogEntryForm}.
	 * 	@param title the title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 	Returns the text of the {@link BlogEntryForm}.
	 * 	@return the text of the {@link BlogEntryForm}.
	 */
	public String getText() {
		return text;
	}

	/**
	 * 	Sets the text of the {@link BlogEntryForm}.
	 * 	@param text the text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 	Returns the creator of the {@link BlogEntryForm}.
	 * 	@return the creator of the {@link BlogEntryForm}.
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * 	Sets the creator of the {@link BlogEntryForm}.
	 * 	@param creator the creator to set.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

}
