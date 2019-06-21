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
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
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
	public BlogUser getBlogUserByName(String nick) throws DAOException {
		List<BlogUser> list = JPAEMProvider.getEntityManager()
										   .createQuery("Select bu from BlogUser bu where bu.nick=:ni", BlogUser.class)
										   .setParameter("ni", nick)
										   .getResultList(); //TODO upit
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
		
//		List<BlogUser> list = JPAEMProvider.getEntityManager().createQuery("Select bu from BlogUser bu", BlogUser.class).getResultList();
//		for(var u : list) {
//			if(u.getNick().equals(nick)) {
//				return u;
//			}
//		}
//		return null;
	
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

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getBlogEntriesByAuthor(BlogUser user) throws DAOException {
		List<BlogEntry> list = JPAEMProvider.getEntityManager()
											.createQuery("Select be from BlogEntry be where be.creator=:nick", BlogEntry.class)
											.setParameter("nick", user.getNick())
											.getResultList();//TODO where autor = nick ili autor = user???
		return list;
	}
	
}