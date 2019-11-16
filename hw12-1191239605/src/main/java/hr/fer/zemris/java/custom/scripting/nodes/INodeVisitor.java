package hr.fer.zemris.java.custom.scripting.nodes;

/**
 *	Interface which describes visitors for the {@link Node} class
 * 	implementing the visitor pattern.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public interface INodeVisitor {

	/**
	 *	Method to call when visiting a {@link TextNode}.
	 *	@param node {@link TextNode} to visit. 	
	 */
	void visitTextNode(TextNode node);
	
	/**
	 *	Method to call when visiting a {@link ForLoopNode}.
	 *	@param node {@link ForLoopNode} to visit. 	
	 */
	void visitForLoopNode(ForLoopNode node);
	
	/**
	 *	Method to call when visiting a {@link EchoNode}.
	 *	@param node {@link EchoNode} to visit. 	
	 */
	void visitEchoNode(EchoNode node);
	
	/**
	 *	Method to call when visiting a {@link DocumentNode}.
	 *	@param node {@link DocumentNode} to visit. 	
	 */
	void visitDocumentNode(DocumentNode node);
}
