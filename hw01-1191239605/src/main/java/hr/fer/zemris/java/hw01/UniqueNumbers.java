package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 *	Program that does stuff.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0.0 
 */

public class UniqueNumbers {

	public static void main(String[] args) {
		//fali main

	}
	
	/**
	 * Class that simulates a node in the binary tree. Contains an int value and left and right children. 
	 */
	public static class TreeNode {
		int value;
		TreeNode left = null;
		TreeNode right = null;
		
	}
	/**
	 *	Adds another node in the tree and assigns it the value.
	 *	@param newValue Value that the node is assigned. 
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
	 *	@param node The root of the tree. 
	 */
	public static int treeSize(TreeNode node) {
		if(node == null) {
			return 0;
		}
		return treeSize(node.left) + treeSize(node.right) + 1;
	}
	
	//je li bolje pozvati funkciju nad cvorovima i onda provjeravati jesu li oni null ili prije poziva provjeriti jesu li null
	/**
	 *	Searches the tree for the value. Returns true if the value is found, false otherwise.
	 *	@param root The root of the tree.
	 *	@param value Value to search for in the tree. 
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if(root == null ) {
			return false;
		}
		if(root.value == value) {
			return true;
		}
		if(root.value < value) {
			return containsValue(root.right, value);
		}
		if(root.value > value) {
			return containsValue(root.left, value);
		}
		return false; //nepotrebno, moze se maknuti neki od zadnja 3 if-a
	}
	
	/**
	 * Prints the binary tree in inorder format.
	 * @param node The root of the tree.
	 */
	public static void printTree(TreeNode node) {
		if(node != null) {
			printTree(node.left);
			System.out.format("%d ", node.value);
			printTree(node.right);
		}
	}
}

