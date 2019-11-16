package searching.algorithms;

/**
 *	Class which represents a node in the searching algorithm
 *	which stores the current state, cost and its parent {@link Node}.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Node<S> {

	/**State of the {@link Node}.*/
	private S state;

	/**Parent of the {@link Node}.*/
	private Node<S> parent;
	
	/**Cost of the {@link Node}.*/
	private double cost;
	
	/**
	 * 	Constructs a new {@link Node} with the given paremeters.	
	 * 	@param parent The parent of the {@link Node}.
	 * 	@param state The state of the {@link Node}.
	 * 	@param cost The cost of the {@link Node}.
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
	}
	
	/**
	 * 	Returns the state of this {@link Node}.
	 * 	@return The state of this {@link Node}.
	 */
	public S getState() {
		return state;
	}
	
	/**
	 *  Returns the cost of this {@link Node}.	
	 *  @return The cost of this {@link Node}.
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * 	Returns the parent {@link Node} of this {@link Node}.
	 * 	@return The parent {@link Node} or <code>null</code>
	 * 			if this {@link Node} doesn't have a parent.
	 */
	public Node<S> getParent(){
		return parent;
	}
}
