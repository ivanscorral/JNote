package jnote.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class About extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
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
	public About() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEsteProgramaHa = new JLabel("<html>Este programa ha sido desarrollado e implementado por Iv\u00E1n S\u00E1nchez Corral, <br> estudiante de 1\u00BA de Ingenier\u00EDa Inform\u00E1tica en la Universidad de Deusto.</html>");
		lblEsteProgramaHa.setBounds(10, 11, 414, 27);
		contentPane.add(lblEsteProgramaHa);
		
		JLabel lblContacto = new JLabel("<html> Contacto: <a href=ivan.sanchezc@opendeusto.es>ivan.sanchezc@opendeusto.es</a></html> ");
		lblContacto.setBounds(10, 56, 414, 14);
		contentPane.add(lblContacto);
	}

}
