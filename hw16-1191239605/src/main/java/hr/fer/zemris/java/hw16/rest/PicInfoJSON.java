package hr.fer.zemris.java.hw16.rest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.servlets.DBProvider;
import hr.fer.zemris.java.hw16.servlets.PicInfo;

/**
 * Razred koristi biblioteku org.json za rad s JSON formatom
 * (da vidite kako se s time radi; mogli smo koristiti i gson).
 * 
 * @author marcupic
 */
@Path("/picInfo")
public class PicInfoJSON {
	
	
	@GET
	@Produces("application/json")
	public Response getTagList() throws IOException {
		List<String> tags = DBProvider.getDB().getAllTags();
		Gson json = new Gson();
		return Response.status(Status.OK).entity(json.toJson(tags)).build();
	}
	
	@Path("{tag}")
	@GET
	@Produces("application/json")
	public Response getThumbnailsList(@Context ServletContext context, @PathParam("tag") String tag) throws IOException {
		List<PicInfo> picsByTag = DBProvider.getDB().getPicsByTag(tag);
		
		makeThumbnails(context.getRealPath("/slike"), 
					   context.getRealPath("/thumbnails"), 
					   picsByTag);
		
		Gson json = new Gson();
		List<String> pics = picsByTag.stream().map(p->p.getName()).collect(Collectors.toList());
		return Response.status(Status.OK).entity(json.toJson(pics)).build();
	}
	
	@Path("/pic/{name}")
	@GET
	@Produces("application/json")
	public Response getPicture(@Context ServletContext context, @PathParam("name") String name) throws IOException {
		PicInfo pic = DBProvider.getDB().getPictureByName(name);
		Gson json = new Gson();
		return Response.status(Status.OK).entity(json.toJson(pic)).build();
	}

	/**
	 *	TODO javadoc 
	 */
	private void makeThumbnails(String picsPath ,String thumbnailsPath, List<PicInfo> thumbnails) throws IOException {
		java.nio.file.Path thumbnailsDir = Paths.get(thumbnailsPath);
		java.nio.file.Path picsDir = Paths.get(picsPath);
		
		//create directory for thumbnails
		if(!Files.exists(thumbnailsDir)) {
			Files.createDirectory(thumbnailsDir);
		}
		
		//create thumbnails if it doesn't exist
		for(var pic : thumbnails) {
			if(!Files.exists(thumbnailsDir.resolve(pic.getName()))) {
				BufferedImage origImg = ImageIO.read(picsDir.resolve(pic.getName()).toFile());
				BufferedImage thumbnailImg = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
				thumbnailImg.createGraphics().drawImage(origImg.getScaledInstance(150, 150, BufferedImage.SCALE_DEFAULT), 0, 0, null);
				ImageIO.write(thumbnailImg, "jpg", thumbnailsDir.resolve(pic.getName()).toFile());
			}
		}
	}
	
}