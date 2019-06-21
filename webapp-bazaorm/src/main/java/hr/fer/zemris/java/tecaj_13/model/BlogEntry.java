package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**Unique Id of the blog entry.*/
	private Long id;

	/**{@link List} of comments on this blog entry.*/
	private List<BlogComment> comments = new ArrayList<>();
	
	/**Time this blog entry was created.*/
	private Date createdAt;
	
	/**Time this blog entry was last modified.*/
	private Date lastModifiedAt;
	
	/**Title of the blog entry.*/
	private String title;
	
	/**Body of the blog entry.*/
	private String text;
	
	/**Creator of the blog entry.*/
	private BlogUser creator; //TODO dodaj list<BlogEntry> u blog user
	//TODO dodaj getter i setter za creatora

	/**
	 * 	Returns the id of the {@link BlogEntry}.
	 * 	@return the id of the {@link BlogEntry}.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * 	Sets the id of the {@link BlogEntry}.
	 * 	@param id the id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 	Returns the comments of the {@link BlogEntry}.
	 * 	@return the comments of the {@link BlogEntry}.
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * 	Sets the comments of the {@link BlogEntry}.
	 * 	@param comments the comments to set.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * 	Returns the createdAt of the {@link BlogEntry}.
	 * 	@return the createdAt of the {@link BlogEntry}.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * 	Sets the createdAt of the {@link BlogEntry}.
	 * 	@param createdAt the createdAt to set.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 	Returns the lastModifiedAt of the {@link BlogEntry}.
	 * 	@return the lastModifiedAt of the {@link BlogEntry}.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * 	Sets the lastModifiedAt of the {@link BlogEntry}.
	 * 	@param lastModifiedAt the lastModifiedAt to set.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * 	Returns the title of the {@link BlogEntry}.
	 * 	@return the title of the {@link BlogEntry}.
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * 	Sets the title of the {@link BlogEntry}.
	 * 	@param title the title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * 	Sets the text of the {@link BlogEntry}.
	 * 	@param text the text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

//	/**
//	 * 	Returns the creator of the BlogEntry.
//	 * 	@return the creator of the BlogEntry.
//	 */
//	//TODO fali spec i veza
//	public BlogUser getCreator() {
//		return creator;
//	}
//
//	/**
//	 * 	Sets the creator of the BlogEntry.
//	 * 	@param creator the creator to set.
//	 */
//	public void setCreator(BlogUser creator) {
//		this.creator = creator;
//	}

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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}