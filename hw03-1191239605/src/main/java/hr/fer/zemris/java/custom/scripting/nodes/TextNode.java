package hr.fer.zemris.java.custom.scripting.nodes;

/**
 *	Class which represents a piece of textual data.
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class TextNode extends Node {

	/**
	 *	Textual data is stored here.
	 */
	private String text;

	/**
	 *	Constructs a new {@link TextNode} and stores
	 *	the given text.
	 *	@param text The text to store.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 *	Returns the stored text.
	 *	@return The stored text.
	 */
	public String getText() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}
	
	
	
	
	
	
	
}
