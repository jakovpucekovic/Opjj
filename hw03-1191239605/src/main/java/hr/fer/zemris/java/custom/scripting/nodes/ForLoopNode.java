package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVarible;

public class ForLoopNode extends Node {

	private ElementVarible variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; //can be null
	
	
	public ForLoopNode(ElementVarible variable, Element startExpression, Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	public ElementVarible getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
}
