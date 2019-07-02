package hr.fer.zemris.java.hw16.db;

import java.util.ArrayList;
import java.util.List;

/**
 *	Java-bean class which holds information about the
 *	pictures.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class PicInfo {

	/**Name of the picture.*/
	private String name;

	/**Description of the picture.*/
	private String description;
	
	/**Tags describing the picture.*/
	private List<String> tags;
	
	/**
	 * 	Constructs a new {@link PicInfo}.
	 */
	public PicInfo() {
		tags = new ArrayList<>();
	}
	
	/**
	 * 	Constructs a new {@link PicInfo}.
	 * 	@param name Name of the picture.
	 * 	@param description Description of the picture.
	 * 	@param tags {@link List} of tags describing the picture.
	 */
	public PicInfo(String name, String description, List<String> tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}
	
	/**
	 * 	Returns the name of the {@link PicInfo}.
	 * 	@return the name of the {@link PicInfo}.
	 */
	public String getName() {
		return name;
	}
	/**
	 * 	Sets the name of the {@link PicInfo}.
	 * 	@param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 	Returns the description of the {@link PicInfo}.
	 * 	@return the description of the {@link PicInfo}.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 	Sets the description of the {@link PicInfo}.
	 * 	@param description the description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 	Returns the tags of the {@link PicInfo}.
	 * 	@return the tags of the {@link PicInfo}.
	 */
	public List<String> getTags() {
		return tags;
	}
	/**
	 * 	Sets the tags of the {@link PicInfo}.
	 * 	@param tags the tags to set.
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		return "PicInfo:" + name;
	}
	
}
