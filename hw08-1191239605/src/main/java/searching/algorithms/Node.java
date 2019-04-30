package searching.algorithms;

/**
 *	Node TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Node<S> {

	private S state;
	private Node<S> parent;
	private double cost;
	
	public Node(Node<S> parent, S state, double cost) {
		this.state = state;
		this.parent = parent;
		this.cost = cost;
	}
	
	public S getState() {
		return state;
	}
	
	public double getCost() {
		return cost;
	}
	
	public Node<S> getParent(){
		return parent;
	}
}
