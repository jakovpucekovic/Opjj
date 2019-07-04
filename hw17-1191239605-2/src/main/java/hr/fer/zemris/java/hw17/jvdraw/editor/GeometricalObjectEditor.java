package hr.fer.zemris.java.hw17.jvdraw.editor;

import javax.swing.JPanel;

/**
 *	GeometricalObjectEditor TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	public abstract void checkEditing();
	
	public abstract void acceptEditing();
	
}
