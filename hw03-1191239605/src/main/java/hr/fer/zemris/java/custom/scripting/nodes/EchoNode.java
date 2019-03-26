package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class EchoNode extends Node {

	private Element[] elements;

	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	public Element[] getElements() {
		return elements;
	}
	
	
	
	
}
