package jnote.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jnote.utils.Utils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;

public class Replace extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSearch;
	private JTextField textFieldReplace;
	private JTextArea textArea;
	private JCheckBox checkCapital;
	private boolean isSearched;
	private ArrayList<Integer> results;
	private JFrame frame;
	private int indexSearch = 0;
	private int indexReplace = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Replace frame = new Replace();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setTextArea(JTextArea textArea){
		this.textArea = textArea;
	}

	/**
	 * Create the frame.
	 */
	public Replace() {
		frame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 190);
		setTitle("Reemplazar");
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSearch = new JButton("Buscar siguiente");
		btnSearch.setBounds(286, 11, 123, 23);
		contentPane.add(btnSearch);
		
		JButton btnReplace = new JButton("Reemplazar");
		btnReplace.setBounds(286, 45, 123, 23);
		contentPane.add(btnReplace);
		
		JButton btnReplaceAll = new JButton("Reemplazar todo");
		btnReplaceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnReplaceAll.setBounds(286, 79, 123, 23);
		contentPane.add(btnReplaceAll);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(286, 110, 123, 23);
		contentPane.add(btnCancel);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(86, 12, 176, 20);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		textFieldReplace = new JTextField();
		textFieldReplace.setBounds(86, 46, 176, 20);
		contentPane.add(textFieldReplace);
		textFieldReplace.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBounds(10, 15, 46, 14);
		contentPane.add(lblBuscar);
		
		JLabel lblReemplazar = new JLabel("<html>Reemplazar <br> por: </html>");
		lblReemplazar.setBounds(10, 45, 66, 28);
		contentPane.add(lblReemplazar);
		
		checkCapital = new JCheckBox("Coincidir may\u00FAsculas y min\u00FAsculas");
		checkCapital.setBounds(10, 121, 234, 23);
		contentPane.add(checkCapital);
		
		btnSearch.setEnabled(false);
		btnReplace.setEnabled(false);
		btnReplaceAll.setEnabled(false);
		
		//listeners
		textFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(textFieldSearch.getText().isEmpty()){
					btnSearch.setEnabled(false);
					btnReplace.setEnabled(false);
					btnReplaceAll.setEnabled(false);
				}else{
					btnSearch.setEnabled(true);
					btnReplace.setEnabled(true);
					btnReplaceAll.setEnabled(true);
				}
				isSearched = false;
				indexSearch = 0;
				indexReplace = 0;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(textFieldSearch.getText().isEmpty()){
					btnSearch.setEnabled(false);
					btnReplace.setEnabled(false);
					btnReplaceAll.setEnabled(false);
				}else{
					btnSearch.setEnabled(true);
					btnReplace.setEnabled(true);
					btnReplaceAll.setEnabled(true);
				}
				isSearched = false;
				indexSearch = 0;
				indexReplace = 0;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(textFieldSearch.getText().isEmpty()){
					btnSearch.setEnabled(false);
					btnReplace.setEnabled(false);
					btnReplaceAll.setEnabled(false);
				}else{
					btnSearch.setEnabled(true);
					btnReplace.setEnabled(true);
					btnReplaceAll.setEnabled(true);
				}
				isSearched = false;
				indexSearch = 0;
				indexReplace = 0;
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				search();
				if(results.size() != 0){
					isSearched = true;
					textArea.setSelectionStart(results.get(indexSearch));
					textArea.setSelectionEnd(results.get(indexSearch)+ textFieldSearch.getText().length());
					indexSearch++;
				}else{
					JOptionPane.showMessageDialog(frame, "No se encontró '" + textFieldSearch.getText() + "'.");
				}
				
				if(indexSearch == results.size()){
					indexSearch = 0;
				}
				
			}
		});
		
		btnReplace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
				if(results.size() != 0){
					isSearched = true;
					textArea.setSelectionStart(results.get(indexReplace));
					textArea.setSelectionEnd(results.get(indexReplace)+ textFieldSearch.getText().length());
					textArea.replaceSelection(textFieldReplace.getText());
					indexReplace++;
				}else{
					JOptionPane.showMessageDialog(frame, "No se encontró '" + textFieldSearch.getText() + "'.");

				}
				if(indexReplace == results.size()){
					indexReplace = 0;
					isSearched = false;
				}
				
			}
		});
		
		btnReplaceAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkCapital.isSelected()){
					textArea.setText(Utils.replace(textFieldSearch.getText(), textFieldReplace.getText(), textArea.getText()));
				}else{
					textArea.setText(Utils.replace(textFieldSearch.getText().toLowerCase(), textFieldReplace.getText(), textArea.getText().toLowerCase()));
				}
				
			}
		});
		
	}
	
	
	private void search(){
		if(!isSearched){
			if(checkCapital.isSelected()){
				results = Utils.find(textArea.getText(), textFieldSearch.getText());
				
			}else{
				results = Utils.find(textArea.getText().toLowerCase(), textFieldSearch.getText().toLowerCase());
				System.out.println("TEXTAREA: " + textArea.getText());
				System.out.println("SEARCH FIELD: " + textFieldSearch.getText().toLowerCase());
			}
		}
	}
}
