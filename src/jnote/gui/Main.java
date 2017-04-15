package jnote.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

import jnote.utils.Utils;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Main extends JFrame {
	
	//HAY QUE ARREGLAR LOS SALTOS DE LINEA AL GUARDAR EL ARCHIVO

	private JPanel contentPane;
	private JMenuItem menuExit;
	private String currentFilePath;
	private boolean isSaved;
	private UndoManager manager = new UndoManager();
	private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		JFrame frame = this;
		setTitle("Sin título: Bloc de notas");
		currentFilePath = "";
		isSaved = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Archivo");
		menuBar.add(menuFile);
		
		JMenuItem menuNew = new JMenuItem("Nuevo");
		menuFile.add(menuNew);
		
		JMenuItem menuOpen = new JMenuItem("Abrir...");
		menuFile.add(menuOpen);
		
		JMenuItem menuSave = new JMenuItem("Guardar");
		menuFile.add(menuSave);
		
		JMenuItem menuSaveAs = new JMenuItem("Guardar como...");
		menuFile.add(menuSaveAs);
		menuFile.addSeparator();
		
		menuExit = new JMenuItem("Salir");
		menuFile.add(menuExit);
		
		JMenu menuEdicion = new JMenu("Edici\u00F3n");
		menuBar.add(menuEdicion);
		
		JMenuItem menuUndo = new JMenuItem("Deshacer");
		menuEdicion.add(menuUndo);
		
		menuEdicion.addSeparator();
		
		JMenuItem menuCut = new JMenuItem("Cortar");
		menuEdicion.add(menuCut);
		
		JMenuItem menuCopy = new JMenuItem("Copiar");
		menuEdicion.add(menuCopy);
		
		JMenuItem menuPaste = new JMenuItem("Pegar");
		menuEdicion.add(menuPaste);
		
		JMenuItem menuDelete = new JMenuItem("Eliminar");
		menuEdicion.add(menuDelete);
		
		menuEdicion.addSeparator();
		
		JMenuItem menuSearch = new JMenuItem("Buscar...");
		menuEdicion.add(menuSearch);
		
		JMenuItem menuReplace = new JMenuItem("Reemplazar...");
		menuEdicion.add(menuReplace);
		
		menuEdicion.addSeparator();
		
		JMenuItem menuSelectAll = new JMenuItem("Seleccionar todo");
		menuEdicion.add(menuSelectAll);
		
		JMenuItem menuDate = new JMenuItem("Hora y fecha");
		menuEdicion.add(menuDate);

		


		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new EmptyBorder(0, 1, 0, 1));
		textArea.getDocument().addUndoableEditListener(manager);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		contentPane.add(scrollPane);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		menuCut.setEnabled(false);
		menuCopy.setEnabled(false);
		menuDelete.setEnabled(false);
		
		JOptionPane notSavedPane = new JOptionPane();
		String[] options = {"Sí", "No"};
		notSavedPane.setMessage("¿Seguro que quieres salir sin guardar?");
		notSavedPane.setOptions(options);
		
		menuSearch.setEnabled(false);
		menuReplace.setEnabled(false);
		
		JMenu menuHelp = new JMenu("Ayuda");
		menuBar.add(menuHelp);
		
		JMenuItem menuAbout = new JMenuItem("Acerca de JNote");
		menuHelp.add(menuAbout);
		
		if(!manager.canUndo()){
			menuUndo.setEnabled(false);
		}
		//Listeners...
		
		textArea.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(textArea.getSelectedText() != null){
					menuCut.setEnabled(true);
					menuCopy.setEnabled(true);
					menuDelete.setEnabled(true);
				}else{
					menuCut.setEnabled(false);
					menuCopy.setEnabled(false);
					menuDelete.setEnabled(false);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			

		});
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				isSaved = false;
				menuCut.setEnabled(false);
				menuCopy.setEnabled(false);
				menuDelete.setEnabled(false);

				if(manager.canUndo()){
					menuUndo.setEnabled(true);
				}else{
					menuUndo.setEnabled(false);
				}
				
				if(textArea.getText().equals("")){
					menuSearch.setEnabled(false);
					menuReplace.setEnabled(false);
				}else{
					menuSearch.setEnabled(true);
					menuReplace.setEnabled(true);
				}
				
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				isSaved = false;
				menuCut.setEnabled(false);
				menuCopy.setEnabled(false);
				menuDelete.setEnabled(false);
				
				if(manager.canUndo()){
					menuUndo.setEnabled(true);
				}else{
					menuUndo.setEnabled(false);
				}
				
				if(textArea.getText().equals("")){
					menuSearch.setEnabled(false);
					menuReplace.setEnabled(false);
				}else{
					menuSearch.setEnabled(true);
					menuReplace.setEnabled(true);
				}
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				menuCut.setEnabled(false);
				menuCopy.setEnabled(false);
				menuDelete.setEnabled(false);
				isSaved = false;
				if(manager.canUndo()){
					menuUndo.setEnabled(true);
				}else{
					menuUndo.setEnabled(false);
				}
				
				if(textArea.getText().equals("")){
					menuSearch.setEnabled(false);
					menuReplace.setEnabled(false);
				}else{
					menuSearch.setEnabled(true);
					menuReplace.setEnabled(true);
				}
			}
		});
		
		menuSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentFilePath.equals("")){
					chooser.showSaveDialog(null);
					currentFilePath = chooser.getSelectedFile().getAbsolutePath();
					isSaved = Utils.save(textArea.getText(), currentFilePath);
				}else{
					isSaved = Utils.save(textArea.getText(), currentFilePath);
				}				
			}
		});
		
		menuSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.showSaveDialog(null);
				currentFilePath = chooser.getSelectedFile().getAbsolutePath();
				isSaved = Utils.save(textArea.getText(), currentFilePath);
				
			}
		});
		
		menuOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isSaved){
					chooser.showOpenDialog(null);
					currentFilePath = chooser.getSelectedFile().getAbsolutePath();
					textArea.setText(Utils.open(currentFilePath));
					frame.setTitle(chooser.getSelectedFile().getName() + ": Bloc de notas");
					isSaved = true;
				}else{
					int option = notSavedPane.showConfirmDialog(null, "¿Quieres abrir otro archivo sin guardar el actual?");
					if(option == 0){
						chooser.showOpenDialog(null);
						currentFilePath = chooser.getSelectedFile().getAbsolutePath();
						textArea.setText(Utils.open(currentFilePath));
						frame.setTitle(chooser.getSelectedFile().getName() + ": Bloc de notas");
						isSaved = true;
					}
				}
				
			}
		});
		
		menuNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isSaved){
					currentFilePath = "";
					textArea.setText("");
					frame.setTitle("Sin título: Bloc de notas");
					isSaved = true;
				}else{
					int option = notSavedPane.showConfirmDialog(null, "¿Seguro que quieres crear un nuevo archivo sin guardar?");
					if(option == 0){
						currentFilePath = "";
						textArea.setText("");
						frame.setTitle("Sin título: Bloc de notas");
						isSaved = true;
					}
				}
			}
		});
		
		menuExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isSaved){
					frame.dispose();
				}else{
					int option = notSavedPane.showConfirmDialog(null, "¿Seguro que salir sin guardar?");
					if(option == 0){
						frame.dispose();
					}
				}
				
			}
		});
		
		menuUndo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.undo();
				if(!manager.canUndo()){
					menuUndo.setEnabled(false);
				}
			}
		});
		
		menuCut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textArea.getSelectedText() != null){
					StringSelection myText = new StringSelection(textArea.getSelectedText());
					clipboard.setContents(myText, myText);
					textArea.replaceSelection("");
				}
				
			}
		});
		
		menuCopy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textArea.getSelectedText() != null){
					StringSelection myText = new StringSelection(textArea.getSelectedText());
					clipboard.setContents(myText, myText);
				}
				
			}
		});
		
		menuPaste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(clipboard.getContents(null).isDataFlavorSupported(DataFlavor.stringFlavor)){
					try{
						String clipboardText = (String)clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
						textArea.setText(textArea.getText() + clipboardText);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
			}
		});
		
		menuDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textArea.getSelectedText() != null){
					textArea.replaceSelection("");
				}
				
			}
		});
		
		menuDate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date(System.currentTimeMillis());
				DateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");

				String fecha = format.format(date);
				textArea.setText(textArea.getText() + fecha);
				
			}
		});
		
		menuSelectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setSelectionStart(0);
				textArea.setSelectionEnd(textArea.getText().length());
				
			}
		});
		
		menuSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Search s = new Search();
				s.setVisible(true);
				s.setChildTextField(textArea);
			}
		});
		
		menuReplace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Replace r = new Replace();
				r.setTextArea(textArea);
				r.setVisible(true);
				
			}
		});
		
		menuAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new About().setVisible(true);
				
			}
		});
		
		
	}

}
