package hr.fer.zemris.java.custom.scripting.parser;

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
		String docBody1 = loader("endtag1.txt");	
		String docBody2 = loader("endtag2.txt");	
		String docBody3 = loader("endtag3.txt");	
		
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(docBody1));
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(docBody2));
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(docBody3));
		
	}
	
	@Test
	public void testValidForTags() {
		String docBody = loader("for.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);
		
		assertEquals(originalDocumentBody, originalDocumentBody2);
	}
	
	@Test
	public void testWeirdForTag() {
		String docBody = loader("weirdFor.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		assertEquals("{$FOR i -1.35 bbb \"1\" $} {$END$}", originalDocumentBody);
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
	public void testEscapingInString() {
		SmartScriptParser parser = new SmartScriptParser("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		assertEquals("A tag follows {$=\"Joe \\\"Long\\\" Smith\" $}.", originalDocumentBody);
	}
	
	@Test
	public void testEscapingInString2() {
		SmartScriptParser parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		
		assertEquals("Example \\{$=1$}. Now actually write one {$=1 $}", originalDocumentBody);
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
