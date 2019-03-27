package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptingParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptingParserException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


public class SmartScriptTester {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Should give filepath.");
			System.exit(-1);
		}
		
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		
		SmartScriptingParser parser = null;
		
		try {
			parser = new SmartScriptingParser(docBody);
		} catch(SmartScriptingParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch( Exception e) {
			System.out.println("U failed");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		
	}

}
