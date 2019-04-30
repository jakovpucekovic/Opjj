package searching.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

/**
 *	SlagalicaDemo TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SlagalicaDemo {

	public static void main(String[] args) {

		Slagalica slagalica = new Slagalica(
//				new KonfiguracijaSlagalice(new int[] {1,2,3,4,5,6,7,0,8})
				new KonfiguracijaSlagalice(new int[] {2,3,0,1,4,6,7,5,8})
		);
		Node<KonfiguracijaSlagalice> rješenje =
			SearchUtil.bfs(slagalica.initialState, slagalica.succ, slagalica.pred);
		
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
		}
		
	}

}