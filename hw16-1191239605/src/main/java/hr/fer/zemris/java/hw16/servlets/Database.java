package hr.fer.zemris.java.hw16.servlets;

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
 *	Database TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Database {

	private List<PicInfo> pics = new ArrayList<>();
	
	/**
	 * 	Constructs a new Database.
	 * 	TODO javadoc
	 * @throws IOException 
	 */
	public Database(String path) throws IOException {//TODO vidjet ko rjesava ovaj exception
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
	 * 	Returns the pics of the Database.
	 * 	@return the pics of the Database.
	 */
	public List<PicInfo> getPics() {
		return pics;
	}

	public List<PicInfo> getPicsByTag(String tag){
		List<PicInfo> picInfoByTag = new ArrayList<>();
		for(var info : pics) {
			if(info.getTags().contains(tag)) {
				picInfoByTag.add(info);
			}
		}
		return picInfoByTag;
	}
	
	public List<String> getAllTags(){
		Set<String> set = new HashSet<>();
		for(var pic : pics) {
			set.addAll(pic.getTags());
		}
		return set.stream().collect(Collectors.toList());
	}
	
	public PicInfo getPictureByName(String name) {
		for(var p : pics) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
}
