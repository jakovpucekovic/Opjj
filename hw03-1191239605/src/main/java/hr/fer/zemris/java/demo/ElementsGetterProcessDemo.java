package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterProcessDemo {

	public static void main(String[] args) {
//		Collection col = new ArrayIndexedCollection();
		Collection col = new LinkedListIndexedCollection();
		
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		
		getter.processRemaining(System.out::println);
	}
}
//Ana, Jasna