package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.Objects;

/**
 *	MultipleDocumentModel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{

	SingleDocumentModel createNewDocument();
	
	SingleDocumentModel getCurrentDocument();
	
	default SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		return null;
	}
	
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	void closeDocument(SingleDocumentModel model);
	
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	int getNumberOfDocuments();
	
	SingleDocumentModel getDocument(int index);

}
