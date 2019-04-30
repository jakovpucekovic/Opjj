package hr.fer.zemris.java.hw06.shell.command.utils;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.commands.utils.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.utils.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.utils.NameBuilderParser;

public class NameBuilderParserTest {
	
	@Test
	public void test() {
		
		NameBuilder n = NameBuilderParser.text("");
		n = n.then(NameBuilderParser.text("then")).then(NameBuilderParser.text(" baja"));
	
		StringBuilder sb = new StringBuilder();
		n.execute(new FilterResult(Paths.get("slika1-zagreb.jpg"),"slika(\\d+)-([^.]+)\\.jpg"), sb);
	
		System.out.println(sb.toString());
	}
}
