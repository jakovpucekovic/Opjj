package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.zemris.java.custom.scripting.exec.SmartScriptEngine;

/**
 *	Demo3 TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Demo3 {

	public static void main(String[] args) {
		String documentBody = readFromDisk("./webroot/scripts/brojPoziva.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
		cookies);
		
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();//TODO fali novi red nakon 3
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}

	
	private static String readFromDisk(String path) {
		Path p = Paths.get(path);
		if(!Files.exists(p)) {
			throw new IllegalArgumentException("File doesn't exist.");
		}
		String doc;
		try {
			doc = Files.readString(p);
		} catch (IOException ex) {
			throw new IllegalArgumentException("Error while reading");
		}
		
		
		return doc;
	}
	
}
