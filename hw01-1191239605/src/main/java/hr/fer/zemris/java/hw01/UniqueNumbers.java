package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 *	Class <code>UniqueNumbers</code> which allows the user to work with a binary search tree.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0
 */
public class UniqueNumbers {

	/**
	 * The method which interacts with the user, adds valid user input to the binary search tree and prints the input sorted from lower to higher and from higher to lower.
	 * Input "kraj" to signal the end of the input.
	 * 
	 * @param args No arguments.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String scannerInput;
		
		TreeNode head = null;
		
		while(true) {
			System.out.format("Unesite broj > ");
			scannerInput = sc.next();
			if(scannerInput.equals("kraj")) {
				break;
			}
			
			int userInput;
			try {
				userInput = Integer.parseInt(scannerInput);
			} catch (NumberFormatException ex) {
				System.out.format("'%s' nije cijeli broj.%n", scannerInput);
				continue;
			}
			if(containsValue(head, userInput)) {
				System.out.format("Broj već postoji. Preskačem.%n");
			}
			else {
				head = addNode(head, userInput);
				System.out.format("Dodano.%n");
			}
		}
		
		System.out.format("Ispis od najmanjeg: ");
		printTree(head);
		System.out.format("%nIspis of najvećeg: ");
		printTreeReverse(head);
		System.out.format("%n");
		
		sc.close();
	}
	
	/**
	 * Class <code>TreeNode</code> that simulates a node in the binary tree. 
	 * Contains an int value and left and right children. 
	 */
	public static class TreeNode {
		int value;
		TreeNode left = null;
		TreeNode right = null;
	}
	
	/**
	 *	Adds another node in the tree and assigns it the value. Does nothing if the tree already contains the value.
	 *	The value of each left child is lower that the value of its parent and the value of each right child is greater	that the value of its parent.
	 *
	 *	@param newValue Value that the node is assigned.
	 *	@return The tree with the inserted value. 
	 */
	public static TreeNode addNode(TreeNode node, int newValue) {
		//if node doesn't exist, makes a new node and assigns it the value
		if(node == null) {
			node = new TreeNode();
			node.value = newValue;
			return node;
		}
		if(node.value > newValue) {
			node.left = addNode(node.left, newValue);
			return node;
		}
		if(node.value < newValue) {
			node.right = addNode(node.right, newValue);
			return node;
		}
		return node;
	}

	/**
	 *	Counts the number of nodes in the tree.
	 *
	 *	@param node The root of the tree. 
	 *	@return Number of nodes in the tree.
	 */
	public static int treeSize(TreeNode node) {
		if(node == null) {
			return 0;
		}
		return treeSize(node.left) + treeSize(node.right) + 1;
	}
	
	/**
	 *	Checks if the tree contains the given value.
	 *	
	 *	@param root The root of the tree.
	 *	@param value Value to search for in the tree.
	 *	@return True if the tree contains the value, False otherwise.
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if(root == null ) {
			return false;
		}
		if(root.value < value) {
			return containsValue(root.right, value);
		}
		else if(root.value > value) {
			return containsValue(root.left, value);
		}
		return true; //root.value == value
	}
	
	/**
	 * Method that prints the binary tree in inorder format(left child, parent, right child).
	 * 
	 * @param node The root of the tree.
	 */
	public static void printTree(TreeNode node) {
		if(node != null) {
			printTree(node.left);
			System.out.format("%d ", node.value);
			printTree(node.right);
		}
	}

	/**
	 * Method that prints the binary tree in reverse inorder format(right child, parent, left child).
	 * 
	 * @param node The root of the tree.
	 */
	public static void printTreeReverse(TreeNode node) {
		if(node != null) {
			printTreeReverse(node.right);
			System.out.format("%d ", node.value);
			printTreeReverse(node.left);
		}
	}	
}

