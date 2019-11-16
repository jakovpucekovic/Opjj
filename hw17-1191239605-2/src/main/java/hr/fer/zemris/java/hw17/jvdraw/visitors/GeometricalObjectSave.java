package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;

/**
 *	Implementation of {@link GeometricalObjectVisitor} which writes the {@link GeometricalObject}s
 *	that it visits into the given file.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class GeometricalObjectSave implements GeometricalObjectVisitor{

	/**Path to file.*/
	Path path;
	
	/**
	 * 	Constructs a new {@link GeometricalObjectSave}.
	 * 	@param path Path to file where the objects should be saved.
	 */
	public GeometricalObjectSave(Path path) {
		this.path = path;
		try {
			Files.writeString(path, "", StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 *	{@inheritDoc}
	 *	@throws RuntimeException if something goes wrong.
	 */
	@Override
	public void visit(Line line) {
		try {
			Files.writeString(path,
							  String.format("LINE %d %d %d %d %d %d %d\n", line.getStart().x,
									  									   line.getStart().y,
									  									   line.getEnd().x,
									  									   line.getEnd().y,
									  									   line.getColor().getRed(),
									  									   line.getColor().getGreen(),
									  									   line.getColor().getBlue()),
							  StandardOpenOption.APPEND);
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 *	{@inheritDoc}
	 *	@throws RuntimeException if something goes wrong.
	 */
	@Override
	public void visit(Circle circle) {
		try {
			Files.writeString(path,
							  String.format("CIRCLE %d %d %d %d %d %d\n", circle.getCenter().x, 
									  									  circle.getCenter().y, 
									  								 	  circle.getRadius(), 
									  									  circle.getBorderColor().getRed(), 
									  									  circle.getBorderColor().getGreen(), 
									  									  circle.getBorderColor().getBlue()), 
							  StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *	{@inheritDoc}
	 *	@throws RuntimeException if something goes wrong.
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		try {
			Files.writeString(path,
							  String.format("FCIRCLE %d %d %d %d %d %d %d %d %d\n", filledCircle.getCenter().x, 
									  										   		filledCircle.getCenter().y, 
									  										   		filledCircle.getRadius(), 
									  										   		filledCircle.getBorderColor().getRed(), 
									  										   		filledCircle.getBorderColor().getGreen(), 
									  										   		filledCircle.getBorderColor().getBlue(), 
									  										   		filledCircle.getFillColor().getRed(),
										  										   	filledCircle.getFillColor().getGreen(),
										  										   	filledCircle.getFillColor().getBlue()),
							  StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
