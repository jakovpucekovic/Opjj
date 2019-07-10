package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<BlogUser> getAllBlogUsers() throws DAOException {
		List<BlogUser> list = JPAEMProvider.getEntityManager().createQuery("Select bu from BlogUser bu", BlogUser.class).getResultList();
		return list;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void saveBlogEntry(BlogEntry entry) throws DAOException {
		if(entry.getId() == null) {
			JPAEMProvider.getEntityManager().persist(entry);
		} else {
			JPAEMProvider.getEntityManager().merge(entry);
		}		
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void saveBlogUser(BlogUser user) throws DAOException {
		if(user.getId() == null) {
			JPAEMProvider.getEntityManager().persist(user);
		} else {
			JPAEMProvider.getEntityManager().merge(user);
		}	
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void saveBlogComment(BlogComment comment) throws DAOException {
		if(comment.getId() == null) {
			JPAEMProvider.getEntityManager().persist(comment);
		} else {
			JPAEMProvider.getEntityManager().merge(comment);
		}
	}
	
}