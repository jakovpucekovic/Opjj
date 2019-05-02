package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.commands.utils.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.utils.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.utils.NameBuilderParser;

public class NameBuilderParserTest {

	
	@Test
	public void testGivenExample() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,03}.jpg");
		NameBuilder builder = parser.getNameBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		builder.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg"), sb);
		assertEquals("gradovi-zagreb-001.jpg", sb.toString());
	}
	
	@Test
	public void testEmptyExpression() {
		NameBuilderParser parser = new NameBuilderParser("");
		NameBuilder builder = parser.getNameBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		builder.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg"), sb);
		assertEquals("", sb.toString());
	}
	
	@Test
	public void testEmptyPadding() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${2}-${1,3}.jpg");
		NameBuilder builder = parser.getNameBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		builder.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg"), sb);
		assertEquals("gradovi-zagreb-  1.jpg", sb.toString());
	}
	
	@Test
	public void testSpacesInGroups() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${    2  }-${ 1   ,  3    }.jpg");
		NameBuilder builder = parser.getNameBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		builder.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg"), sb);
		assertEquals("gradovi-zagreb-  1.jpg", sb.toString());
	}
	
	@Test
	public void testZeroLength() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${1,0}.jpg");
		NameBuilder builder = parser.getNameBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		builder.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg"), sb);
		assertEquals("gradovi-1.jpg", sb.toString());
	}
	
	@Test
	public void testZeroLengthZeroPadding() {
		NameBuilderParser parser = new NameBuilderParser("gradovi-${1,00}.jpg");
		NameBuilder builder = parser.getNameBuilder();
		
		StringBuilder sb = new StringBuilder();
		
		builder.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg"), sb);
		assertEquals("gradovi-1.jpg", sb.toString());
	}
	
	@Test
	public void testDoubleGroup() {
		assertThrows(IllegalArgumentException.class, ()->  new NameBuilderParser("${${2}}"));
	}
	
	@Test
	public void testGroupNotClosed() {		
		assertThrows(IllegalArgumentException.class, ()-> new NameBuilderParser("${1,2"));
	}
	
}
