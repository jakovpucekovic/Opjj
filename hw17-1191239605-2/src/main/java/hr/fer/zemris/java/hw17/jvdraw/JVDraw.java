package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorInfo;
import hr.fer.zemris.java.hw17.jvdraw.graphicalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.CurrentTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;

/**
 *	JVDraw TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	private DrawingModel model = new DrawingModelImpl();
	private JDrawingCanvas canvas;
	private JColorArea fgColor;
	private JColorArea bgColor;
	
	private CurrentTool currentTool;
	private LineTool lineTool;
	private CircleTool circleTool;
	private FilledCircleTool filledCircleTool;
	
	/**
	 * 	Constructs a new {@link JVDraw}.
	 */
	public JVDraw() {		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setLocation(10, 10);
		setSize(600, 800);
		
		initGUI();
		setClosingOperations();
	
	}
	
	/**
	 *  Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
//TODO	createActions();
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
		cp.add(list, BorderLayout.EAST);
		
		//add color info label
		JColorInfo bottomColorInfo = new JColorInfo(fgColor, bgColor);
		//fire the listener for the first time so that the label appears
		bottomColorInfo.newColorSelected(null, null, null); 
		cp.add(bottomColorInfo, BorderLayout.SOUTH);
	}
	
	private void createTools() {
		lineTool = new LineTool(model, fgColor);
		circleTool = new CircleTool(model, fgColor);
		filledCircleTool = new FilledCircleTool(model, fgColor, bgColor);
		currentTool = new CurrentTool(lineTool);
	}
	
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
						//TODO
					}
					case(KeyEvent.VK_PLUS):{
						
					}
					case(KeyEvent.VK_MINUS):{
						
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
					//TODO
				}
			}
		
		});
		
		return list;
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
		
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);
	}
	
	/**
	 *  Private method which creates the menu.
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");
		mb.add(file);
		
		JMenuItem open = new JMenuItem("Open");
		file.add(open);
		
		JMenuItem save = new JMenuItem("Save");
		file.add(save);
		
		JMenuItem saveAs = new JMenuItem("Save As");
		file.add(saveAs);
		
		JMenuItem export = new JMenuItem("Export");
		file.add(export);
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		
		setJMenuBar(mb);
	}

	
	//TODO
	private void exit() {
		dispose();
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
