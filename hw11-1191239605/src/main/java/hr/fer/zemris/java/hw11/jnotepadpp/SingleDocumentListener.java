package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 *	Interface which describes a listener capable of registering
 *	to {@link SingleDocumentModel}.
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
