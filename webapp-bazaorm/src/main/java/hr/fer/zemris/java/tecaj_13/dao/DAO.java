package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Returns the {@link BlogEntry} with the given id.
	 * @param id Id of the {@link BlogEntry} to return.
	 * @return {@link BlogEntry} with the given id.
	 * @throws DAOException if anything goes wrong.
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns the {@link BlogUser} with the given id.
	 * @param id Id of the {@link BlogUser} to return.
	 * @return {@link BlogUser} with the given id.
	 * @throws DAOException if anything goes wrong.
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Returns the {@link BlogUser} with the given nickname.
	 * @param nick Nickname of the {@link BlogUser}.
	 * @return {@link BlogUser} with the given nickname.
	 * @throws DAOException if anything goes wrong.
	 */
	public BlogUser getBlogUserByName(String nick) throws DAOException;
	
	/**
	 * Returns a {@link List} of all {@link BlogUser}s.
	 * @return {@link List} of all {@link BlogUser}s.
	 * @throws DAOException if anything goes wrong.
	 */
	public List<BlogUser> getAllBlogUsers() throws DAOException;
	
	/**
	 * Saves the given {@link BlogEntry}.
	 * @param entry {@link BlogEntry} to save.
	 * @throws DAOException if anything goes wrong.
	 */
	public void saveBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Saves the given {@link BlogUser}.
	 * @param user {@link BlogUser} to save.
	 * @throws DAOException if anything goes wrong.
	 */
	public void saveBlogUser(BlogUser user) throws DAOException;

	/**
	 * Saves the given {@link BlogComment}.
	 * @param comment {@link BlogComment} to save.
	 * @throws DAOException if anything goes wrong.
	 */
	public void saveBlogComment(BlogComment comment) throws DAOException;
	
	//TODO javadoc
	public List<BlogEntry> getBlogEntriesByAuthor(BlogUser user) throws DAOException;
	
}