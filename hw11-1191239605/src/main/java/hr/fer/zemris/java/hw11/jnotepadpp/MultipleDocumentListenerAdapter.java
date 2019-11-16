package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 *	Adapter class for {@link MultipleDocumentListener} with empty implementations of
 *	all methods.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public abstract class MultipleDocumentListenerAdapter implements MultipleDocumentListener {

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		MultipleDocumentListener.super.currentDocumentChanged(previousModel, currentModel);
	}
	
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void documentAdded(SingleDocumentModel model) {
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void documentRemoved(SingleDocumentModel model) {
	}

}
