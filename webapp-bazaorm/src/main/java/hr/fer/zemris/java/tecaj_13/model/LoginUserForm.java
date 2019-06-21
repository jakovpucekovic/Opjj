package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw06.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 *	LoginUserForm TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class LoginUserForm {

	private String id;
		
	private String nick;
	
	private String password;
	

	
	Map<String, String> errors = new HashMap<>();
	
	
	public String getError(String name) {
		return errors.get(name);
	}
	
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.id = setup(req.getParameter("id"));
		this.nick = setup(req.getParameter("nick"));
		this.password = setup(req.getParameter("password"));
	}

	
	public void fillFromBlogUser(BlogUser r) {
		if(r.getId()==null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		
		this.nick = r.getNick();
//		this.password = r.getPasswordHash(); //TODO
	}

	
	public void fillBlogUser(BlogUser user) {
		if(this.id.isEmpty()) {
			user.setId(null);
		} else {
			user.setId(Long.valueOf(this.id));
		}
		
		user.setNick(this.nick);
		user.setPasswordHash(Crypto.bytetohex(Crypto.sha(this.password)));
	}
	
	
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
		} else if(this.password.length() < 8) {
			errors.put("password", "Password must contain atleast 8 characters!");//TODO password
		} else {
			List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
			for(var u : users) {
				if(u.getNick().equals(this.nick)) {
					if(!u.getPasswordHash().equals(Crypto.bytetohex(Crypto.sha(this.password)))) {
						errors.put("password", "Wrong password!");
					}
					break;
				}
			}
		}
		
	}
	
	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne stringove, što je
	 * puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string inače.
	 */
	private String setup(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	private boolean usernameExists(String name) {
		List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
		for(var u : users) {
			if(u.getNick().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	Returns the id of the BlogUserForm.
	 * 	@return the id of the BlogUserForm.
	 */
	public String getId() {
		return id;
	}

	/**
	 * 	Sets the id of the BlogUserForm.
	 * 	@param id the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 	Returns the nick of the BlogUserForm.
	 * 	@return the nick of the BlogUserForm.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * 	Sets the nick of the BlogUserForm.
	 * 	@param nick the nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * 	Returns the password of the BlogUserForm.
	 * 	@return the password of the BlogUserForm.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 	Sets the password of the BlogUserForm.
	 * 	@param password the password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 	Returns the email of the BlogUserForm.
	 * 	@return the email of the BlogUserForm.
	 */

	
}
