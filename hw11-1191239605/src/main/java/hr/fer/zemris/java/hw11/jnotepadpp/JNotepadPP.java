package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 *	JNotepadPP TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class JNotepadPP extends JFrame {	
	
	private DefaultMultipleDocumentModel model;
	
	/**
	 * 	Constructs a new JNotepadPP.
	 * 	TODO javadoc
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(10, 10);
		setSize(500, 500);
		
		model = new DefaultMultipleDocumentModel();
		model.setModifiedIcon(loadPic("icons/modified.png"));
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				MultipleDocumentListener.super.currentDocumentChanged(previousModel, currentModel);
			}
			
			/**
			 * 	{@inheritDoc}
			 */
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				//TODO tko se brine o updateanju multiple modela, jnotepad preko ovakvog listenera ili samo model
			}
			
			/**
			 * 	{@inheritDoc}
			 */
			@Override
			public void documentAdded(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
		});
		
		initGUI();
		setClosingOperations();
	}
	
	
	//TODO javadoc
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createActions();
		createMenus();
		cp.add(createToolbar(), BorderLayout.NORTH);
		
		cp.add(model, BorderLayout.CENTER);
		
	}	
	
	
	/**
	 * TODO javadoc
	 */
	private void createActions() {
		newDocument.putValue(Action.NAME, "New");
		openDocument.putValue(Action.NAME, "Open");
		saveDocument.putValue(Action.NAME, "Save");
		saveAsDocument.putValue(Action.NAME, "Save as");
		closeDocument.putValue(Action.NAME, "Close");
		exitAction.putValue(Action.NAME, "Exit");
		
//		cutSelectedPart.putValue(Action.NAME,"Cut");
//		copySelectedPart.putValue(Action.NAME,"Copy");
//		pasteSelectedPart.putValue(Action.NAME,"Paste");

		saveDocument.putValue(Action.SMALL_ICON, loadPic("icons/save.png"));
		closeDocument.putValue(Action.SMALL_ICON, loadPic("icons/close.png"));
	}


	/**
	 *TODO javadoc
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		//TODO add info
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(closeDocument));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));

		JMenu edit = new JMenu("Edit");
		mb.add(edit);
//		edit.add(new JMenuItem(cutSelectedPart));
//		edit.add(new JMenuItem(copySelectedPart));
//		edit.add(new JMenuItem(pasteSelectedPart));
		
		setJMenuBar(mb);
		
	}


	/**
	 * @return //TODO javadoc
	 */
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton(newDocument));
		toolbar.add(new JButton(openDocument));
		toolbar.add(new JButton(saveDocument));
		toolbar.add(new JButton(saveAsDocument));
		toolbar.add(new JButton(closeDocument));
		toolbar.add(new JButton("Cut"));
		toolbar.add(new JButton("Copy"));
		toolbar.add(new JButton("Paste"));
		toolbar.add(new JButton("Info"));
		toolbar.add(new JButton("Exit"));
		
		return toolbar;
	}

	private ImageIcon loadPic(String path) {
		byte[] bytes = null;
		try(InputStream is = this.getClass().getResourceAsStream(path)){
			bytes = is.readAllBytes();
		} catch(Exception ex) {
			System.err.println("Couldnt load pic"); //TODO exception
		}
		ImageIcon image = new ImageIcon(bytes);
		return image;
	}
	
	private final Action newDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};
	
	private final Action openDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			load("Load"); //TODO i18n
		}
	};
	
	
	private final Action saveDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			save("Save"); //TODO i18n
		}
	};
	
	private final Action saveAsDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveAs("Save as"); //TODO i18n
		}
	};
	
	private final Action closeDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			close();
		}
	};
	
	private final Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	private void load(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		if(jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path chosenPath = jfc.getSelectedFile().toPath();
		
		if(chosenPath != null) {
			model.loadDocument(chosenPath);
		}
	}
	
	private void save(String text) {
		if(model.getCurrentDocument().getFilePath() == null) {
			saveAs(text);
		}
		model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
	}
	
	private void saveAs(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		if(jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path chosenPath = jfc.getSelectedFile().toPath();
		if(Files.exists(chosenPath)) {
			if(JOptionPane.showConfirmDialog(
					this,
					"File already exists. Overwrite?",
					"Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
				return; //TODO dodaj while petlju
			}
		}
		model.saveDocument(model.getCurrentDocument(), chosenPath);
	}
	
	private void close() {
		if(model.getCurrentDocument().isModified()) {
			if(JOptionPane.showConfirmDialog(
					this,
					"Document is modified. Save changes?",
					"Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				save("Save");//TODO i18n
			}
		}
		model.closeDocument(model.getCurrentDocument());
	}
	
	private void setClosingOperations() {
		//register closing listener
				addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						dispose();//TODO save all
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
					}
				});
	}
	
	
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(()->
				new JNotepadPP().setVisible(true));
		
	}
	
	
}
