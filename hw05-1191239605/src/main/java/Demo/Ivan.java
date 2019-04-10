package Demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Ivan {

	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(
				Paths.get("src/main/resources/examples/kochCurve.txt"),
				StandardCharsets.UTF_8); 
		} catch (IOException e) {
			System.out.println("Can't read file");
			System.exit(-1);
		}
		
		String[] data = new String[lines.size()];
		for(int i = 0; i < lines.size(); ++i) {
			data[i] = lines.get(i);
		}
		
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
	
	public static void main(String[] args) {

		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

		
	}
	
}
