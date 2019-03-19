package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Opis inputa");
			return;
		}
		
		ObjectStack stack = new ObjectStack();

		String[] str = args[0].split(" "); //pametniji nacin od ovoga, preko regexa mybe?
		for(int i = 0; i < str.length; i++) {
			try {
				int n = Integer.parseInt(str[i]);
				stack.push(n);
			} catch(NumberFormatException ex) {
				switch(str[i]) {
					case ("+"): { 
						stack.push((int)stack.pop() + (int)stack.pop());
						break;
					}
					case("-"): {
						stack.push((int)stack.pop() - (int)stack.pop());
						break;
					}
					case("*"): {
						stack.push((int)stack.pop() * (int)stack.pop());
						break;
					}
					case("/"): {
						int t = (int)stack.pop();
						if(t == 0) {
							System.out.println("Division by zero has occured.");
							return;
						}
						stack.push((int)stack.pop() / t );
						break;
					}
					case("%"):{
						int t = (int)stack.pop();
						if(t == 0) {
							System.out.println("Division by zero has occured.");
							return;
						}
						stack.push((int)stack.pop() % t);
						break;
					}
					default:{ //nije racunska operacija ni broj
						System.out.println("Wrong input.");
					}
				}
			}
		}
		if(stack.size() != 1) {
			System.out.println("An error has occured has occured.");
		} else {
			System.out.format("The expression evaluates to %d.%n", stack.peek());
		}
		
	}
}
