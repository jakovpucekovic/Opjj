package ispit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


/**
 *	Datumi TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Datumi extends JFrame {


	private static final long serialVersionUID = 1L;

	
	private Container cp;
	private JLabel label;
	private JList<LocalDate> list = new JList<>();
	private Action openAction;
	List<String> lines;
	List<LocalDate> dates = new ArrayList<>();
	
	public Datumi() {
		super();
		
		addListeners();
		initActions();
		initGui();
	}
	
	
	private void addListeners() {
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				LocalDate selectedDate = list.getSelectedValue();
				String dan = "";
				if(selectedDate.isAfter(LocalDate.now())) {
					dan += " biti će";
					switch(selectedDate.getDayOfWeek()) {
					case FRIDAY: dan += " petak";break;
					case THURSDAY: dan += " cetvrtak";break;
					case MONDAY: dan += " ponedjeljak";break;
					case TUESDAY: dan += " utorak";break;
					case SATURDAY: dan += " subota";break;
					case SUNDAY: dan += " nedjelja";break;
					case WEDNESDAY: dan += " srijeda";break;
					}
				} else if(selectedDate.isBefore(LocalDate.now())) {
					switch(selectedDate.getDayOfWeek()) {
					case FRIDAY: dan = " bio je petak";break;
					case THURSDAY: dan = " bio je cetvrtak";break;
					case MONDAY: dan = " bio je ponedjeljak";break;
					case TUESDAY: dan = " bio je utorak";break;
					case SATURDAY: dan = " bila je subota";break;
					case SUNDAY: dan = " bila je nedjelja";break;
					case WEDNESDAY: dan = " bila je srijeda";break;
					}
				} else {
					dan += " je";
					switch(selectedDate.getDayOfWeek()) {
					case FRIDAY: dan += " petak";break;
					case THURSDAY: dan += " cetvrtak";break;
					case MONDAY: dan += " ponedjeljak";break;
					case TUESDAY: dan += " utorak";break;
					case SATURDAY: dan += " subota";break;
					case SUNDAY: dan += " nedjelja";break;
					case WEDNESDAY: dan += " srijeda";break;
					}
				}
				label.setText("Na datum " + selectedDate + dan + ".");
			}
		});
	}
	
	private void initActions() {
		openAction = new AbstractAction("Open") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					open();
					parse();
				} catch (IOException e1) {
				}
				
			}
		};
	}
	
	private void parse() {
		dates = new ArrayList<>();
		for(var line: lines) {
			if(line.startsWith("#") || line.isBlank()) {
				continue;
			} else {
				dates.add(LocalDate.parse(line));
			}
		}
		LocalDate[] dateArray = {};
		list.setListData(dates.toArray(dateArray));
	}
	
	private void open() throws IOException{
		JFileChooser jfc = new JFileChooser();
		
		if(jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path chosenPath = jfc.getSelectedFile().toPath();
		
		if(chosenPath != null) {
			lines = Files.readAllLines(chosenPath);
		}
	}
	
	private void initGui() {
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createMenu();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		
		panel.add(new JScrollPane(list));

		
		label = new JLabel();
		panel.add(label);
		
		cp.add(panel);
		
	}
	
	
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		mb.add(file);
		
		file.add(new JMenuItem(openAction));
		
		setJMenuBar(mb);
	}
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Datumi();
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		});
	}
	
	
	
}
