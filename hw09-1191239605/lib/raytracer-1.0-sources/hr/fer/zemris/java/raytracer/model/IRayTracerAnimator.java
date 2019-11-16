package hr.fer.zemris.java.raytracer.model;

/**
 * <p>Interface which describes animation support for out raytracer.
 * Objects which are of type IRayTracerAnimator will be informed
 * of the time progress, and for current time can be asked
 * where the observer is, where he look at, where is "up".</p>
 * 
 * <p>Using method {@link #getTargetTimeFrameDuration()} object can
 * inform GUI how often the scene should be rendered (i.e. each 2000
 * milliseconds). The GUI will its best to satisfy this, so that, if
 * target timeframe duration is 2000 ms, and rendering takes 700 ms, 
 * new repaint will be scheduled after 1300 ms. Please note that if
 * rendering takes more than target timeframe duration, the best that 
 * GUI can do is call repaint immediately upon previous repaint results
 * are obtained.</p>
 * 
 * @author marcupic
 *
 */
public interface IRayTracerAnimator {
	/**
	 * Used to inform the object of the amount of elapsed time.
	 * The object can use this to calculate new time for which
	 * calls to {@link #getEye()}, {@link #getView()} and {@link #getViewUp()}
	 * should return values.
	 * 
	 * @param deltaTime amount of elapsed time (in milliseconds)
	 */
	void update(long deltaTime);
	/**
	 * Returns the amount of time between two repaints.
	 * 
	 * @return duration of one timeframe (in milliseconds)
	 */
	long getTargetTimeFrameDuration();
	/**
	 * Where is user positioned at current time?
	 * @return user position
	 */
	Point3D getEye();
	/**
	 * Where does user look at, at current time?
	 * @return look-at position
	 */
	Point3D getView();
	/**
	 * Where is "up"-direction for user at current time?
	 * @return "up"-direction
	 */
	Point3D getViewUp();
}
