package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *	BlogUser TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

@Entity
@Table(name="blog_users")
//TODO cacheable?
public class BlogUser {

	@Id @GeneratedValue
	private Long id;
	
	@Column(length=50, nullable=false)
	private String firstName;
	
	@Column(length=80, nullable=false)
	private String lastName;
	
	@Column(length=20, nullable=false)
	//TODO kako napraviti nick unique
	private String nick;

	@Column(length=50, nullable=false)
	private String email;
	
	@Column(length=20, nullable=false)
	private String passwordHash;
	
	/**
	 * 	Returns the id of the BlogUser.
	 * 	@return the id of the BlogUser.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 	Sets the id of the BlogUser.
	 * 	@param id the id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 	Returns the firstName of the BlogUser.
	 * 	@return the firstName of the BlogUser.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * 	Sets the firstName of the BlogUser.
	 * 	@param firstName the firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 	Returns the lastName of the BlogUser.
	 * 	@return the lastName of the BlogUser.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * 	Sets the lastName of the BlogUser.
	 * 	@param lastName the lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 	Returns the nick of the BlogUser.
	 * 	@return the nick of the BlogUser.
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * 	Sets the nick of the BlogUser.
	 * 	@param nick the nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * 	Returns the email of the BlogUser.
	 * 	@return the email of the BlogUser.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 	Sets the email of the BlogUser.
	 * 	@param email the email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 	Returns the passwordHash of the BlogUser.
	 * 	@return the passwordHash of the BlogUser.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * 	Sets the passwordHash of the BlogUser.
	 * 	@param passwordHash the passwordHash to set.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
