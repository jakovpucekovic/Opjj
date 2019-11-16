package hr.fer.zemris.java.hw17.jvdraw.editor;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;

/**
 *	Class which enables editing of {@link GeometricalObject}s.
 *
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 *  Method which must be called before acceptEditing(). This 
	 *  method checks if the edited values are valid.
	 *  @throws RuntimeException if the values are invalid.
	 */	
	public abstract void checkEditing();
	
	/**
	 *  Method which writes the changes into the {@link GeometricalObject}.
	 */
	public abstract void acceptEditing();
	
}
