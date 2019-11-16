package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 *	Base class for all graph nodes.
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public abstract class Node {

	/**
	 *	Private array of children of this {@link Node}. 
	 */
	private List<Node> children;
	
	/**
	 * 	Adds new child to this {@link Node}. When run,
	 * 	for the first time, creates the list of children.
	 * 	@param child Node to add as as child.
	 * 	@throws NullPointerException If given child is <code>null</code>.
	 */
	public void addChildNode(Node child) {
		if(children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}
	
	/**
	 * 	Returns the number of children this {@link Node} has.
	 * 	@return Number of children.
	 */
	public int numberOfChildren() {
		if(children == null) {
			return 0;
		}
		return children.size();
	}
	
	/**
	 * 	Returns the child at the given index.
	 * 	@param index Index of the child to return.
	 * 	@return Child {@link Node} at the given index.
	 * 	@throws NullPointerException If the {@link Node} doesn't
	 * 			have any children.
	 * 	@throws IndexOutOfBoundsException If the {@link Node} doesn't
	 * 			have a child at the given index.
	 */
	public Node getChild(int index) {
		if(children == null) {
			throw new NullPointerException("Node has no children");
		}
		return (Node) children.get(index);
	}
	
	/**
	 *	Accepts a {@link INodeVisitor} and should call the appropriate
	 *	visit method of the given {@link INodeVisitor}.
	 *	@param visitor {@link INodeVisitor} to visit this {@link Node}. 
	 */
	public abstract void accept(INodeVisitor visitor);
	
}
