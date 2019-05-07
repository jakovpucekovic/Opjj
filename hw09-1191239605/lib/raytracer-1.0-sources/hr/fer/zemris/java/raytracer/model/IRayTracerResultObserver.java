package hr.fer.zemris.java.raytracer.model;

/**
 * This interface specifies the GUI observer which can display produced image on screen.
 * 
 * @author marcupic
 *
 */
public interface IRayTracerResultObserver {
	
	/**
	 * This method is used to notify GUI than image data is ready and can be rendered on screen.
	 * Arrays <code>red</code>, <code>green</code> and <code>blue</code> are serialized 2D array
	 * of screen and should contain <code>width*height</code> elements each. Legal values for
	 * each element are integers from 0 to 255 (inclusive). Color for pixel <code>(x,y)</code> 
	 * should be stored in position <code>y*width+x</code>.
	 *  
	 * @param red red components of pixels color
	 * @param green green components of pixels color
	 * @param blue blue components of pixels color
	 * @param requestVersion parameter used to associate this result with previously GUI-made request
	 */
	public void acceptResult(short[] red, short[] green, short[] blue, long requestVersion);
	
}
