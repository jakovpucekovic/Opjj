package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 *	Class which provides the methods for coloring a {@link Picture}.	
 *
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Coloring {

	/**The starting {@link Pixel}.*/
	private Pixel reference;

	/**The {@link Picture} which should be colored.*/
	private Picture picture;
	
	/**New color.*/
	private int fillColor;
	
	/**The color of the starting {@link Pixel}.*/
	private int refColor;
	
	/**
	 *	Constructs a new {@link Coloring} with the given parameters.
	 *	@param reference The starting pixel.
	 *	@param picture The picture to color.
	 *	@param fillColor The new color.
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}
	
	/**
	 *  {@link Consumer} which changes the color of the given {@link Pixel}
	 *  to the fill color.
	 */
	public Consumer<Pixel> process = (Pixel p) -> picture.setPixelColor(p.x, p.y, fillColor);
	
	/**
	 * 	{@link Function} which returns a {@link List} of {@link Pixel}s
	 * 	which are the neighbors the of the given {@link Pixel}.
	 */
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
	
	/**
	 * 	{@link Predicate} which decides whether the given {@link Pixel}
	 * 	is acceptable which is determined by the color of the {@link Pixel}.
	 */
	public Predicate<Pixel> acceptable = (Pixel p) -> picture.getPixelColor(p.x, p.y) == refColor;
	
	/**
	 *  {@link Supplier} which return the initial state of this {@link Coloring}.
	 */
	public Supplier<Pixel> initialState = () -> {return reference;};
}
