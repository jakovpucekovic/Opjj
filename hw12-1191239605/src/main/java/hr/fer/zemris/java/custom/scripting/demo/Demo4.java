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
 *	Demo4 TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Demo4 {
	public static void main(String[] args) {
		String documentBody = readFromDisk("./webroot/scripts/fibonacci.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<>();
		
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
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