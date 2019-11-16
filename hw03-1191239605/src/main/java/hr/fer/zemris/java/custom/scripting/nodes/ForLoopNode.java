package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVarible;

/**
 *	Class which represents a for-loop construct.
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class ForLoopNode extends Node {

	/**
	 *	Name of the variable in the for-loop.
	 */
	private ElementVarible variable;
	/**
	 *	Start expression of the for-loop.
	 */
	private Element startExpression;
	/**
	 *	End expression of the for-loop.
	 */
	private Element endExpression;
	/**
	 *	Step expression of the for-loop. Can be null.
	 */
	private Element stepExpression;
	
	/**
	 *	Costructs a new {@link ForLoopNode} with the
	 *	given values.
	 *	@param variable	Variable of the for-loop.
	 *	@param startExpression Start expression of the for-loop.
	 *	@param endExpression End expression of the for-loop.
	 *	@param stepExpression Step expression of the for-loop, can be <code>null</code>.
	 */
	public ForLoopNode(ElementVarible variable, Element startExpression, Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 *	Returns the variable of the for-loop.
	 *	@return The variable of the for-loop.
	 */
	public ElementVarible getVariable() {
		return variable;
	}
	
	/**
	 *	Returns the start expression of the for-loop.
	 *	@return The start expression of the for-loop.
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 *	Returns the end expression of the for-loop.
	 *	@return The end expression of the for-loop.
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 *	Returns the step expression of the for-loop.
	 *	@return The step expression of the for-loop.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
}
