package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorInfo;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.model.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.CurrentTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledTriangleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectSave;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 *	Main class which implements all the functionality.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	/**{@link DrawingModel} used for keeping track of {@link GeometricalObject}s.*/
	private DrawingModel model = new DrawingModelImpl();
	
	/**Canvas used for drawing {@link GeometricalObject}s.*/
	private JDrawingCanvas canvas;
	
	/**Allows the user to choose draw color.*/
	private JColorArea fgColor;
	
	/**Allows the user to choose fill color.*/
	private JColorArea bgColor;
	
	
	/**Provider of the current {@link Tool}.*/
	private CurrentTool currentTool;
	
	/**Used for drawing lines.*/
	private LineTool lineTool;
	
	/**Used for drawing circles.*/
	private CircleTool circleTool;
	
	/**Used for drawing filled circles.*/
	private FilledCircleTool filledCircleTool;
	
	private FilledTriangleTool filledTriangleTool;
	
	
	/**Opens a new file.*/
	private Action openAction;
	
	/**Saves the current file.*/
	private Action saveAction;
	
	/**Saves the current file as another file.*/
	private Action saveAsAction;
	
	/**Exports the current file.*/
	private Action exportAction;
	
	/**Exits the application.*/
	private Action exitAction;
	
	/**Path to the currently open file.*/
	private Path path;
	
	
	/**
	 * 	Constructs a new {@link JVDraw}.
	 */
	public JVDraw() {		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setLocation(10, 10);
		setSize(800, 800);
		
		initGUI();
		setClosingOperations();
	}
	
	/**
	 *  Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createActions();
		createMenu();
		
		//add toolbar
		JToolBar toolbar = new JToolBar();
		
		//add color area
		fgColor = new JColorArea(Color.RED);
		bgColor = new JColorArea(Color.BLUE);
		toolbar.add(fgColor);
		toolbar.add(bgColor);
		cp.add(toolbar, BorderLayout.NORTH);

		//add buttons
		addButtons(toolbar);
		
		//create tools
		createTools();
		
		//add canvas
		canvas = new JDrawingCanvas(model, currentTool);
		cp.add(canvas, BorderLayout.CENTER);
		
		//add list
		JList<GeometricalObject> list = createList();
		cp.add(new JScrollPane(list), BorderLayout.EAST);
		
		//add color info label
		JColorInfo bottomColorInfo = new JColorInfo(fgColor, bgColor);
		//fire the listener for the first time so that the label appears
		bottomColorInfo.newColorSelected(null, null, null); 
		cp.add(bottomColorInfo, BorderLayout.SOUTH);
	}
	
	/**
	 *	Private method which creates all the {@link Action}s. 
	 */
	private void createActions() {
		openAction = new AbstractAction("Open") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				open();				 
			}
		};
		
		saveAction = new AbstractAction("Save") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		};
		
		saveAsAction = new AbstractAction("Save As") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		};
		
		exportAction = new AbstractAction("Export") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				export();
			}
		};
		
		exitAction = new AbstractAction("Exit") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		};
		
	}
	
	/**
	 *	Opens a new document and loads its contents. Saves the current
	 *	document before opening the new one. 
	 */
	private void open() {
		if(model.isModified()) {
			if(JOptionPane.showConfirmDialog(JVDraw.this,
										 "You're about to open a new document, but this document is modified. Do you want to save it first?",
										 "Warning",
										 JOptionPane.YES_NO_OPTION,
										 JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				save();
			}
		}
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files", "jvd");
		chooser.setFileFilter(filter);
		if(chooser.showOpenDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().toPath();
			try {
				model.clear();
				List<String> list = Files.readAllLines(path);
				for(var l : list) {
					String[] params = l.split(" ");
					if(params[0].equals("LINE")) {
						Point start = new Point(Integer.parseInt(params[1]), Integer.parseInt(params[2]));
						Point end = new Point(Integer.parseInt(params[3]), Integer.parseInt(params[4]));
						Color color = new Color(Integer.parseInt(params[5]),Integer.parseInt(params[6]),Integer.parseInt(params[7]));
						model.add(new Line(start, end, color));
					} else if(params[0].equals("CIRCLE")) {
						Point center = new Point(Integer.parseInt(params[1]),Integer.parseInt(params[2]));
						int radius = Integer.parseInt(params[3]);
						Color color = new Color(Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]));
						model.add(new Circle(center, radius, color));
					} else if(params[0].equals("FCIRCLE")) {
						Point center = new Point(Integer.parseInt(params[1]),Integer.parseInt(params[2]));
						int radius = Integer.parseInt(params[3]);
						Color borderColor = new Color(Integer.parseInt(params[4]),Integer.parseInt(params[5]),Integer.parseInt(params[6]));
						Color fillColor = new Color(Integer.parseInt(params[7]),Integer.parseInt(params[8]),Integer.parseInt(params[9]));
						model.add(new FilledCircle(center, radius, borderColor, fillColor));								
					} else if(params[0].equals("FTRIANGLE")) {
						Point a = new Point(Integer.parseInt(params[1]),Integer.parseInt(params[2]));
						Point b = new Point(Integer.parseInt(params[3]),Integer.parseInt(params[4]));
						Point c = new Point(Integer.parseInt(params[5]),Integer.parseInt(params[6]));
						Color bColor = new Color(Integer.parseInt(params[7]), Integer.parseInt(params[8]), Integer.parseInt(params[9]));
						Color fColor = new Color(Integer.parseInt(params[10]),Integer.parseInt(params[11]),Integer.parseInt(params[12]));
						model.add(new FilledTriangle(a, b, c, bColor, fColor));
					}else {
						continue;
					}
				}
			} catch (Exception ex) {
				JOptionPane.showConfirmDialog(JVDraw.this, 
											 "Cannot read file.", 
											 "Error", 
											 JOptionPane.OK_CANCEL_OPTION,
											 JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 *	Private method which saves the current document if it's
	 *	modified. Also, if the document has not been saved before,
	 *	forwards the action to the saveAs() function. 
	 */
	private void save() {
		if(!model.isModified()) {
			return;
		}
		if(path == null) {
			saveAs();
		} else {
			try {
				GeometricalObjectVisitor saver = new GeometricalObjectSave(path);
				for(int i = 0; i < model.getSize(); ++i) {
			       	model.getObject(i).accept(saver);
			    }
				model.clearModifiedFlag();
			} catch (Exception ex) {
				JOptionPane.showConfirmDialog(JVDraw.this, 
						 "Error saving file.", 
						 "Error", 
						 JOptionPane.OK_CANCEL_OPTION,
						 JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * 	Private method which saves the current document as a
	 * 	new document with the extesion ".jvd" on disk. 
	 */
	private void saveAs() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files", "jvd");
		chooser.setFileFilter(filter);
		if(chooser.showSaveDialog(JVDraw.this) == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().toPath();
			if(!path.endsWith(".jvd")) {
				path = Paths.get(path.toString() + ".jvd");
			}
			
			try {
				if(Files.notExists(path)) {
					Files.createFile(path);
				}
				GeometricalObjectSave saver = new GeometricalObjectSave(path);
				for(int i = 0; i < model.getSize(); ++i) {
			       	model.getObject(i).accept(saver);
				}
				model.clearModifiedFlag();
			} catch (Exception ex) {
				JOptionPane.showConfirmDialog(JVDraw.this, 
						 "Error saving file.", 
						 "Error", 
						 JOptionPane.OK_CANCEL_OPTION,
						 JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
	
	/**
	 *  Private method which exports the current document as a png, jpg or gif image,
	 *  based on what the user has decided. Default is png.
	 */
	private void export() {
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for(int i = 0; i < model.getSize(); ++i) {
			model.getObject(i).accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox();
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.translate(-box.x, -box.y);
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		for(int i = 0; i < model.getSize(); ++i) {
			model.getObject(i).accept(painter);
		}
		g.dispose();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, GIF and JPG files", "png", "gif", "jpg");
		chooser.setFileFilter(filter);
		if(chooser.showDialog(JVDraw.this, "Export") == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().toPath();
			try {
				if(path.toString().endsWith(".gif")) {
					ImageIO.write(image, "gif", path.toFile());
				} else if(path.toString().endsWith(".jpg")) {
					ImageIO.write(image, "jpg", path.toFile());
				} else if(path.toString().endsWith(".png")) {
					ImageIO.write(image, "png", path.toFile());
				} else {
					path = Paths.get(path.toString() + ".png");
					ImageIO.write(image, "png", path.toFile());
				}
			} catch (Exception ex) {
				JOptionPane.showConfirmDialog(JVDraw.this, 
						 "Error exporting file.", 
						 "Error", 
						 JOptionPane.OK_CANCEL_OPTION,
						 JOptionPane.ERROR_MESSAGE);
			}	
			
			JOptionPane.showConfirmDialog(JVDraw.this, 
										 "Export successful",
										 "Message",
										 JOptionPane.OK_CANCEL_OPTION);
		}	
	}
	
	/**
	 * 	Exits the application after saving if there were unsaved changes.
	 */
	private void exit() {
		if(model.isModified()) {
			int response = JOptionPane.showConfirmDialog(JVDraw.this,
														"Do you want to save before closing?",
														"Warning",
														JOptionPane.YES_NO_CANCEL_OPTION,
														JOptionPane.WARNING_MESSAGE);
			if(response == JOptionPane.YES_OPTION) {
				save();
			} else if(response == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		dispose();
	}

	/**
	 *  Private method which creates the menu.
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
	
		JMenu file = new JMenu("File");
		mb.add(file);
		
		JMenuItem open = new JMenuItem(openAction);
		file.add(open);
		
		JMenuItem save = new JMenuItem(saveAction);
		file.add(save);
		
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		file.add(saveAs);
		
		JMenuItem export = new JMenuItem(exportAction);
		file.add(export);
		
		JMenuItem exit = new JMenuItem(exitAction);
		file.add(exit);
		
		setJMenuBar(mb);
	}

	/**
	 *	Private method which adds the buttons on the toolbar. 
	 */
	private void addButtons(JToolBar toolbar) {
		ButtonGroup buttons = new ButtonGroup();
		
		JToggleButton line = new JToggleButton(new AbstractAction("Line") {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool.changeToolTo(lineTool);
			}
		});
		line.setSelected(true);
		buttons.add(line);
		
		JToggleButton circle = new JToggleButton(new AbstractAction("Circle") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool.changeToolTo(circleTool);
			}
		});
		buttons.add(circle);
		
		JToggleButton filledCircle = new JToggleButton(new AbstractAction("Filled Circle") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool.changeToolTo(filledCircleTool);
			}
		});
		buttons.add(filledCircle);
		
		JToggleButton filledTriangle = new JToggleButton(new AbstractAction("Filled Triangle") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool.changeToolTo(filledTriangleTool);
			}
		});
		buttons.add(filledTriangle);
		
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);
		toolbar.add(filledTriangle);
	}

	/**
	 *	Creates all of the different {@link Tool}s which will be used and
	 *	sets the initial {@link Tool} on a {@link LineTool}. 
	 */
	private void createTools() {
		lineTool = new LineTool(model, fgColor);
		circleTool = new CircleTool(model, fgColor);
		filledCircleTool = new FilledCircleTool(model, fgColor, bgColor);
		filledTriangleTool = new FilledTriangleTool(bgColor, fgColor, model);
		currentTool = new CurrentTool(lineTool);
	}
	
	/**
	 *	Creates a {@link JList} of {@link GeometricalObject}s which has some
	 * 	additional functionality. 
	 */
	private JList<GeometricalObject> createList(){
		JList<GeometricalObject> list = new JList<GeometricalObject>(new DrawingObjectListModel(model));
		list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		list.addKeyListener(new KeyAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case(KeyEvent.VK_DELETE):{
						model.remove(list.getSelectedValue());
						break;
					}
					case(KeyEvent.VK_PLUS):{
						if(model.indexOf(list.getSelectedValue()) < model.getSize() - 2) {
							model.changeOrder(list.getSelectedValue(), 1);
						}
						break;
					}
					case(KeyEvent.VK_MINUS):{
						if(model.indexOf(list.getSelectedValue()) > 0) {
							model.changeOrder(list.getSelectedValue(), -1);
						}
						break;
					}
				}
			}
				
		});
		
		list.addMouseListener(new MouseAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					GeometricalObjectEditor editor = list.getSelectedValue().createGeometricalObjectEditor();
					if(JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit object", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch(Exception ex) {
							JOptionPane.showConfirmDialog(JVDraw.this,
														 "An unexpected error happened. Object couldn't be edited.", 
														 "Error", 
														 JOptionPane.OK_CANCEL_OPTION, 
														 JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		
		});
		
		return list;
	}
	
	/**
	 * 	Sets closing operation which exits the program when user clicks
	 * 	the x button.
	 */
	private void setClosingOperations() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});		
	}
	
	
	/**
	 *  Main method which runs the program.
	 * 	@param args None.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new JVDraw().setVisible(true));
	}
	
}
