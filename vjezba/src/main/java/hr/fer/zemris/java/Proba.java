package hr.fer.zemris.java.proba;

/**
	Program koji radi nesto.

	@author Jakov Pucekovic
	@version 1.0.0
*/

public class Proba{

	/**
		Main koji radi nesto.

		@param args argumenti komandne linije koje program prima.
	*/

	public static void main(String ... args){

		if(args.length <= 0){
			System.out.print("Nisu predani argumenti komandne linije\n");
			return;
		}

		for(var x : args){
			System.out.println(x);
		}

	}

}