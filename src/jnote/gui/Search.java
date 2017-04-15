package jnote.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jnote.utils.Utils;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;

public class Search extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea childField;
	boolean isSearched = false;
	private ArrayList<Integer> results;
	private int index = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search();
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
	
	public void setChildTextField(JTextArea textField){
		this.childField = textField;
	}
	
	public Search() {
		JFrame frame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 435, 115);
		setTitle("Buscar");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(60, 11, 239, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Buscar siguiente");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSearch.setBounds(309, 10, 110, 23);
		contentPane.add(btnSearch);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBounds(10, 14, 46, 14);
		contentPane.add(lblBuscar);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(309, 36, 110, 23);
		contentPane.add(btnCancel);
		
		JCheckBox checkCapital = new JCheckBox("Coincidir may\u00FAsculas y min\u00FAsculas");
		checkCapital.setBounds(10, 58, 289, 23);
		contentPane.add(checkCapital);
		
		//deshabilitar botón de buscar
		
		btnSearch.setEnabled(false);
		
		
		//listeners..

		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(textField.getText().equals("")){
					btnSearch.setEnabled(false);
				}else{
					btnSearch.setEnabled(true);
				}
				isSearched = false;
				index = 0;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(textField.getText().equals("")){
					btnSearch.setEnabled(false);
				}else{
					btnSearch.setEnabled(true);
				}
				isSearched = false;
				index = 0;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(textField.getText().equals("")){
					btnSearch.setEnabled(false);
				}else{
					btnSearch.setEnabled(true);
				}				
				isSearched = false;
				index = 0;
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isSearched){
					if(checkCapital.isSelected()){
						results = Utils.find(childField.getText(), textField.getText());
					}else{
						results = Utils.find(childField.getText().toLowerCase(), textField.getText().toLowerCase());
					}
				}
				if(results.size() != 0){
					isSearched = true;
					childField.setSelectionStart(results.get(index));
					childField.setSelectionEnd(results.get(index)+ textField.getText().length());
					index++;
				}else{
					JOptionPane.showMessageDialog(frame, "No se encontró '" + textField.getText() + "'.");
				}
				
				if(index == results.size()){
					index = 0;
				}
			}
		});
		
	}
}
