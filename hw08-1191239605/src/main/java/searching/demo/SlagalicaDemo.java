package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 *	SlagalicaDemo TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SlagalicaDemo {

	public static void main(String[] args) {

		int[] el = new int[9];
		for(int i = 0; i < args[0].length(); ++i) {
			el[i] = Character.getNumericValue(args[0].charAt(i));
		}
		
		Slagalica slagalica;
		
		try {
		slagalica = new Slagalica(
				new KonfiguracijaSlagalice(el));
		} catch(IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		Node<KonfiguracijaSlagalice> rješenje =
			SearchUtil.bfsv(slagalica.initialState, slagalica.succ, slagalica.goal);
		
		if(rješenje==null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println(
			"Imam rješenje. Broj poteza je: " + rješenje.getCost()
			);
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while(trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
		
	}

}