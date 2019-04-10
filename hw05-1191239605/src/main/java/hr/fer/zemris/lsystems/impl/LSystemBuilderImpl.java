package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 *	Class which implements {@link LSystemBuilder}.
 *	
 *	@author Jakov Pucekovic
 *	@version 1.0 
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	
	/**Dictionary which holds production rules.*/
	private Dictionary<Character, String> productions = new Dictionary<>();
	/**Dictionary which holds commands.*/
	private Dictionary<Character, Command> commands = new Dictionary<>();;
	
	/**Starting sequence.*/
	private String axiom = "";
//TODO javadoc
	private double angle = 0;
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 *	Class which implements {@link LSystem}.
	 */
	public class LSystemImpl implements LSystem{
 
		/**Context in which we're working.*/
		private Context ctx;
		@Override
		public void draw(int arg0, Painter arg1) {
			ctx = new Context();
			ctx.pushState(new TurtleState(origin, new Vector2D(1, 0).rotated(angle), Color.black, unitLength));
			String generated = generate(arg0);
			for(int i = 0; i < generated.length(); i++) {
				Command toExcecute = commands.get(generated.charAt(i));
				if(toExcecute != null) {
					toExcecute.execute(ctx, arg1);
				}
			}
		}
		
		/**
		 *	Generates a new sequence from the starting sequence
		 *	by applying production rules given amount of times.
		 *	@param arg0 The number of times production rules should be applied.
		 *	@return The generated sequence.
		 */
		@Override
		public String generate(int arg0) {
			String generated = axiom;
			StringBuilder gen = new StringBuilder();
			for(int i = 0; i < arg0; i++) {
				for(int j = 0; j < generated.length(); j++) {
					String production = productions.get(generated.charAt(j));
					if(production != null) {
						gen.append(production);
					} else {
						gen.append(generated.charAt(j));
					}
				}
				generated = gen.toString();
				gen.delete(0, gen.length());
			}
			return generated;
		}
		
	}
	
	//TODO javadoc
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 *	 TODO javadoc
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		
		for(var i : arg0) {
			if(i.isBlank()) {
				continue;
			}
			String[] split = i.split("\\s");
			if(configureAngle(split)) {
				continue;
			} else if(configureAxiom(split)) {
				continue;
			} else if(configureOrigin(split)) {
				continue;
			} else if(configureProduction(split)) {
				continue;
			} else if(configureUnitLength(split)) {
				continue;
			} else if(configureUnitLengthDegreeScaler(split)) {
				continue;
			} else if(configureCommand(split)) {
				continue;
			} else {
				throw new IllegalArgumentException("Unknown configuration.");
			}
		}
		return this;
	}
	
	
	/**
	 *	Checks whether the given data describes a command and adds it if yes.
	 *	@param data The data to check.
	 *	@return <code>true</code> if yes, <code>false</code> otherwise.
	 *	@throws IllegalArgumentException If the given data describes a 
	 *									 a command which has more that 1 character
	 *									 or if a command which uses this character
	 *									 already exists.
	 */
	private boolean configureCommand(String[] data) {
		if(data.length != 3 || data.length != 4 || !data[0].equals("command")) {
			return false;
		}
		if(data[1].length() != 1) {
			throw new IllegalArgumentException("Command doesn't have 1 character.");
		}
		if(commands.get(data[1].charAt(0)) != null) {
			throw new IllegalArgumentException("Cannot have 2 commands which use the same character.");
		}
		commands.put(data[1].charAt(0), makeCommand(Arrays.copyOfRange(data, 2, data.length)));
		return true;
	}
	
	/**
	 *	Checks whether the given data describes a configuration 
	 *	of unitLengthDegreeScaler and configures it if yes.
	 *	@param data The data to check.
	 *	@return <code>true</code> if yes, <code>false</code> otherwise.
	 *	@throws IllegalArgumentException If the given data describes a 
	 *									 a configuration of unitLengthDegreeScalar
	 *									 which is invalid, e.g. length <= 0.
	 */
	private boolean configureUnitLengthDegreeScaler(String[] data) {
		if(data.length < 2 || data.length > 4 || !data[0].equals("unitLengthDegreeScaler")) {
			return false;
		}
		double newLength;
		if(data.length == 2) {
			/*Only 1 number, no "/".*/
			if(data[1].indexOf('/') == -1) {
				try {
					newLength = Double.parseDouble(data[1]);
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");	//TODO mogu staviti newLength=-1 pa se na kraju baca exeception					
				}			
			} else {
			/*2 numbers separated with "/" without spaces*/
				String[] numbers = data[1].split("/");
				try {
					newLength = Double.parseDouble(numbers[0]) / 
								Double.parseDouble(numbers[1]);
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");						
				}
			}
		} else if(data.length == 3) {
			/*1. number, space, /, no space, 2. number*/
			if(data[1].indexOf('/') == -1) {
				try {
					newLength = Double.parseDouble(data[1])/
								Double.parseDouble(data[2].substring(1));
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");						
				}
			/*1. number, no space, /, space, 2. number*/
			} else if(data[2].indexOf('/') == -1) {
				try {
					newLength = Double.parseDouble(data[1].substring(0, data[1].length() - 1))/
								Double.parseDouble(data[2]);
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");											
				}
			} else {
				throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");
			}
		} else if(data.length == 4) {
			/*2 numbers separated with "/" with spaces everywhere.*/
			try {
				newLength = Double.parseDouble(data[1]) / 
							Double.parseDouble(data[3]);
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");						
			}
		} else {
			newLength = -1;
		}
		
		if(newLength <= 0) {
			throw new IllegalArgumentException("Wrong unitLengthDegreeScaler input.");					
		}
		unitLengthDegreeScaler = newLength;
		return true;
	}
	
	/**
	 *	Checks whether the given data describes 
	 *	a production rule and adds it if yes.
	 *	@param data The data to check.
	 *	@return <code>true</code> if yes, <code>false</code> otherwise.
	 *	@throws IllegalArgumentException If the given data describes a 
	 *									 a production rule which has more
	 *									 than 1 character or uses a character
	 *									 which already has a production rule.
	 */
	private boolean configureProduction(String[] data) {
		if(data.length != 3 || !data[0].equals("production")) {
			return false;
		}
		if(data[1].length() != 1) {
			throw new IllegalArgumentException("Production doesn't describe 1 character.");
		}
		if(commands.get(data[1].charAt(0)) != null) {
			throw new IllegalArgumentException("Cannot have 2 production rules which use the same character.");
		}
		productions.put(data[1].charAt(0), data[2]);
		return true;
	}
	
	
	/**
	 *	Checks whether the given data describes 
	 *	a configuration of axiom and configures it if yes.
	 *	@param data The data to check.
	 *	@return <code>true</code> if yes, <code>false</code> otherwise. 
	 */
	private boolean configureAxiom(String[] data) {
		if(data.length != 2 || !data[0].equals("axiom")) {
			return false;
		}
		axiom = data[1];
		return true;
	}
	
	/**
	 *	Checks whether the given data describes 
	 *	a configuration of unitLength and configures it if yes.
	 *	@param data The data to check.
	 *	@return <code>true</code> if yes, <code>false</code> otherwise.
	 *	@throws IllegalArgumentException If the given data describes a 
	 *									 a configuration of unitLength which
	 *									 is invalid, e.g. length <= 0
	 */
	private boolean configureUnitLength(String[] data) {
		if(data.length != 2 || !data[0].equals("unitLength")) {
			return false;
		}
		double newLength;
		try {
			newLength = Double.parseDouble(data[1]);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Wrong unitLeghth input."); 
		}
		if(newLength <= 0) {
			throw new IllegalArgumentException("Wrong unitLeghth input."); 
		}	
		unitLength = newLength;
		return true;
	}
	
	
	//TODO javadoc
	private boolean configureAngle(String[] data) {
		if(data.length != 2 || !data[0].equals("angle")) {
			return false;
		}
		double newAngle;
		try {
			newAngle = Double.parseDouble(data[1]);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Wrong angle input."); 
		}
		angle = Math.toRadians(newAngle);
		return true;
	}

	/**
	 *	Checks whether the given data describes 
	 *	a configuration of origin and configures origin if yes.
	 *	@param data The data to check.
	 *	@return <code>true</code> if yes, <code>false</code> otherwise.
	 *	@throws IllegalArgumentException If the given data describes a 
	 *									 a configuration of origin which
	 *									 is invalid, e.g. x or y < 0.
	 */
	private boolean configureOrigin(String[] data ) {
		if(data.length != 3 || !data[0].equals("origin")) {
			return false;
		}
		double x,y;
		try {
			x = Double.parseDouble(data[1]);
			y = Double.parseDouble(data[2]);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Wrong origin coordinates."); 
		}
		if(x < 0 || y < 0) {
			throw new IllegalArgumentException("Wrong origin coordinates.");
		}
		origin = new Vector2D(x, y);
		return true;
	}
	
	
	/**
	 * 	Private method which deduces the correct {@link Command}
	 * 	from the given input and returns a new instance of it.
	 * 	@param command String from which the {@link Command} should be deduced.
	 * 	@return The new {@link Command} of the correct type.
	 * 	@throws IllegalArgumentException If a {@link Command} can't be deduced from
	 * 									 the given {@link String}.
	 */
	private Command makeCommand(String[] command) {
		if(command.length != 1 && command.length != 2) {
			throw new IllegalArgumentException("Cannot parse command " + command);
		}
		
		switch(command[0]) {
			case("draw")  : return new DrawCommand  (Double.parseDouble(command[1]));
			case("skip")  : return new SkipCommand  (Double.parseDouble(command[1]));
			case("scale") : return new ScaleCommand (Double.parseDouble(command[1]));
			case("rotate"): return new RotateCommand(Math.toRadians(Double.parseDouble(command[1])));
			case("push")  : return new PushCommand  ();
			case("pop")   : return new PopCommand   ();
			case("color") : return new ColorCommand (new Color(Integer.parseInt(command[1], 16)));
			default       : throw  new IllegalArgumentException("Command isn't recognized.");
		}
	}
	
	/**
	 *	Method which adds a new {@link Command} of the correct type
	 *	deduced from the given parameters.
	 *	@param arg0 Character which signals when the command should happen.
	 *	@param arg1 String from which the {@link Command} is deduced.
	 *	@return this.
	 *	@throws IllegalArgumentException If a {@link Command} cannot be deduced 
	 *									 from the given parameters. 
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		commands.put(arg0, makeCommand(arg1.split("\\s")));
		return this;
	}

	/**
	 * 	Adds a new production rule.
	 * 	@param arg0 Character which needs to be swapped.
	 * 	@param arg1 Sequence for which the Character should be swapped.
	 * 	@return this.
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	/**
	 *	Sets the angle. Given angle should be in degrees.
	 *	@param arg0 Angle in degrees.
	 *	@return this. 
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = Math.toRadians(arg0);
		return this;
	}

	/**
	 * 	Sets the starting sequence.
	 * 	@param arg0 The new starting sequence.
	 * 	@return this.
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	/**
	 * 	Sets the origin point.
	 * 	@param The x-coordinate of the new origin point.
	 * 	@param The y-coordinate of the new origin point.
	 * 	@return this.
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	//TODO javadoc
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}
	
	//TODO javadoc
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
