package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterDemo5 {

	public static void main(String[] args) {
//		Collection col1 = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
//		Collection col2 = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		
		Collection col1 = new LinkedListIndexedCollection(); 
		Collection col2 = new LinkedListIndexedCollection(); 

		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		
		System.out.println("Ima nepredanih elemenata: " + getter1.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter2.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter3.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter3.getNextElement());
	}
}
//Ivo, Ana, Ivo, Jasmina, Štefanija