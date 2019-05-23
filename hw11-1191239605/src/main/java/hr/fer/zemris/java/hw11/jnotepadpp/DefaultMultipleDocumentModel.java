package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *	Implementation of {@link MultipleDocumentModel} which is also a {@link JTabbedPane}
 *	component.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	private static final long serialVersionUID = 1L;
	
	/**{@link List} of all other documnets.*/
	private List<SingleDocumentModel> documents;
	/**Currently open document.*/
	private SingleDocumentModel currentDocument;
	
	/**{@link List} of registered listeners.*/
	private List<MultipleDocumentListener> listeners;
	
	/**Icon which is shown when a documnet is modified.*/
	private ImageIcon modifiedIcon;
	/**Icon which is shown when a document is not modified.*/
	private ImageIcon unmodifiedIcon;	
	
	/**
	 * 	Constructs a new {@link DefaultMultipleDocumentModel}.
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel previous = currentDocument;
				currentDocument = documents.get(getSelectedIndex());
				notifyListenersChanged(previous, currentDocument);
			}
		});
	}

	/**
	 * 	Sets the modified and unmodified icons.
	 * 	@param icon1 The modified icon.
	 * 	@param icon2 The unmodified icon.
	 */
	public void setModifiedIcons(ImageIcon icon1, ImageIcon icon2) {
		this.modifiedIcon = icon1;
		this.unmodifiedIcon = icon2;
	}
	
	/**
	 * 	{@link SingleDocumentListener} which updates the modified icon when a document is modified.
	 */
	private SingleDocumentListener update = new SingleDocumentListener() {
		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if(model.isModified()) {
				setIconAt(documents.indexOf(model), modifiedIcon);
			} else {
				setIconAt(documents.indexOf(model), unmodifiedIcon);
			}
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {			
			setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
		}
	};
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		
		//add new document
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		newDocument.addSingleDocumentListener(update);
		documents.add(newDocument);
		
		notifyListenersAdded(newDocument);
		
		//switch tab to new document
		SingleDocumentModel previousDoc = currentDocument;
		addTab("(unnamed)", new JScrollPane(newDocument.getTextComponent()));
		currentDocument = newDocument;
		setSelectedIndex(documents.indexOf(newDocument));
		setIconAt(documents.indexOf(newDocument), modifiedIcon);
		
		notifyListenersChanged(previousDoc, newDocument);
		
		return newDocument;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		
		for(var doc : documents) {
			//if file with given path already opened, switch to it
			if(doc.getFilePath() != null && doc.getFilePath().equals(path)) {
				currentDocument = doc;
				setSelectedIndex(documents.indexOf(doc));
				return doc;
			}
		}
		
		if(!Files.exists(path)){
			throw new RuntimeException("Path to a non existing file.");
		}
		if(!Files.isReadable(path)) {
			throw new RuntimeException("Path to non readable file.");
		}
		
		SingleDocumentModel newDocument;
		try {
			newDocument = new DefaultSingleDocumentModel(path, Files.readString(path));
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		newDocument.addSingleDocumentListener(update);
		documents.add(newDocument);
		addTab(newDocument.getFilePath().getFileName().toString(), new JScrollPane(newDocument.getTextComponent()));
		notifyListenersAdded(newDocument);
		
		//if there is the only open tab is a default unmodified tab, remove it
		if(documents.size() == 2 && !currentDocument.isModified() && currentDocument.getFilePath() == null) {
			closeDocument(currentDocument);
		}		
					
		currentDocument = newDocument;
		setSelectedIndex(documents.indexOf(newDocument));
		setIconAt(documents.indexOf(newDocument), modifiedIcon);
		return newDocument;
		
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model);

		if(newPath != null) {
			for(var doc : documents) {
				if(model.equals(doc) || doc.getFilePath() == null) {
					continue;
				}
				if(newPath.equals(doc.getFilePath())) {
					throw new IllegalArgumentException("Another document with the given path is already opened.");
				}
			}
			
			model.setFilePath(newPath);
		}
		
		try {
			Files.writeString(model.getFilePath(), model.getTextComponent().getText(), Charset.forName("utf8"));
		} catch(IOException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		model.setModified(false);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		
		if(currentDocument.equals(model)) {
			//if closing current document, switch to next
			if(documents.size() > 1) {
				int newIndex = index - 1 < 0 ? 0 : (index - 1);
				currentDocument = documents.get(newIndex);
				setSelectedIndex(newIndex);
				notifyListenersChanged(model, currentDocument);
			//if closing only document, create new empty document as current
			} else {
				createNewDocument();
			}
		}
		
		documents.remove(index);
		removeTabAt(index);
		notifyListenersRemoved(model);
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * 	Notifies all listeners that a {@link SingleDocumentModel} was removed.
	 * 	@param model The removed {@link SingleDocumentModel}.
	 */
	private void notifyListenersRemoved(SingleDocumentModel model) {
		for(var l : listeners) {
			l.documentRemoved(model);
		}
	}
	
	/**
	 * 	Notifies all listeners that a new {@link SingleDocumentModel} was added.
	 * 	@param model The added {@link SingleDocumentModel}.
	 */
	private void notifyListenersAdded(SingleDocumentModel model) {
		for(var l : listeners) {
			l.documentAdded(model);
		}
	}
	
	/**
	 * 	Notifies all listeners that the current {@link SingleDocumentModel} changed.
	 * 	@param previous Previous {@link SingleDocumentModel}.
	 *	@param current New current {@link SingleDocumentModel}.
	 */
	private void notifyListenersChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for(var l : listeners) {
			l.currentDocumentChanged(previous, current);
		}
	}
}
