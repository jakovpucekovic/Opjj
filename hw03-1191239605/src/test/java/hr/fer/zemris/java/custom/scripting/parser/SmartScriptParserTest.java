package hr.fer.zemris.java.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.hw03.SmartScriptTester;

public class SmartScriptParserTest {

	
	
	@Test
	public void testWrongNumberOfEndTags() {
		String docBody1 = loader("endtag-toParse1.txt");	
		String docBody2 = loader("endtag-toParse2.txt");	
		String docBody3 = loader("endtag-toParse3.txt");	
		
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(docBody1));
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(docBody2));
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(docBody3));
		
	}
	
	@Test
	public void testValidForTags() {
		String docBody = loader("for-toParse.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		assertEquals(loader("for-result.txt"), originalDocumentBody);
	}
	
	
	@Test
	public void testValidVariableNames() {
		String docBody = loader("variable-toParse.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		assertEquals(loader("variable-result.txt"), originalDocumentBody);
	}
	
	
	
	@Test
	public void testGivenExample() {
		String docBody = loader("test1.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);
		
		
		assertEquals(originalDocumentBody, originalDocumentBody2);
	}
	
	
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is =
				this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
				return null;
		}
	}
}
