package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

/**
 *	Interface which implements the visitor designe patter for {@link GeometricalObject}s.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface GeometricalObjectVisitor {

	/**
	 * 	Is called when this visitor visits a {@link Line}.
	 * 	@param line {@link Line} to visit.
	 */
	public abstract void visit(Line line);
	
	/**
	 * 	Is called when this visitor visits a {@link Circle}.
	 * 	@param circle {@link Circle} to visit.
	 */
	public abstract void visit(Circle circle);
	
	/**
	 *  Is called when this visitor visits a {@link FilledCircle}.	
	 *  @param filledCircle {@link FilledCircle} to visit.
	 */
	public abstract void visit(FilledCircle filledCircle);
	
}
