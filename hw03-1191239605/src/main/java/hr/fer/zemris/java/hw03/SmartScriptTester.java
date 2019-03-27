package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptingParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptingParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


public class SmartScriptTester {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Should give filepath.");
			System.exit(-1);
		}
		
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Invalid filepath.");
			System.exit(-1);
		}
		
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

	
	public static String createOriginalDocumentBody(DocumentNode document) {		
		return createBody(document);
	}
	
	private static String createBody(Node node) {
		if(node.getClass().equals(TextNode.class)){
			return ((TextNode)node).getText();
		} else if(node.getClass().equals(ForLoopNode.class)) {
			return createFor((ForLoopNode) node);
		} else if(node.getClass().equals(EchoNode.class)) {
			return createEcho((EchoNode) node);	
		} else {
			String returnString = "";
			for(int i = 0; i < node.numberOfChildren(); i++) {
				returnString += createBody(node.getChild(i));
			}
			return returnString;
		}
	}


	private static String createFor(ForLoopNode node) {
		StringBuilder forLoopOutput = new StringBuilder();
		forLoopOutput.append("{$FOR ");
		forLoopOutput.append(node.getVariable().asText()).append(" ");
		forLoopOutput.append(node.getStartExpression().asText()).append(" ");
		forLoopOutput.append(node.getEndExpression().asText()).append(" ");
		forLoopOutput.append(node.getStepExpression().asText()).append(" $}");
		
		for(int i = 0; i < node.numberOfChildren(); i++) {
			forLoopOutput.append(createBody(node.getChild(i)));
		}
		forLoopOutput.append("{$END$}");
		return forLoopOutput.toString();
	}


	private static String createEcho(EchoNode node) {
		StringBuilder echoOutput = new StringBuilder();
		echoOutput.append("{$=");
		Element[] elements = node.getElements();
		for(int i = 0; i < elements.length; i++) {
			if(elements[i].getClass().equals(ElementString.class)) {
				echoOutput.append("\"").append(elements[i].asText()).append("\" ");
			}else {
				echoOutput.append(elements[i].asText()).append(" ");
			}
		}
		echoOutput.append("$}");
		return echoOutput.toString();
	}
	
	
}
