package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.RegisterUserForm;

/**
 *	BlogEntriesServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet("/servleti/author/*")
public class BlogEntriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String path = req.getPathInfo().substring(1);
		
		String nick = (String) req.getSession().getAttribute("current.user.nick");
		
		if(nick != null && path.matches(nick + "/new")) {
			BlogEntry entry = new BlogEntry();
			BlogEntryForm entryForm = new BlogEntryForm();
			entryForm.fillFromBlogEntry(entry);
			
			req.setAttribute("blogEntryForm", entryForm);
			
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
			return;
		} else if(nick != null && path.matches(nick + "/edit/\\d+")) {
			Long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			BlogEntryForm entryForm = new BlogEntryForm();
			entryForm.fillFromBlogEntry(entry);
			
			req.setAttribute("blogEntryForm", entryForm);
			
			req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
			return;
		} else if(nick != null && path.matches(nick + "/\\d+")){	
			
			
			
		} else if(path.matches(".+/\\d+")){
			Long entryId = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
			req.setAttribute("blogEntry", entry);
			
			req.getRequestDispatcher("/WEB-INF/pages/readBlog.jsp").forward(req, resp);
			return;
		}else {
			BlogUser user = DAOProvider.getDAO().getBlogUserByName(path);
			
			List<BlogEntry> list = DAOProvider.getDAO().getBlogEntriesByAuthor(user);
			req.setAttribute("blogEntries", list);
			req.setAttribute("currentPageAuthor", user);
			
			req.getRequestDispatcher("/WEB-INF/pages/listBlogs.jsp").forward(req, resp);
			return;
		}
//		else {
//			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
//			return;
//		}
//		
		
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if("Save".equals(method)) {		
			BlogEntryForm blogForm = new BlogEntryForm();
			blogForm.fillFromHttpRequest(req);
			blogForm.validate();
			
			if(blogForm.hasErrors()) {
				req.setAttribute("blogForm", blogForm);
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
				return;
			}
			
			BlogEntry entry = new BlogEntry();
			blogForm.fillBlogEntry(entry);
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(new Date());
			String nick = (String) req.getSession().getAttribute("current.user.nick");
			entry.setCreator(DAOProvider.getDAO().getBlogUserByName(nick));
			
			DAOProvider.getDAO().saveBlogEntry(entry);
		} else if("Add comment".equals(method)) {
			BlogCommentForm commentForm = new BlogCommentForm();
			commentForm.fillFromHttpRequest(req);
			commentForm.validate();
			
			if(commentForm.hasErrors()) {
				req.setAttribute("commentForm", commentForm);
				req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
				return;
			}
			
			BlogComment comment = new BlogComment();
			commentForm.fillBlogComment(comment);
			comment.setPostedOn(new Date());
			DAOProvider.getDAO().saveBlogComment(comment);
			
		}
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
	
}
