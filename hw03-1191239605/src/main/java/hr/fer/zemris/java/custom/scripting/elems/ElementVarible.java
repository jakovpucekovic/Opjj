package hr.fer.zemris.java.custom.scripting.elems;

public class ElementVarible extends Element {
	
	private String name;

	
	public ElementVarible(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
	}
	
}
