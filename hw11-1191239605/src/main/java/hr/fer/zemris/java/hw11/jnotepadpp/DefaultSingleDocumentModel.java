package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *	One implementation of {@link SingleDocumentModel} interface.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class DefaultSingleDocumentModel implements SingleDocumentModel{

	/**Text area of the document*/
	private JTextArea textArea;
	/**Path of the document*/
	private Path path;
	/**Flag whether the document is modified*/
	private boolean modified;
	/**List of registered listeners*/
	private List<SingleDocumentListener> listeners;	
	
	
	/**
	 * 	Constructs a new {@link DefaultSingleDocumentModel} with the given path
	 * 	and the given text.
	 * 	@param filePath {@link Path} to the document.
	 * 	@param textContent Content of the document.
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.path = filePath;
		listeners = new ArrayList<>();
		textArea = new JTextArea(textContent);
		textArea.setPreferredSize(new Dimension(600, 800));
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
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
