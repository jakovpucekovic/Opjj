package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {
	
	@Test
	void treeHeightEmptyTree() {
		TreeNode root = null;	
		int result = UniqueNumbers.treeSize(root);
		assertEquals(0, result);
	}
	
	@Test
	void treeHeight() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 8);
		root = UniqueNumbers.addNode(root, 7);
		root = UniqueNumbers.addNode(root, 6);
		root = UniqueNumbers.addNode(root, 5);
		root = UniqueNumbers.addNode(root, 4);
		root = UniqueNumbers.addNode(root, 3);
		
		int result = UniqueNumbers.treeSize(root);
		assertEquals(6, result);
	}
	
	@Test
	void containsValueEmpty() {
		TreeNode root = null;
		assertFalse(UniqueNumbers.containsValue(root, 7));
	}

	@Test
	void containsValueInRoot() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, -43);
		root = UniqueNumbers.addNode(root, -57);
		root = UniqueNumbers.addNode(root, 84);
		
		assertTrue(UniqueNumbers.containsValue(root, -43));
		assertFalse(UniqueNumbers.containsValue(root, 7));
	}
	
	@Test
	void containsValueInChildren() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 5);
		root = UniqueNumbers.addNode(root, 7);
		root = UniqueNumbers.addNode(root, 2);
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 3);
		root = UniqueNumbers.addNode(root, 6);
		root = UniqueNumbers.addNode(root, 8);
		
		assertTrue(UniqueNumbers.containsValue(root, 8));
		assertFalse(UniqueNumbers.containsValue(root, 10));
	}
	
	@Test
	void addNodeAddRoot(){
		TreeNode root = UniqueNumbers.addNode(null, 7);
		
		assertNotNull(root);
		assertEquals(7, root.value);
		assertEquals(root, UniqueNumbers.addNode(root, 8));
	}
	
	@Test
	void addNodeFullTree(){
		TreeNode root = UniqueNumbers.addNode(null, 5);
		root = UniqueNumbers.addNode(root, 7);
		root = UniqueNumbers.addNode(root, 2);
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 3);
		root = UniqueNumbers.addNode(root, 6);
		root = UniqueNumbers.addNode(root, 4);
		
		assertEquals(5, root.value);
		assertEquals(2, root.left.value);
		assertEquals(1, root.left.left.value);
		assertEquals(3, root.left.right.value);
		assertEquals(4, root.left.right.right.value);
		assertEquals(7, root.right.value);
		assertEquals(6, root.right.left.value);
	}
}
