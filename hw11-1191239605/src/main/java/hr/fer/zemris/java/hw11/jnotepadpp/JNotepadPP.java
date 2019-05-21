package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *	JNotepadPP TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class JNotepadPP extends JFrame {	
	
	private DefaultMultipleDocumentModel model;
	
	private JPanel statusbar;
	
	
	/**
	 * 	Constructs a new JNotepadPP.
	 * 	TODO javadoc
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(10, 10);
		setTitle("JNotepad++");
		setSize(500, 500);
		
		statusbar = new JPanel();
		
		model = new DefaultMultipleDocumentModel(); //TODO preimenuj iz model u nesto smislenije
		model.setModifiedIcons(loadPic("icons/modified.png"), loadPic("icons/unmodified.png"));
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override //TODO jel se tu misli na to da je novi dokument postao current ili da je doslo do promjena unutar current documenta
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				MultipleDocumentListener.super.currentDocumentChanged(previousModel, currentModel);
//				previousModel.getTextComponent().removeCaretListener(caretListener);
//				currentModel.getTextComponent().addCaretListener(caretListener); 
				//TODO napraviti statusbar kao poseban razred koji onda registrira caretListener?
			}
			
			/**
			 * 	{@inheritDoc}
			 */
			@Override
			public void documentRemoved(SingleDocumentModel model1) {
				//TODO tko se brine o updateanju multiple modela, jnotepad preko ovakvog listenera ili samo model
				if(model.getNumberOfDocuments() == 0) {
					closeDocument.setEnabled(false);
					infoAction.setEnabled(false);
				}
			}
			
			/**
			 * 	{@inheritDoc}
			 */
			@Override
			public void documentAdded(SingleDocumentModel model1) {
				if(model.getNumberOfDocuments() > 0) {
					closeDocument.setEnabled(true);
					infoAction.setEnabled(true);
				}
				
			}
		});
		
		model.addChangeListener(new ChangeListener() { //TODO gdje pratim kako se mjenjaju tabovi
			
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel doc = model.getCurrentDocument();
				if(doc != null && doc.getFilePath() != null) {
					setTitle(doc.getFilePath().toAbsolutePath().toString() + " - JNotepad++");
				} else {
					setTitle("(unnamed) - JNotepad++"); //TODO jel bi bilo pametnije postaviti path na unnamed ili neku varijablu koja prati ime?
				}
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
		
		configStatusbar();
		
		JLabel lab = new JLabel();
		lab.setLayout(new BorderLayout());
		lab.add(model, BorderLayout.CENTER);
		lab.add(statusbar, BorderLayout.SOUTH);
		
		cp.add(createToolbar(), BorderLayout.NORTH);
		
		cp.add(lab, BorderLayout.CENTER);
		
	}	
	
	
	private void configStatusbar() {
		statusbar.setLayout(new GridLayout(1, 0));
		statusbar.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel lenght = new JLabel("length: 0");
		JLabel line = new JLabel("Ln: 1");
		JLabel column = new JLabel("Col: 1");
		JLabel selection = new JLabel("Sel: 0");
		JLabel dateTime = new JLabel("datetime"); //TODO vrijeme i datum
				
		statusbar.add(lenght);
		statusbar.add(line);
		statusbar.add(column);
		statusbar.add(selection);
		statusbar.add(dateTime);
		
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
		closeDocument.setEnabled(false);
		infoAction.putValue(Action.NAME, "Info");
		infoAction.setEnabled(false);
		exitAction.putValue(Action.NAME, "Exit");
				
//		cutSelectedPart.putValue(Action.NAME,"Cut");
//		copySelectedPart.putValue(Action.NAME,"Copy");
//		pasteSelectedPart.putValue(Action.NAME,"Paste");

		newDocument.putValue(Action.SMALL_ICON, loadPic("icons/new.png"));
		openDocument.putValue(Action.SMALL_ICON, loadPic("icons/open.png"));
		saveDocument.putValue(Action.SMALL_ICON, loadPic("icons/save.png"));
		saveAsDocument.putValue(Action.SMALL_ICON, loadPic("icons/save_as.png"));
		closeDocument.putValue(Action.SMALL_ICON, loadPic("icons/close.png"));
		infoAction.putValue(Action.SMALL_ICON, loadPic("icons/info.png"));
		exitAction.putValue(Action.SMALL_ICON, loadPic("icons/exit.png"));
		
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
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
		edit.add(new JMenuItem(infoAction));
		
		setJMenuBar(mb);
		
	}


	/**
	 * @return //TODO javadoc
	 */
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		
		toolbar.add(new ToolbarButton(newDocument));
		toolbar.add(new ToolbarButton(openDocument));
		toolbar.add(new ToolbarButton(saveDocument));
		toolbar.add(new ToolbarButton(saveAsDocument));
		toolbar.add(new ToolbarButton(closeDocument));
//		toolbar.add(new ToolbarButton("Cut")); //TODO Jel mozemo koristiti DefaultEditorKit i kako?
//		toolbar.add(new ToolbarButton("Copy"));
//		toolbar.add(new ToolbarButton("Paste"));
		toolbar.add(new ToolbarButton(infoAction));
		toolbar.add(new ToolbarButton(exitAction));
		
		return toolbar;
	}

	
	//my button class which hides text
	private class ToolbarButton extends JButton{
		
		/**
		 * 	Constructs a new JNotepadPP.ToolbarButton.
		 * 	TODO javadoc
		 */
		public ToolbarButton(Action a) {
			super(a);
			setHideActionText(true);
		}
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
	
	private final Action infoAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			info("Statistics"); //TODO i18n
		}
	};
	
	
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
			exit("Exit"); //TODO i18n
		}
	};
	
	private void info(String text) {
		String docText = model.getCurrentDocument().getTextComponent().getText();
		int numberOfCharacters = docText.length();
		int numberOfNonBlankCharacters = 0;
		char[] docTextArray = docText.toCharArray();
		for(int i = 0; i < numberOfCharacters; ++i) {
			if(Character.isWhitespace(docTextArray[i])) {
				continue;
			}
			++numberOfNonBlankCharacters;
		}
		int numberOfLines = model.getCurrentDocument().getTextComponent().getLineCount();
		
		Object[] options = {"OK"};
		JOptionPane.showOptionDialog(
				this,
				"Your document has " + numberOfCharacters + " characters, " + numberOfNonBlankCharacters + " non-blank characters and " + numberOfLines + " lines.",
				"Statistical info",
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[0]);
		
		  
	}
			
	private void exit(String text) {
		for(int i = 0; i < model.getNumberOfDocuments(); ++i) {
			model.setSelectedIndex(i);
			if(model.getDocument(i).isModified()) {
				switch(JOptionPane.showConfirmDialog(
						this,
						"Document is modified. Do you want to save the chages?",
						"Warning",
						JOptionPane.YES_NO_CANCEL_OPTION)){
					case JOptionPane.YES_OPTION:
						save("Save");
						break;
					case JOptionPane.NO_OPTION:
						break;
					case JOptionPane.CANCEL_OPTION:
						return;
				}
			}
		}
		dispose();
	}
	
	private void load(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		if(jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		Path chosenPath = jfc.getSelectedFile().toPath();
		
		if(!Files.exists(chosenPath)) {
			if(JOptionPane.showConfirmDialog(
					this,
					"Document you're trying to open doesn't exist. Create one instead?", //TODO i18n
					"Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
				return;
			} else {
				model.createNewDocument();
				model.getCurrentDocument().setFilePath(chosenPath);
			}
		}
		
		if(!Files.isWritable(chosenPath)) {
			JOptionPane.showMessageDialog(this, "Don't have permissions to edit this file.");
			return;
		}
		
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
						exit("Exit");
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
		SwingUtilities.invokeLater(()->{
			JNotepadPP notepadpp = new JNotepadPP();
//			notepadpp.pack();// TODO jel bolje preko pack ili dati neki fiksni default size?
			notepadpp.setVisible(true);
		});
	}
		
}
