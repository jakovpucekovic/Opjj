package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.commands.utils.FilterResult;

public class MassrrenameShellCommandTest {

	
	@Test
	public void test() {
		
		FilterResult fr = new FilterResult(Paths.get("dir1/slika1-zagreb.jpg"), "slika(\\d+)-([^.]+)\\.jpg");
		
		System.out.println(fr.toString());
		System.out.println(fr.numberOfGroups());
		System.out.println(fr.group(0));
		System.out.println(fr.group(1));
		System.out.println(fr.group(2));
	}
}
