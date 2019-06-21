package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.servlets.util.Crypto;

/**
 *	{@link RegisterUserForm} is a class which allows for the registration
 *	of new users.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class RegisterUserForm {
	
	/**Unique id of the user.*/
	private String id;
	
	/**Last name of the user.*/
	private String lastName;
	
	/**First name of the user.*/
	private String firstName;
	
	/**Nickname of the user.*/
	private String nick;
	
	/**Password of the user.*/
	private String password;
	
	/**Email of the user.*/
	private String email;

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
		this.firstName = setup(req.getParameter("firstName"));
		this.lastName = setup(req.getParameter("lastName"));
		this.email = setup(req.getParameter("email"));
		this.nick = setup(req.getParameter("nick"));
		this.password = setup(req.getParameter("password"));
	}

	/**
	 *	Fills this {@link LoginUserForm} with the data in the given {@link BlogUser}. 
	 *	@param user {@link BlogUser} which provides the data.
	 */
	public void fillFromBlogUser(BlogUser r) {
		if(r.getId()==null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		
		this.firstName = r.getFirstName();
		this.lastName = r.getLastName();
		this.nick = r.getNick();
		this.email = r.getEmail();
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
		
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setNick(this.nick);
		user.setEmail(this.email);
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
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "First name is mandatory!");
		} else if(this.firstName.length() > 50) {
			errors.put("firstName", "First name cannot be longer than 50 characters!");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is mandatory!");
		} else if(this.firstName.length() > 50) {
			errors.put("lastName", "Last name cannot be longer than 80 characters!");
		}

		if(this.nick.isEmpty()) {
			errors.put("nick", "Nick is mandatory!");
		} else if(this.nick.length() > 20) {
			errors.put("nick", "Nick cannot be longer than 20 characters!");
		} else if(usernameExists(this.nick)) {
			errors.put("nick", "Username is already taken!");
		}
		
		if(this.email.isEmpty()) {
			errors.put("email", "EMail is mandatory!");
		} else if(emailExists(this.email)){
			errors.put("email", "An account with is already registered with this EMail.");
		}else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "EMail has wrong format.");
			}
		}
	
		if(this.password.isEmpty()) {
			errors.put("password", "Password is mandatory!"); 
		} else if(this.password.length() < 8) {
			errors.put("password", "Password must contain atleast 8 characters!");//TODO password
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
//		List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
//		for(var u : users) {
//			if(u.getNick().equals(name)) {
//				return true;
//			}
//		}
//		return false; TODO obrisi
		
		if(DAOProvider.getDAO().getBlogUserByName(name) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 	Private method which checks if an user with the given email
	 * 	already exists.
	 * 	@param email Email of the user.
	 * 	@return <code>true</code> if yes, <code>false</code> if no.
	 */
	private boolean emailExists(String email) {
		List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
		for(var u : users) {
			if(u.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	Returns the id of the {@link RegisterUserForm}.
	 * 	@return the id of the {@link RegisterUserForm}.
	 */
	public String getId() {
		return id;
	}

	/**
	 * 	Sets the id of the {@link RegisterUserForm}.
	 * 	@param id the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 	Returns the lastName of the {@link RegisterUserForm}.
	 * 	@return the lastName of the {@link RegisterUserForm}.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 	Sets the lastName of the {@link RegisterUserForm}.
	 * 	@param lastName the lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 	Returns the firstName of the {@link RegisterUserForm}.
	 * 	@return the firstName of the {@link RegisterUserForm}.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 	Sets the firstName of the {@link RegisterUserForm}.
	 * 	@param firstName the firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 	Returns the nick of the {@link RegisterUserForm}.
	 * 	@return the nick of the {@link RegisterUserForm}.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * 	Sets the nick of the {@link RegisterUserForm}.
	 * 	@param nick the nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * 	Returns the password of the {@link RegisterUserForm}.
	 * 	@return the password of the {@link RegisterUserForm}.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 	Sets the password of the {@link RegisterUserForm}.
	 * 	@param password the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 	Returns the email of the {@link RegisterUserForm}.
	 * 	@return the email of the {@link RegisterUserForm}.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 	Sets the email of the {@link RegisterUserForm}.
	 * 	@param email the email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
