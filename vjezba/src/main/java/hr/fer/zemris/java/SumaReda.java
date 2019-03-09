package hr.fer.zemris.java.primjeri;

/**
	Paket sadrzava primjere iz knjige prof cupica koje rjesavam sebi za vjezbu.

	@author Jakov Pucekovic
	@version 1.0.0
*/

import java.lang.Math.*;

class Primjeri{

	/**
		Suma reda racuna e na x za vrijednost x koju korisnik unese.
		@param input x za koji racunamo e na x
		@exception NumberFormatException za unos u krivom formatu
		@exception NullPointerException za prazan unos
	*/
	public static double SumaReda(String input){

		double x;
		try{ 
			x=Double.parseDouble(input);
		} catch(NumberFormatException|NullPointerException ex){
			return -1.0;
		  }

		double suma = 1.0;
		double poten = 1.0;
		double fakt = 1.0;

		for(int i = 1; i < 10; i++){
			poten = poten * x;
			fakt = fakt * i;
			suma += poten/fakt;
		}

		return suma;
	}


}