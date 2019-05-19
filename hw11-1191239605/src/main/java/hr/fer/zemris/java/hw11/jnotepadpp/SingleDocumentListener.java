package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 *	SingleDocumentListener TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface SingleDocumentListener {

	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	void documentFilePathUpdated(SingleDocumentModel model);
	
}
