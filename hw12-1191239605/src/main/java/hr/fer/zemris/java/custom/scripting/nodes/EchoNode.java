package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 *	Class which represents a command which generates
 *	some textual output dynamically. It stores {@link Element}s.
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class EchoNode extends Node {

	/**
	 *	Elements are stored here.
	 */
	private Element[] elements;

	/**
	 *	Construct a new {@link EchoNode} and
	 *	stores the given {@link Element}s.
	 *	@param elements Elements to store.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 *	Returns the stored elements.
	 *	@return The stored elements.
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$= ");
		for(var el : elements) {
			sb.append(el).append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}
	
}
