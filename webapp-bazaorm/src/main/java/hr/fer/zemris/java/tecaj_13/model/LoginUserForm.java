package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.servlets.util.Crypto;

/**
 *	LoginUserForm is a class which helps during the login process.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class LoginUserForm {

	/**Unique id of the user.*/
	private String id;
		
	/**Nickname of the user.*/
	private String nick;
	
	/**Password of the user.*/
	private String password;
	
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
		this.nick = setup(req.getParameter("nick"));
		this.password = setup(req.getParameter("password"));
	}

	/**
	 *	Fills this {@link LoginUserForm} with the data in the given {@link BlogUser}. 
	 *	@param user {@link BlogUser} which provides the data.
	 */
	public void fillFromBlogUser(BlogUser user) {
		if(user.getId()==null) {
			this.id = "";
		} else {
			this.id = user.getId().toString();
		}
		
		this.nick = user.getNick();
	}

	/**
	 * 	Fills the given {@link BlogUser} with the data stored in this
	 * 	{@link LoginUserForm}.
	 * 	@param user {@link BlogUser} to fill.
	 */
	public void fillBlogUser(BlogUser user) {
		if(this.id.isEmpty()) {
			user.setId(null);
		} else {
			user.setId(Long.valueOf(this.id));
		}
		
		user.setNick(this.nick);
		user.setPasswordHash(Crypto.bytetohex(Crypto.sha(this.password)));
	}
	
	/**
	 * 	Checks if this {@link LoginUserForm} contains valid data
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

		if(this.nick.isEmpty()) {
			errors.put("nick", "Nick is mandatory!");
		} else if(this.nick.length() > 20) {
			errors.put("nick", "Nick cannot be longer than 20 characters!");
		} else if(!usernameExists(this.nick)) {
			errors.put("nick", "Username doesn't exist!");
		}
	
		if(this.password.isEmpty()) {
			errors.put("password", "Password is mandatory!"); 
		} else {
			List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
			for(var u : users) {
				if(u.getNick().equals(this.nick)) {
					if(!u.getPasswordHash().equals(Crypto.bytetohex(Crypto.sha(this.password)))) {
						errors.put("nick", "Invalid username or password!");
					}
					break;
				}
			}
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
	 * 	Private method which checks if an user with the given nickname
	 * 	already exists.
	 * 	@param name Nickname of the user.
	 * 	@return <code>true</code> if yes, <code>false</code> if no.
	 */
	private boolean usernameExists(String name) {
		if(DAOProvider.getDAO().getBlogUserByName(name) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 	Returns the id of the {@link LoginUserForm}.
	 * 	@return the id of the {@link LoginUserForm}.
	 */
	public String getId() {
		return id;
	}

	/**
	 * 	Sets the id of the {@link LoginUserForm}.
	 * 	@param id the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 	Returns the nick of the {@link LoginUserForm}.
	 * 	@return the nick of the {@link LoginUserForm}.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * 	Sets the nick of the {@link LoginUserForm}.
	 * 	@param nick the nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * 	Returns the password of the {@link LoginUserForm}.
	 * 	@return the password of the {@link LoginUserForm}.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 	Sets the password of the {@link LoginUserForm}.
	 * 	@param password the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
