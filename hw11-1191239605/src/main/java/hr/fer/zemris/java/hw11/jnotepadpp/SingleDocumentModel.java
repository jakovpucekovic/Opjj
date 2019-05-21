package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.Objects;

import javax.swing.JTextArea;

/**
 *	SingleDocumentModel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface SingleDocumentModel {

	/**
	 * 	Returns the text component of this {@link SingleDocumentModel}.
	 * 	@return The text component of this {@link SingleDocumentModel}.
	 */
	JTextArea getTextComponent();
	
	/**
	 * 	Returns the file path of this {@link SingleDocumentModel}.
	 * 	@return The file path of this {@link SingleDocumentModel}.
	 */
	Path getFilePath();
	
	/**
	 * 	Sets the file path of this {@link SingleDocumentModel} to the given path.
	 * 	@param path {@link Path} to set the file path to.
	 * 	@throws NullPointerException If the given path is <code>null</code>.
	 */
	default void setFilePath(Path path) {
		Objects.requireNonNull(path);
	}
	
	/**
	 *  Checks whether this {@link SingleDocumentModel} is modified or not.
	 *  @return <code>true</code> if it is modified, <code>false</code> if not.
	 */
	boolean isModified();
	
	/**
	 * 	Sets the modified flag of this {@link SingleDocumentModel}.
	 * 	@param modified <code>true</code> if this {@link SingleDocumentModel} is 
	 * 				   modified, <code>false</code> if not.
	 */
	void setModified(boolean modified);
	
	/**
	 * 	Adds the given {@link SingleDocumentListener} to listeners
	 * 	which will be notified of changes.
	 * 	@param l {@link SingleDocumentListener} to add.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * 	Removes the given {@link SingleDocumentListener} from listeners
	 * 	which are notified of changes.
	 * 	@param l {@link SingleDocumentListener} to remove.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
	
}
