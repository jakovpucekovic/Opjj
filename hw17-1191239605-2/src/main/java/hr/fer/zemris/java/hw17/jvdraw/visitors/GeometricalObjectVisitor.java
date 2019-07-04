package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

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
