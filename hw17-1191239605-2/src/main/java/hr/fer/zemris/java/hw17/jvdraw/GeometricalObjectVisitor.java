package hr.fer.zemris.java.hw17.jvdraw;

/**
 *	GeometricalObjectVisitor TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public interface GeometricalObjectVisitor {

	public abstract void visit(Line line);
	
	public abstract void visit(Circle circle);
	
	public abstract void visit(FilledCircle filledCircle);
	
}
