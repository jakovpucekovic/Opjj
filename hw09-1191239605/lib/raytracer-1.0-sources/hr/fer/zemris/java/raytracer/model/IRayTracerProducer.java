package hr.fer.zemris.java.raytracer.model;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Interface that specifies objects which are capable to create scene snapshots by
 * using ray-tracing technique.
 * 
 * @author marcupic
 *
 */
public interface IRayTracerProducer {

	/**
	 * Method which is called by GUI when a scene snapshot is required. Since not many of you
	 * are familiar with ray-tracing, please read homework text carefully for further explanation
	 * since the description of ray-tracing technique can not be included in this methods' comment.
	 * 
	 * @param eye position of human observer
	 * @param view position that is observed
	 * @param viewUp specification of view-up vector which is used to determine y-axis for screen
	 * @param horizontal horizontal width of observed space
	 * @param vertical vertical height of observed space
	 * @param width number of pixels per screen row
	 * @param height number of pixel per screen column
	 * @param requestNo used internally and must be passed on to GUI observer with rendered image
	 * @param observer GUI observer that will accept and display image this producer creates
	 * @param cancel object used to cancel rendering of image; is set to <code>true</code>, rendering should be canceled
	 */
	public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel);
	
}
