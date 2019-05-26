package hr.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVarible;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 *	SmartScriptEngine TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack;
	private INodeVisitor visitor;
	
	/**
	 * 	Constructs a new SmartScriptEngine.
	 * 	TODO javadoc
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		
		multistack = new ObjectMultistack();
		visitor = new SmartVisitor();
	}
	
	
	public void execute() {
		documentNode.accept(visitor);
	}
	
	
	private class SmartVisitor implements INodeVisitor{

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch(IOException ex) {
				//TODO sto radim ako se dogodi IOException kod pisanja
				System.out.println(ex.getMessage());
			}
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName(); 
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper step = new ValueWrapper(node.getStepExpression().asText());
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			multistack.push(varName, start);
			while(multistack.peek(varName).numCompare(end.getValue()) <= 0) {
				for(int i = 0; i< node.numberOfChildren(); ++i) {
					node.getChild(i).accept(visitor);
				}
				
				multistack.peek(varName).add(step.getValue());
			}
			
			multistack.pop(varName);
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			//TODO u uputi pise create stack of objects
			Stack<ValueWrapper> stack = new Stack<>();
			for(var el : node.getElements()) {
				if(el instanceof ElementConstantDouble ||
				   el instanceof ElementConstantInteger ||
				   el instanceof ElementString) {
					stack.push(new ValueWrapper(el.asText()));
				}
				if(el instanceof ElementVarible) {
					if(multistack.peek(((ElementVarible) el).getName())!=null) {
						ValueWrapper var = multistack.peek(((ElementVarible) el).getName());
						stack.push(var);
					}
					//TODO throw ako nema takve varijable
				}
				if(el instanceof ElementOperator) {
					ValueWrapper a = stack.pop();
					ValueWrapper b = stack.pop();
					switch (el.asText()) {
					case "+":
						b.add(a.getValue());
						break;
					case "-":
						b.subtract(a.getValue());
						break;
					case "*":
						b.multiply(a.getValue());
						break;
					case "/":
						b.divide(a.getValue());
						break;
					default:
						//TODO throw unsuported operation
						break;
					}
					stack.push(b);
				}
				if(el instanceof ElementFunction) {
					applyFunction((ElementFunction)el, stack);
				}
			}
			
			for(var v : stack) {
				try {
					requestContext.write(v.toString());
				} catch (IOException e) {
					// TODO sto ako IOException kod write
					System.out.println(e.getMessage());
				}
			}
			
		}
		
		
		private void applyFunction(ElementFunction elFunction, Stack<ValueWrapper> stack) {
			switch (elFunction.asText()) {
			case "sin":
				double value = Double.parseDouble(stack.pop().toString());//TODO int u double? i sto ako je string?
				stack.push(new ValueWrapper(Math.sin(value)));
				return;
			case "decfmt":
				String format = (String)stack.pop().getValue();
				double number = Double.parseDouble(stack.pop().toString());//TODO sto ako nije double
				stack.push(new ValueWrapper(new DecimalFormat(format).format(number)));
				return; 
			default:
				//TODO throw unsupported function;
			}
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); ++i) {
				node.getChild(i).accept(this);
			}
		}
		
		
	}
	
	
	
}
