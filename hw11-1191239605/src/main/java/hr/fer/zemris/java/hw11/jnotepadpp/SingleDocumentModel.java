package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.Objects;

import javax.swing.JTextArea;

/**
 *	SingleDocumentModel TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface SingleDocumentModel {

	JTextArea getTextComponent();
	
	Path getFilePath();
	
	default void setFilePath(Path path) {
		Objects.requireNonNull(path);
	}
	
	boolean isModified();
	
	void setModified(boolean modified);
	
	void addSingleDocumentListener(SingleDocumentListener l);
	
	void removeSingleDocumentListener(SingleDocumentListener l);
	
}
