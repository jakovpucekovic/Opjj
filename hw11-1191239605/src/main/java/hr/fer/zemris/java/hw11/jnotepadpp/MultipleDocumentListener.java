package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 *	MultipleDocumentListener TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface MultipleDocumentListener {

	default void currentDocumentChanged(SingleDocumentModel previousModel, 
								SingleDocumentModel currentModel) {
		if(previousModel == null && currentModel == null) {
			throw new IllegalArgumentException("Both arguments cannot be null.");
		}
	}
	
	void documentAdded(SingleDocumentModel model);
	
	void documentRemoved(SingleDocumentModel model);
	
}
