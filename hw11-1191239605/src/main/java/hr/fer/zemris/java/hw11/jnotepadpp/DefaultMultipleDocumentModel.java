package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 *	DefaultMultipleDocumentModel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	
	private List<MultipleDocumentListener> listeners;
	
	private ImageIcon modifiedIcon;
	private ImageIcon unmodifiedIcon;
	
	public void setModifiedIcons(ImageIcon icon1, ImageIcon icon2) {
		this.modifiedIcon = icon1;
		this.unmodifiedIcon = icon2;
	}
	
	/**
	 * 	Constructs a new DefaultMultipleDocumentModel.
	 * 	TODO javadoc
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
			
		listeners = new ArrayList<>();
		
	}

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
		currentDocument = new DefaultSingleDocumentModel(null, "");
		currentDocument.addSingleDocumentListener(update);
		documents.add(currentDocument);
		
		for(var l : listeners) {
			l.documentAdded(currentDocument);
		}
		
		addTab("(unnamed)",	new JScrollPane(currentDocument.getTextComponent()));
		setIconAt(documents.size() - 1, modifiedIcon);

		return currentDocument;
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
			if(doc.getFilePath() == null) {
				continue;
			}
			if(doc.getFilePath().equals(path)) {
				currentDocument = doc;
				
				setSelectedIndex(documents.indexOf(currentDocument));
				
				return currentDocument;
			}
		}
		if(!Files.isReadable(path)) {
			throw new IllegalArgumentException("Path doesn't lead to a readable file.");//TODO sto ako damo nesto sto nije text?
		}
		try {
			currentDocument = new DefaultSingleDocumentModel(path, Files.readString(path));
			currentDocument.addSingleDocumentListener(update);
			documents.add(currentDocument);
			
			for(var l : listeners) {
				l.documentAdded(currentDocument);
			}
			
			addTab(currentDocument.getFilePath().getFileName().toString(),
					new JScrollPane(currentDocument.getTextComponent()));
			setSelectedIndex(documents.size() - 1);;
			return currentDocument;
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot read path.");//TODO nije dobro rjesenje
		}
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model);
		SingleDocumentModel previous = currentDocument;
		try {//TODO sta s iznimkama, tko ih obraduje?
			if(newPath == null) {
				Files.writeString(model.getFilePath(), model.getTextComponent().getText());
			} else {
				Files.writeString(newPath, model.getTextComponent().getText());
			}
		} catch(IOException ex) {
			//TODO napravi nes
		}
		model.setFilePath(newPath);
		model.setModified(false);
		
		setSelectedIndex(documents.indexOf(currentDocument));
		
		for(var l : listeners) {
			l.currentDocumentChanged(previous, currentDocument);
		}
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		Objects.requireNonNull(model);
		
		int index = documents.indexOf(model);
		
		if(documents.size() > 1) {
			currentDocument = documents.get(index == 0 ? 1 : (index - 1));
		} else {
			currentDocument = null;
		}
		documents.remove(model);
		
		removeTabAt(index);
		
		for(var l : listeners) {
			l.documentRemoved(model);
		}
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
	
}
