package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
	
	/**
	 * 	Constructs a new DefaultMultipleDocumentModel.
	 * 	TODO javadoc
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		documents.add(new DefaultSingleDocumentModel(null, null));
		addTab("new", documents.get(0).getTextComponent());
	
		
		listeners = new ArrayList<>();
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		return new DefaultSingleDocumentModel(null, "new");
		
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
		//TODO
		return null;
	}
	
	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		documents.remove(model);
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
