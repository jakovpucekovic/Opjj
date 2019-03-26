package hr.fer.zemris.java.custom.scripting.elems;

public class ElementString extends Element {

	private String value;

	
	public ElementString(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return value;
	}
	
}
