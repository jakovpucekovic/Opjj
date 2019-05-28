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
			Stack<Object> stack = new Stack<>();
			for(var el : node.getElements()) {
				if(el instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) el).getValue());
				}
				if(el instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) el).getValue());
				}
				if(el instanceof ElementString) {
					stack.push(((ElementString) el).getValue());
				}
				if(el instanceof ElementVarible) {
					if(multistack.peek(((ElementVarible) el).getName())!=null) {
						stack.push(multistack.peek(((ElementVarible)el).getName()).getValue());
					}
					//TODO throw ako nema takve varijable
				}
				if(el instanceof ElementOperator) {
					ValueWrapper a = new ValueWrapper(stack.pop());
					ValueWrapper b = new ValueWrapper(stack.pop());
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
					stack.push(b.getValue());
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
		
		
		private void applyFunction(ElementFunction elFunction, Stack<Object> stack) {
			switch (elFunction.asText()) {
			case "sin":
				double value = Double.parseDouble(new ValueWrapper(stack.pop()).getValue().toString());
				stack.push(Math.sin(Math.toRadians(value)));
				return;
			case "decfmt":
				String format = (String) stack.pop();
				double number = Double.parseDouble(new ValueWrapper(stack.pop()).getValue().toString());
				stack.push(new DecimalFormat(format).format(number));
				return; 
			case "dup":
				Object top = stack.pop();
				stack.push(top);
				stack.push(top);
				return;
			case "swap":
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
				return;
			case "setMimeType":
				String str = stack.pop().toString();
				requestContext.setMimeType(str);
				return;
			case "paramGet":
				String defaultValue1 = stack.pop().toString();
				String name1 = stack.pop().toString();
				String param1 = requestContext.getParameter(name1);
				stack.push(param1 == null ? defaultValue1 : param1);
				return;
			case "pparamGet":
				String defaultValue2 = stack.pop().toString();
				String name2 = stack.pop().toString();
				String param2 = requestContext.getPersistentParameter(name2);
				stack.push(param2 == null ? defaultValue2 : param2);
				return;
			case "pparamSet":
				String name3 = stack.pop().toString();
				String value3 = stack.pop().toString();
				requestContext.setPersistentParameter(name3, value3);
				return;
			case "pparamDel":
				String name4 = stack.pop().toString();
				requestContext.removePersistentParameter(name4);
				return;
			case "tparamGet":
				String defaultValue5 = stack.pop().toString();
				String name5 = stack.pop().toString();
				String param5 = requestContext.getTemporaryParameter(name5);
				stack.push(param5 == null ? defaultValue5 : param5);
				return;
			case "tparamSet":
				String name6 = stack.pop().toString();
				String value6 = stack.pop().toString();
				requestContext.setPersistentParameter(name6, value6);
				return;
			case "tparamDel":
				String name7 = stack.pop().toString();
				requestContext.removePersistentParameter(name7);
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
