package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 *	
 *	Tester class for the {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 *	Run the main method and give the path to the file you want parsed.
 *
 * 	@author Jakov Pucekovic
 *	@since 1.0
 */
public class SmartScriptTester {

	/**
	 *	Method which tests if the {@link SmartScriptParser} works as intended.
	 *	@param args Path to the file which should be parsed. 
	 */
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
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
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

	/**
	 *	Restores the original document from the given {@link DocumentNode}.
	 *	@param document {@link DocumentNode} from which the original document should be restored.
	 *	@return Restored document. 
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {		
		return createBody(document);
	}
	
	/**
	 *	Actual method which restores the document from the given node.
	 *	@param node Node from which document body should be restored.
	 *	@return Restored document body.
	 */
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

	/**
	 *	Method which restores the for tag from the given {@link ForLoopNode}.
	 *	@param node {@link ForLoopNode} from which document body should be restored.
	 *	@return Restored document body.
	 */
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

	/**
	 *	Method which restores the echo tag from the given {@link EchoNode}.
	 *	@param node {@link EchoNode} from which document body should be restored.
	 *	@return Restored document body.
	 */
	private static String createEcho(EchoNode node) {
		StringBuilder echoOutput = new StringBuilder();
		echoOutput.append("{$=");
		Element[] elements = node.getElements();
		for(int i = 0; i < elements.length; i++) {
			echoOutput.append(elements[i].asText()).append(" ");
		}
		echoOutput.append("$}");
		return echoOutput.toString();
	}
	
	
}
