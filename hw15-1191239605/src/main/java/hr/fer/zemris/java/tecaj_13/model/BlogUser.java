package hr.fer.zemris.java.tecaj_13.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 	Class which represents an user on our blog website.
 * 
 *  @author Jakov Pucekovic
 *  @version 1.0	
 */
@Entity
@Table(name="blog_users")
public class BlogUser {

	/**Unique id of the blog user.*/
	private Long id;
	
	/**First name of the blog user.*/
	private String firstName;
	
	/**Last name of the blog user.*/
	private String lastName;
	
	/**Nickname of the blog user.*/
	private String nick;

	/**Email of the blog user.*/
	private String email;
	
	/**Password hash of the blog user.*/
	private String passwordHash;
	
	/**List of entries written by this user.*/
	private List<BlogEntry> entries;
	
	/**
	 * 	Returns the id of the {@link BlogUser}.
	 * 	@return the id of the {@link BlogUser}.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * 	Sets the id of the {@link BlogUser}.
	 * 	@param id the id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 	Returns the firstName of the {@link BlogUser}.
	 * 	@return the firstName of the {@link BlogUser}.
	 */
	@Column(length=50,nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * 	Sets the firstName of the {@link BlogUser}.
	 * 	@param firstName the firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 	Returns the lastName of the {@link BlogUser}.
	 * 	@return the lastName of the {@link BlogUser}.
	 */
	@Column(length=80,nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * 	Sets the lastName of the {@link BlogUser}.
	 * 	@param lastName the lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 	Returns the nick of the {@link BlogUser}.
	 * 	@return the nick of the {@link BlogUser}.
	 */
	@Column(length=20,nullable=false,unique=true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * 	Sets the nick of the {@link BlogUser}.
	 * 	@param nick the nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * 	Returns the email of the {@link BlogUser}.
	 * 	@return the email of the {@link BlogUser}.
	 */
	@Column(length=50,nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * 	Sets the email of the {@link BlogUser}.
	 * 	@param email the email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 	Returns the passwordHash of the {@link BlogUser}.
	 * 	@return the passwordHash of the {@link BlogUser}.
	 */
	@Column(length=40,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * 	Sets the passwordHash of the {@link BlogUser}.
	 * 	@param passwordHash the passwordHash to set.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * 	Returns the entries of the {@link BlogUser}.
	 * 	@return the entries of the {@link BlogUser}.
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * 	Sets the entries of the {@link BlogUser}.
	 * 	@param entries the entries to set.
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * 	{@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}