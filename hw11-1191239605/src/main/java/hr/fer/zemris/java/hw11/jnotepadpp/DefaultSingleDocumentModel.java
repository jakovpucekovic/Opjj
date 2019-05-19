package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;

/**
 *	DefaultSingleDocumentModel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class DefaultSingleDocumentModel implements SingleDocumentModel{

	private JTextArea textArea;
	private Path path;
	private boolean modified;
	
	private List<SingleDocumentListener> listeners;	
	
	/**
	 * 	Constructs a new DefaultSingleDocumentModel.
	 * 	TODO javadoc
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.path = filePath;
		textArea = new JTextArea(textContent);
//		textArea.add TODO dodaje se neki listener
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return path;
	}


	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.path = path;
		notifyAllListeners(true);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyAllListeners(false);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}


	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * 	Method which notifies all registered listeners that a change
	 * 	occurred.
	 * 	@param pathUpdated Flag which indicates if the path was updated. If set
	 * 					   to <code>false</code> means that the <code>modified</code>
	 * 					   variable has been modified.
	 */
	private void notifyAllListeners(boolean pathUpdated) {
		for(var l : listeners) {
			if(pathUpdated) {
				l.documentFilePathUpdated(this);
			} else {
				l.documentModifyStatusUpdated(this);
			}
		}
	}
	
}
