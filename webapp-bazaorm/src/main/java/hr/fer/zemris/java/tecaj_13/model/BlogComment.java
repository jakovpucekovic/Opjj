package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *	BlogComment TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**Unique Id of the blog comment.*/
	private Long id;

	/**Blog entry to which this comment is attached.*/
	private BlogEntry blogEntry;
	
	/**Email of the person who posted the comment.*/
	private String usersEMail;
	
	/**The message of the comment.*/
	private String message;
	
	/**Time the comment was posted on.*/
	private Date postedOn;
	
	
	/**
	 * 	Returns the id of the {@link BlogComment}.
	 * 	@return the id of the {@link BlogComment}.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * 	Sets the id of the {@link BlogComment}.
	 * 	@param id the id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 *	Returns the blogEntry of the {@link BlogComment}.
	 *	@return the blogEntry of the {@link BlogComment}. 
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * 	Sets the blogEntry of the {@link BlogComment}.
	 * 	@param blogEntry the blogEntry to set.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 *	Returns the usersEmail of the {@link BlogComment}.
	 *	@return the usersEmail of the {@link BlogComment}. 
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 *	Sets the usersEmail of the {@link BlogComment}.
	 *	@param usersEMail the usersEMail to set.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 *	Returns the message of the {@link BlogComment}.
	 *	@return the message of the {@link BlogComment}. 
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 *	Sets the message of the {@link BlogComment}.
	 *	@param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 *	Returns the postedOn of the {@link BlogComment}.
	 *	@return the postedOn of the {@link BlogComment}. 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 *	Sets the postedOn of the {@link BlogComment}.
	 *	@param postedOn the postedOn to set.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}