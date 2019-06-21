package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw06.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 *	BlogUserForn TODO javadoc za sve
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class RegisterUserForm {

	
	private String id;
	
	private String lastName;
	
	private String firstName;
	
	private String nick;
	
	private String password;
	
	private String email;

	/**
	 * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a vrijednosti
	 * tekstovi pogrešaka.
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu pogrešku
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku. 
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
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
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r objekt koji čuva originalne podatke
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
//		this.password = r.getPasswordHash(); //TODO
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
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
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način potrebno
	 * napuniti. Metoda provjerava semantičku korektnost svih podataka te po potrebi
	 * registrira pogreške u mapu pogrešaka.
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
	 * 	Returns the lastName of the BlogUserForm.
	 * 	@return the lastName of the BlogUserForm.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 	Sets the lastName of the BlogUserForm.
	 * 	@param lastName the lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 	Returns the firstName of the BlogUserForm.
	 * 	@return the firstName of the BlogUserForm.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 	Sets the firstName of the BlogUserForm.
	 * 	@param firstName the firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public String getEmail() {
		return email;
	}

	/**
	 * 	Sets the email of the BlogUserForm.
	 * 	@param email the email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
