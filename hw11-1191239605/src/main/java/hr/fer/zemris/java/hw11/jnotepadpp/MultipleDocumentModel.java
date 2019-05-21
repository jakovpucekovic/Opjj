package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.Objects;

/**
 *	MultipleDocumentModel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{

	/**
	 *  Creates a new {@link SingleDocumentModel}.
	 *  @return A new {@link SingleDocumentModel}.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * 	Returns the currently active {@link SingleDocumentModel}.
	 * 	@return The currently active {@link SingleDocumentModel}.
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 *  Creates and returns a new {@link SingleDocumentModel} of the 
	 *  file at the given {@link Path}.
	 *  @param path {@link Path} to the file.
	 * 	@throws NullPointerException If the given path is <code>null</code>.
	 */
	default SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		return null;
	}
	
	/**
	 * 	Saves the given {@link SingleDocumentModel} at the given {@link Path}.
	 * 	If newPath is <code>null</code>, the model is saved to its path, else
	 * 	its path is updated to the given path and its saved on the given path.
	 * 	@param model {@link SingleDocumentModel} to save.
	 * 	@param newPath {@link Path} to where the model should be saved.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * 	Closes the given {@link SingleDocumentModel}.
	 * 	@param model {@link SingleDocumentModel} to close.
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * 	Adds the given {@link MultipleDocumentListener} to listeners
	 * 	which will be notified of changes.
	 * 	@param l {@link MultipleDocumentListener} to add.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * 	Removes the given {@link MultipleDocumentListener} from listeners
	 * 	which are notified of changes.
	 * 	@param l {@link MultipleDocumentListener} to remove.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * 	Returns the number of stored {@link SingleDocumentModel}s.
	 * 	@return The number of stored {@link SingleDocumentModel}s.
	 */
	int getNumberOfDocuments();
	
	/**
	 * 	Returns the {@link SingleDocumentModel} at the given index.
	 * 	@return The {@link SingleDocumentModel} at the given index.
	 */
	SingleDocumentModel getDocument(int index);

}
