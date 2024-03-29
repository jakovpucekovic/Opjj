package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	public List<BlogUser> getAllBlogUsers() throws DAOException;
	
	public void saveBlogEntry(BlogEntry entry) throws DAOException;
	
	public void saveBlogUser(BlogUser user) throws DAOException;
	
	public void saveBlogComment(BlogComment comment) throws DAOException;
	
}