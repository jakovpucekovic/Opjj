package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.math.Vector2D;

//TODO provjeravati da je vece od 0
public class LSystemBuilderImpl implements LSystemBuilder{
	
	private Dictionary<Character, String> productions;
	private Dictionary<Character, String> commands;
	
	private String axiom = "";
	private double angle = 0;
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);

	private class LSystemImpl implements LSystem{

		private Context ctx;
		
		@Override
		public void draw(int arg0, Painter arg1) {
			ctx = new Context();
			ctx.pushState(new TurtleState());
			String generated = generate(arg0);
			//TODO za svaki znak u generated se izvrsi command
		}

		@Override
		public String generate(int arg0) {
			String generated = axiom;
			StringBuilder gen = new StringBuilder();
			for(int i = 0; i < arg0; i++) {
				for(int j = 0; j < generated.length(); j++) {
					gen.append(productions.get(generated.charAt(j)));
				}
				generated = gen.toString();
			}
			return generated;
		}
		
	}
	
	@Override
	public LSystem build() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		commands.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	//TODO stupnjevi ili radijani
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
