package hr.fer.zemris.java.hw16.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *	Database which holds information about all the pictures
 *	we have available.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Database {

	/**Pictures are stored as a {@link List} of {@link PicInfo} objects.*/
	private List<PicInfo> pics = new ArrayList<>();
	
	/**
	 * 	Constructs a new {@link Database}.
	 * 	@param path Path to "opisnik.txt".
	 * 	@throws IOException If there is an error reading the file. 
	 */
	public Database(String path) throws IOException {
		Path p = Paths.get(path);
		List<String> lines = Files.readAllLines(p);
		
		for(int i = 0, s = lines.size(); i < s; i += 3) {
			if(i + 2 < s) {
				List<String> tags = Arrays.asList(lines.get(i+2).split(","));
				tags = tags.stream().map(String::trim).collect(Collectors.toList());
				pics.add(new PicInfo(lines.get(i), lines.get(i + 1), tags));
			}
		}	
	}
	
	/**
	 *	Returns all pictures from the {@link Database} which 
	 *  have the given tag.
	 *  @param tag Tag that the returned pictures must have.
	 *  @return {@link List} of {@link PicInfo} objects which represent the pictures. 
	 */
	public List<PicInfo> getPicsByTag(String tag){
		List<PicInfo> picInfoByTag = new ArrayList<>();
		for(var info : pics) {
			if(info.getTags().contains(tag)) {
				picInfoByTag.add(info);
			}
		}
		return picInfoByTag;
	}
	
	/**
	 * 	Returns all the tags that all the pictures in the {@link Database} have. Each
	 * 	tag is returned only once if it appears more than once.
	 * 	@return {@link List} of all the tags.
	 */
	public List<String> getAllTags(){
		Set<String> set = new HashSet<>();
		for(var pic : pics) {
			set.addAll(pic.getTags());
		}
		List<String> returnList = set.stream().collect(Collectors.toList());
		returnList.sort(String::compareTo);
		return returnList;
	}
	
	/**
	 *	Returns the picture with the given name.
	 *	@param name Name of the picture to return.
	 *	@return {@link PicInfo} object which represents the picture or <code>null</code>
	 *			if there is no such picture. 
	 */
	public PicInfo getPictureByName(String name) {
		for(var p : pics) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
}
