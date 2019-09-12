package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 *	FilledTriangleTool TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class FilledTriangleTool implements Tool{

	private Point a;
	private Point b;
	private Point c;
	
	private IColorProvider fillColorP;
	private IColorProvider borderColorP;
	
	
	private int numberOfClicks = 0;
	
	private FilledTriangle filledTriangle = null;
	private DrawingModel model;
	

	public FilledTriangleTool(IColorProvider fillColorP, IColorProvider borderColorP, DrawingModel model) {
		this.fillColorP = fillColorP;
		this.borderColorP = borderColorP;
		this.model = model;
	}


	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		numberOfClicks++;
		model.remove(filledTriangle);

		if(numberOfClicks == 1) {
//			model.remove(filledTriangle);
			a = new Point(e.getX(), e.getY());
			filledTriangle = new FilledTriangle(a, null, null, borderColorP.getCurrentColor(), fillColorP.getCurrentColor());
			model.add(filledTriangle);
			
		} else if(numberOfClicks == 2) {
//			model.remove(filledTriangle);
			b = new Point(e.getX(), e.getY());
			filledTriangle = new FilledTriangle(a, b, null, borderColorP.getCurrentColor(), fillColorP.getCurrentColor());
			model.add(filledTriangle);
			
		} else if(numberOfClicks == 3) {
			c = new Point(e.getX(), e.getY());
			filledTriangle = new FilledTriangle(a, b, c, borderColorP.getCurrentColor(), fillColorP.getCurrentColor());
			model.add(filledTriangle);
		
			numberOfClicks = 0;
			a = null;
			b = null;
			c = null;
			filledTriangle = null;
		}
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		model.remove(filledTriangle);
		
		if(numberOfClicks == 1) {
			filledTriangle = new FilledTriangle(a, e.getPoint(), null, borderColorP.getCurrentColor(), fillColorP.getCurrentColor());
			model.add(filledTriangle);
		} else if(numberOfClicks == 2) {
			filledTriangle = new FilledTriangle(a, b, e.getPoint(), borderColorP.getCurrentColor(), fillColorP.getCurrentColor());			
			model.add(filledTriangle);
		}
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		if(filledTriangle != null) {
			filledTriangle.accept(new GeometricalObjectPainter(g2d));
		}		
	}

	
	
}
