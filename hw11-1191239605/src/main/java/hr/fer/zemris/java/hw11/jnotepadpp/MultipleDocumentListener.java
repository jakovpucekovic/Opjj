package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 *	Iterface which represents a listener capable of registering to
 *	{@link MultipleDocumentModel}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface MultipleDocumentListener {

	/**
	 * 	Action that should be executed when the currentDocument is changed.
	 * 	@param previousModel The previous currentDocument.
	 * 	@param currentModel The new currentDocument.
	 * 	@throws IllegalArgumentException If both parameters are <code>null</code>.
	 */
	default void currentDocumentChanged(SingleDocumentModel previousModel,
								SingleDocumentModel currentModel) {
		if(previousModel == null && currentModel == null) {
			throw new IllegalArgumentException("Both arguments cannot be null.");
		}
	}
	
	/**
	 * 	Action that should be executed when a new {@link SingleDocumentModel} is added.
	 * 	@param model The new {@link SingleDocumentModel} that was added.
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * 	Action that should be executed when a {@link SingleDocumentModel}
	 * 	is removed.
	 * 	@param model The removed {@link SingleDocumentModel}.
	 */
	void documentRemoved(SingleDocumentModel model);
	
}
