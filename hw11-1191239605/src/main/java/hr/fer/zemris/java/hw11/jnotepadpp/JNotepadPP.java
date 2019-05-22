package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 *	JNotepadPP TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class JNotepadPP extends JFrame {	
	
	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel tabModel;
	
	private Statusbar statusbar;
	
	private FormLocalizationProvider flp;
	
	
	private Action newDocument;
	private Action openDocument;
	private Action saveDocument;
	private Action saveAsDocument;
	private Action closeDocument;
	private Action infoAction;
	private Action exitAction;
	
	private Action cutAction = new DefaultEditorKit.CutAction();
	private Action copyAction = new DefaultEditorKit.CopyAction();	
	private Action pasteAction = new DefaultEditorKit.PasteAction();


	/**
	 * 	Constructs a new JNotepadPP.
	 * 	TODO javadoc
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(10, 10);
		setTitle("(unnamed) - JNotepad++");
		setSize(500, 500);
		
		statusbar = new Statusbar();
		
		tabModel = new DefaultMultipleDocumentModel();
		tabModel.setModifiedIcons(loadPic("icons/modified.png"), loadPic("icons/unmodified.png"));
		
		addListeners();
		initGUI();
		setClosingOperations();
	}
	
	//TODO javadoc
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createActions();
		createMenus();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(tabModel, BorderLayout.CENTER);
		panel.add(statusbar, BorderLayout.SOUTH);

		tabModel.createNewDocument();	
		
		cp.add(createToolbar(), BorderLayout.NORTH);
		cp.add(panel, BorderLayout.CENTER);
	}	
	
	private void addListeners() {
		//updates the statusbar when tab comes into focus
		tabModel.addMultipleDocumentListener(new MultipleDocumentListenerAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				super.currentDocumentChanged(previousModel, currentModel);
				statusbar.updateStatusbar(tabModel.getCurrentDocument().getTextComponent());
			};
		});
				
		
		//updates statusbar as text is entered
		tabModel.addMultipleDocumentListener(new MultipleDocumentListenerAdapter() {
			
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				super.currentDocumentChanged(previousModel, currentModel);
				if(previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(e -> statusbar.updateStatusbar(e));
				}
				if(currentModel != null) {
					currentModel.getTextComponent().addCaretListener(e -> statusbar.updateStatusbar(e));
				}
			}
		});
		
				
		//updates the title of JNotepadPP to match the path of the current tab		
		tabModel.addMultipleDocumentListener(new MultipleDocumentListenerAdapter() {
		
			/**
			 *	{@inheritDoc}
			 */
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				super.currentDocumentChanged(previousModel, currentModel);
				SingleDocumentModel doc = tabModel.getCurrentDocument();
				if(doc.getFilePath() != null) {
					setTitle(doc.getFilePath().toAbsolutePath().toString() + " - JNotepad++");
				} else {
					setTitle("(unnamed) - JNotepad++");
				}
			}
		});
	}
	
	private class Statusbar extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private JLabel lenght;
		private JLabel line;
		private JLabel column;
		private JLabel selection;
		private JLabel dateTime;
		Timer clock;
		
		public Statusbar(){
			super();
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.black));	
			
			lenght = new JLabel("Length: 0");
			line = new JLabel("Ln: 1");
			column = new JLabel("Col: 0");
			selection = new JLabel("Sel: 0");
			dateTime = new JLabel("");
				
			clock = new Timer(100, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					dateTime.setText(dateFormat.format(new Date()));
				}
			}); 	
			clock.start();
			
			initStatusbar();
		}
		
		private void initStatusbar() {
			JPanel left = new JPanel();

			left.add(lenght);
			left.add(line);
			left.add(column);
			left.add(selection);
			
			add(left, BorderLayout.WEST);
			add(dateTime, BorderLayout.EAST);
		}
		
		private void updateStatusbar(EventObject e) {
			JTextArea area = (JTextArea)e.getSource();
			updateStatusbar(area);
			
		}
		
		private void updateStatusbar(JTextArea area) {
			lenght.setText("Length: " + area.getText().length());
			
            int ln = 1;
            int col = 1;
            try {
                ln = area.getLineOfOffset(area.getCaretPosition());
                col = area.getCaretPosition() - area.getLineStartOffset(ln);
                ln++;
            }
            catch(Exception ex) { 
            }
            
            line.setText("Ln: " + ln);
            column.setText("Col: " + col);
            
            selection.setText("Sel: " + Math.abs((area.getCaret().getMark() - area.getCaret().getDot())));
		}
		
	}


	/**
	 * TODO javadoc
	 */
	private void createActions() {
		newDocument = new LocalizableAction("new", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tabModel.createNewDocument();
			}
		};
		newDocument.putValue(Action.SMALL_ICON, loadPic("icons/new.png"));
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		openDocument = new LocalizableAction("open", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				load("Load"); //TODO i18n
			}
		};
		openDocument.putValue(Action.SMALL_ICON, loadPic("icons/open.png"));
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
	
		saveDocument = new LocalizableAction("save" , flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				save("Save"); //TODO i18n
			}
		};
		saveDocument.putValue(Action.SMALL_ICON, loadPic("icons/save.png"));
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocument = new LocalizableAction("saveAs", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs("Save as"); //TODO i18n
			}
		};
		saveAsDocument.putValue(Action.SMALL_ICON, loadPic("icons/save_as.png"));
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		closeDocument = new LocalizableAction("close", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		};
		closeDocument.putValue(Action.SMALL_ICON, loadPic("icons/close.png"));
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		exitAction = new LocalizableAction("exit", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exit("Exit"); //TODO i18n
			}
		};
		exitAction.putValue(Action.SMALL_ICON, loadPic("icons/exit.png"));
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		infoAction = new LocalizableAction("statInfo", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				info("Statistics"); //TODO i18n
			}
		};
		infoAction.putValue(Action.SMALL_ICON, loadPic("icons/info.png"));
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		//TODO dodati cut,copy,paste kao localizable
		cutAction.putValue(Action.NAME,"Cut");
		cutAction.putValue(Action.SMALL_ICON, loadPic("icons/cut.png"));

		copyAction.putValue(Action.NAME,"Copy");
		copyAction.putValue(Action.SMALL_ICON, loadPic("icons/copy.png"));

		pasteAction.putValue(Action.NAME,"Paste");
		pasteAction.putValue(Action.SMALL_ICON, loadPic("icons/paste.png"));
		
	}

	/**
	 *TODO javadoc
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(closeDocument));
		file.addSeparator();
		file.add(new JMenuItem(infoAction));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));

		JMenu edit = new JMenu("Edit");
		mb.add(edit);
		edit.add(new JMenuItem(cutAction));
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(pasteAction));

		JMenu lang = new JMenu("Languages");
		mb.add(lang);
		
		JMenuItem hrv = new JMenuItem(new LocalizableAction("hrv", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		});
		lang.add(hrv);

		JMenuItem eng = new JMenuItem(new LocalizableAction("eng", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
		lang.add(eng);

		JMenuItem ger = new JMenuItem(new LocalizableAction("ger", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		});
		lang.add(ger);
		
		JMenu tools = new JMenu("Tools");
		mb.add(tools);
		
		JMenu changeCase = new JMenu("Change case");
		tools.add(changeCase);
//		changeCase.add(new JMenuItem(toUppercaseAction));
//		changeCase.add(new JMenuItem(toLowercaseAction));
//		changeCase.add(new JMenuItem(invertCaseAction));
		
		JMenu sort = new JMenu("Sort");
		tools.add(sort);
//		sort.add(new JMenuItem(ascendingAction));
//		sort.add(new JMenuItem(descendingAction));
		
//		tools.add(new JMenuItem(uniqueAction));
		
		setJMenuBar(mb);
		
	}


	/**
	 * @return //TODO javadoc
	 */
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
//		toolbar.setLayout(new FlowLayout());
		//TODO pokusaj staviti flow layout
		toolbar.setFloatable(true);
		
		toolbar.add(new ToolbarButton(newDocument));
		toolbar.add(new ToolbarButton(openDocument));
		toolbar.add(new ToolbarButton(saveDocument));
		toolbar.add(new ToolbarButton(saveAsDocument));
		toolbar.add(new ToolbarButton(closeDocument));
		toolbar.add(new ToolbarButton(infoAction));
		toolbar.add(new ToolbarButton(exitAction));

		toolbar.add(new ToolbarButton(cutAction));
		toolbar.add(new ToolbarButton(copyAction));
		toolbar.add(new ToolbarButton(pasteAction));
		
		return toolbar;
	}

	
	//my button class which hides text
	private class ToolbarButton extends JButton{
		
		private static final long serialVersionUID = 1L;

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
			System.err.println("Couldnt load pic"); //TODO sto ako ne moze ucitat sliku
		}
		ImageIcon image = new ImageIcon(bytes);
		return image;
	}

	private void info(String text) {
		String docText = tabModel.getCurrentDocument().getTextComponent().getText();
		int numberOfCharacters = docText.length();
		int numberOfNonBlankCharacters = 0;
		char[] docTextArray = docText.toCharArray();
		for(int i = 0; i < numberOfCharacters; ++i) {
			if(Character.isWhitespace(docTextArray[i])) {
				continue;
			}
			++numberOfNonBlankCharacters;
		}
		int numberOfLines = tabModel.getCurrentDocument().getTextComponent().getLineCount();
		
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
		for(int i = 0; i < tabModel.getNumberOfDocuments(); ++i) {
			tabModel.setSelectedIndex(i);
			if(tabModel.getDocument(i).isModified()) {
				String docName = tabModel.getDocument(i).getFilePath() == null ? "(unnamed)" : tabModel.getDocument(i).getFilePath().getFileName().toString();
				switch(JOptionPane.showConfirmDialog(
						this,
						docName + "is modified. Do you want to save the chages?",
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
		
		for(int i = 0; i < tabModel.getNumberOfDocuments(); ++i) {
			tabModel.closeDocument(tabModel.getDocument(i));
		}
		
		statusbar.clock.stop();
		dispose();
	}
	
	private void load(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt" ,"txt");
		jfc.setFileFilter(filter);
		
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
				tabModel.createNewDocument();
				tabModel.getCurrentDocument().setFilePath(chosenPath);
				return;
			}
		}
		
		if(!Files.isWritable(chosenPath) || !Files.isReadable(chosenPath)) {
			JOptionPane.showMessageDialog(this, "Don't have permissions to edit this file.");
			return;
		}
		
		if(chosenPath != null) {
			try {
				tabModel.loadDocument(chosenPath);
			} catch(RuntimeException ex) {
				JOptionPane.showMessageDialog(this, "File cannot be loaded.", "Error loading file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void save(String text) {
		if(tabModel.getCurrentDocument().getFilePath() == null) {
			saveAs(text);
		} else {
			try {
				tabModel.saveDocument(tabModel.getCurrentDocument(), tabModel.getCurrentDocument().getFilePath());
			} catch(RuntimeException ex) {
				JOptionPane.showMessageDialog(this, "File cannot be saved.", "Error saving file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void saveAs(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt" ,"txt");
		jfc.setFileFilter(filter);
		if(jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path chosenPath = jfc.getSelectedFile().toPath();
		if(Files.exists(chosenPath)) {
			if(JOptionPane.showConfirmDialog(this, "File already exists. Overwrite?", "Warning",	JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
				Object[] options = {"OK"};
				JOptionPane.showOptionDialog(
						this,
						"Save aborted.",
						"Info",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]);
				return;
			}
		}
		try {
			tabModel.saveDocument(tabModel.getCurrentDocument(), chosenPath);
		}catch(IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error saving file", JOptionPane.ERROR_MESSAGE);
		} catch(RuntimeException ex) {
			JOptionPane.showMessageDialog(this, "File cannot be saved.", "Error saving file", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void close() {
		if(tabModel.getCurrentDocument().isModified()) {
			if(JOptionPane.showConfirmDialog(
					this,
					"Document is modified. Save changes?",
					"Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				save("Save");//TODO i18n
			}
		}
		tabModel.closeDocument(tabModel.getCurrentDocument());
	}
	
	private void setClosingOperations() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit("Exit");
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
