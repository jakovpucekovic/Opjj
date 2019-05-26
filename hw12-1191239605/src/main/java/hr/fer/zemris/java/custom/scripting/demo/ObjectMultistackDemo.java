package hr.fer.zemris.java.custom.scripting.demo;

import hr.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.zemris.java.custom.scripting.exec.ValueWrapper;

/**
 *	Class which contains an example of using {@link ObjectMultistack}
 *	from the homework.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class ObjectMultistackDemo {

	public static void main(String[] args) {
		
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		System.out.println("Current value for price: "
						+ multistack.peek("price").getValue());
		
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		
		multistack.peek("year").setValue(
						((Integer)multistack.peek("year").getValue()).intValue() + 50
		);
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		
		multistack.pop("year");
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		
		multistack.peek("year").add("5");
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		
		multistack.peek("year").add(5);
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		
		multistack.peek("year").add(5.0);
		System.out.println("Current value for year: "
						+ multistack.peek("year").getValue());
		
	}
// 	Expected output:
	
//	year: 2000
//	price: 200.51
//	year: 1900
//	year: 1950
//	year: 2000
//	year: 2005
//	year: 2010
//	year: 2015.0
	
}