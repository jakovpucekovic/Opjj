package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 *	SingleDocumentListener TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface SingleDocumentListener {

	/**
	 * 	Action that should be executed when the modify status of the
	 * 	{@link SingleDocumentModel} is updated.
	 * 	@param model The observed {@link SingleDocumentModel}. 
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * 	Action that should be executed when the file path of the 
	 * 	{@link SingleDocumentModel} is updated.
	 * 	@param model The observed {@link SingleDocumentModel}.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
	
}
