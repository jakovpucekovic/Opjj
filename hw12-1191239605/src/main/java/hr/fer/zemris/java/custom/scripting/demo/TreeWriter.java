package hr.fer.zemris.java.custom.scripting.demo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 *	TreeWriter TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class TreeWriter {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Expected only 1 argument.");
			return;
		}
		
		Path path = Paths.get(args[0]);
		if(!Files.exists(path)) {
			System.out.println("Given file doesn't exist.");
			return;
		}
		
		try {
			String docBody = Files.readString(path);
			SmartScriptParser p = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			p.getDocumentNode().accept(visitor);
		} catch(SmartScriptParserException ex){
			System.out.println("Document cannot be parsed.");
			return;
		} catch(IOException ex) {
			System.out.println("Error reading file.");
			return;
		}
		
	}

	private static class WriterVisitor implements INodeVisitor{

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node);
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");
			
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node);
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}			
		}
		
	}
}
