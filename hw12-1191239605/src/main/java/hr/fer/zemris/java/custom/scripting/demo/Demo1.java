package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 *	Demo class which parses and executes osnovni.smscr.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class Demo1 {

	/**
	 * 	Main method which run the program.
	 * 	@param args None.
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("./webroot/scripts/ispit.smscr");
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<>();
		
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies, null)
		).execute();
	}

	/**
	 *  Reads the file at the given path.
	 * 	@param path Path to the file to read.
	 * 	@return Read file as {@link String}.
	 */
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
