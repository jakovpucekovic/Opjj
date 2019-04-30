package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 *	Coloring TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Coloring {

	private Pixel reference;
	private Picture picture;
	private int fillColor;
	private int refColor;
	
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}
	
	public Consumer<Pixel> process = new Consumer<>() {

		@Override
		public void accept(Pixel t) {
			picture.setPixelColor(t.x, t.y, fillColor);
		}
	};
	
	public Function<Pixel, List<Pixel>> succ = new Function<>() {

		@Override
		public List<Pixel> apply(Pixel t) {
			List<Pixel> list = new ArrayList<>();
			if(t.x > 0) {
				list.add(new Pixel(t.x - 1, t.y));
			}
			if(t.y > 0) {
				list.add(new Pixel(t.x, t.y - 1));
			}
			if(t.x < picture.getWidth() - 1) {
				list.add(new Pixel(t.x + 1, t.y));
			}
			if(t.y < picture.getHeight() - 1) {
				list.add(new Pixel(t.x, t.y + 1));
			}
			return list;
		}
	};
	
	public Predicate<Pixel> acceptable = new Predicate<>() {

		@Override
		public boolean test(Pixel t) {//TODO jel treba provjeravati jel susjed
			if(picture.getPixelColor(t.x, t.y) == refColor) {
				return true;
			}
			return false;
		}
	};
	
	public Supplier<Pixel> initialState = new Supplier<>() {

		@Override
		public Pixel get() {
			return reference;
		}
	};
	
}
