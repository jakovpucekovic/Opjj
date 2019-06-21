package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


@Entity
@Table(name="blog_users")
public class BlogUser {

private Long id;
	
	private String firstName;
	
	private String lastName;
	
	//TODO kako napraviti nick unique
	private String nick;

	private String email;
	
	private String passwordHash;
	
	/**
	 * 	Returns the id of the BlogUser.
	 * 	@return the id of the BlogUser.
	 */
	@Id @GeneratedValue
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
	@Column(length=50,nullable=false)
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
	@Column(length=80,nullable=false)
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
	@Column(length=20,nullable=false)
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
	@Column(length=50,nullable=false)
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
	@Column(length=40,nullable=false)
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