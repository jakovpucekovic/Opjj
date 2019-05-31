package hr.fer.zemris.java.custom.scripting.exec;

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
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 *	Class which executes a smart script previously parsed by
 * 	{@link SmartScriptParser} by using the vistor pattern.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class SmartScriptEngine {

	/**{@link DocumentNode} to execute.*/
	private DocumentNode documentNode;
	
	/**{@link RequestContext} in which to execute the {@link DocumentNode}.*/
	private RequestContext requestContext;
	
	/**{@link ObjectMultistack} used for storing values during execution.*/
	private ObjectMultistack multistack;
	
	/**{@link INodeVisitor} which does the execution.*/
	private INodeVisitor visitor;
	
	/**
	 * 	Constructs a new {@link SmartScriptEngine} which can execute
	 * 	the given {@link DocumentNode} structure in the given {@link RequestContext}.
	 * 	@param documentNode {@link DocumentNode} to execute.
	 * 	@param requestContext {@link RequestContext} in which the script should be executed.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		
		multistack = new ObjectMultistack();
		visitor = new SmartVisitor();
	}
	
	/**
	 *	Executes the smart script given in the form of nodes.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
	
	
	/**
	 * 	Private class which does the actual execution of the smart
	 * 	script implementing the visitor design pattern.
	 */
	private class SmartVisitor implements INodeVisitor{

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); ++i) {
				node.getChild(i).accept(this);
			}
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch(IOException ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName(); 
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper step  = new ValueWrapper(node.getStepExpression().asText());
			ValueWrapper end   = new ValueWrapper(node.getEndExpression().asText());
			
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
					if(multistack.peek(((ElementVarible) el).getName()) != null) {
						stack.push(multistack.peek(((ElementVarible)el).getName()).getValue());
					} else {
						throw new RuntimeException("Variable doesn't exist");
					}
				}
				if(el instanceof ElementOperator) {
					applyOperator((ElementOperator)el, stack);
				}
				if(el instanceof ElementFunction) {
					applyFunction((ElementFunction)el, stack);
				}
			}
			
			for(var v : stack) {
				try {
					requestContext.write(v.toString());
				} catch (IOException ex) {
					throw new RuntimeException(ex.getMessage());
				}
			}
		}
		
		/**
		 * 	Private method which executes all operators given in the {@link EchoNode}.
		 * 	@param elOperator {@link ElementOperator} which contains the operator.
		 * 	@param stack Stack used for storing values during execution of {@link EchoNode}. 
		 *	@throws UnsupportedOperationException If the given operator is not supported.
		 */
		private void applyOperator(ElementOperator elOperator, Stack<Object> stack) {
			//We wrap the objects from stack into ValueWrappers to perform operations on them, because
			//the stack contains Objects and they can be int, double, String or something else
			ValueWrapper a = new ValueWrapper(stack.pop());
			ValueWrapper b = new ValueWrapper(stack.pop());
			switch (elOperator.asText()) {
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
				throw new UnsupportedOperationException("Operator " + elOperator.asText() + " is not supported.");
			}
			stack.push(b.getValue());
		}
		
		/**
		 *	Private method which executes all functions given in the {@link EchoNode}.
		 *	@param elFunction {@link ElementFunction} which contains the function.
		 *	@param stack Stack used for storing values during execution of {@link EchoNode}. 
		 *	@throws UnsupportedOperationException If the given function is not supported.
		 */
		private void applyFunction(ElementFunction elFunction, Stack<Object> stack) {
			double number;
			String name, param, value;
			switch (elFunction.asText()) {
			case "sin":
				number = Double.parseDouble(new ValueWrapper(stack.pop()).getValue().toString());
				stack.push(Math.sin(Math.toRadians(number)));
				return;
			case "decfmt":
				String format = (String) stack.pop();
				number = Double.parseDouble(new ValueWrapper(stack.pop()).getValue().toString());
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
				value = stack.pop().toString();
				requestContext.setMimeType(value);
				return;
			case "paramGet":
				value = stack.pop().toString();
				name = stack.pop().toString();
				param = requestContext.getParameter(name);
				stack.push(param == null ? value : param);
				return;
			case "pparamGet":
				value = stack.pop().toString();
				name = stack.pop().toString();
				param = requestContext.getPersistentParameter(name);
				stack.push(param == null ? value : param);
				return;
			case "pparamSet":
				name = stack.pop().toString();
				value = stack.pop().toString();
				requestContext.setPersistentParameter(name, value);
				return;
			case "pparamDel":
				name = stack.pop().toString();
				requestContext.removePersistentParameter(name);
				return;
			case "tparamGet":
				value = stack.pop().toString();
				name = stack.pop().toString();
				param = requestContext.getTemporaryParameter(name);
				stack.push(param == null ? value : param);
				return;
			case "tparamSet":
				name = stack.pop().toString();
				value = stack.pop().toString();
				requestContext.setTemporaryParameter(name, value);
				return;
			case "tparamDel":
				name = stack.pop().toString();
				requestContext.removeTemporaryParameter(name);
				return;
			default:
				throw new UnsupportedOperationException("Function " + elFunction.asText() + " is not supported.");
			}
		}	
	}
	
}
