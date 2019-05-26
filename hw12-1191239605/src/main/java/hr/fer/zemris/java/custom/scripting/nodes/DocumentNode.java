package hr.fer.zemris.java.custom.scripting.nodes;

/**
 *	Class which represents an entire document.
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class DocumentNode extends Node {

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
	
}
