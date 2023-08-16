package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexion.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class backUp extends JFrame {

	private JPanel contentPane;
	public static Locale currentLocale = new Locale(elegirIdioma.idioma);
	public static ResourceBundle messages = ResourceBundle.getBundle("userInterface.messages", currentLocale);
	private JTextField tfRuta;
	public static String ruta = "";
	private JButton btnIngresar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					backUp frame = new backUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void backup(String r) {
		String sql = "exec backupTER ?";
		System.out.println("Se entra");
		System.out.println(r);

		try {
			PreparedStatement ps = Conexion.getConnection().prepareStatement(sql);
			ps.setString(1, r);
			System.out.println("Se entra");

			boolean rs = ps.execute(sql);

		/*	if (rs > 0) {
				JOptionPane.showMessageDialog(null, messages.getString("succesfulBackup"));
			} else {
				JOptionPane.showMessageDialog(null, messages.getString("errorDatabase"));
			}/*

			//System.out.println(passwordField);

		} catch (Exception e) {
			// un error aun peor
			 System.out.println(e);
		}
	}

	/**
	 * Create the frame.
	 */
	public backUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfRuta = new JTextField();
		tfRuta.setHorizontalAlignment(SwingConstants.CENTER);
		tfRuta.setForeground(new Color(30, 144, 255));
		tfRuta.setFont(new Font("Arial", Font.PLAIN, 25));
		tfRuta.setColumns(10);
		tfRuta.setBounds(10, 97, 414, 36);
		contentPane.add(tfRuta);
		
		JLabel lblRuta = new JLabel(messages.getString("backupRoute"));
		lblRuta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRuta.setForeground(Color.WHITE);
		lblRuta.setFont(new Font("Arial", Font.BOLD, 30));
		lblRuta.setBounds(0, 0, 434, 86);
		contentPane.add(lblRuta);
		
		btnIngresar = new JButton(messages.getString("Confirm"));
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruta = tfRuta.getText();
				System.out.println(ruta);
				if (!(ruta.isEmpty())) {
					backup(ruta);
				} else {
					JOptionPane.showMessageDialog(null, messages.getString("invalidRoute"));
				}
			}
		});
		btnIngresar.setForeground(new Color(30, 144, 255));
		btnIngresar.setFont(new Font("Arial", Font.PLAIN, 25));
		btnIngresar.setBackground(Color.WHITE);
		btnIngresar.setBounds(254, 144, 170, 51);
		contentPane.add(btnIngresar);
		
		JLabel backgroundLabel = new JLabel(messages.getString("backupRoute"));
		backgroundLabel.setIcon(new ImageIcon(backUp.class.getResource("/driver/fondoGeneral.png")));
		backgroundLabel.setBounds(0, 0, 434, 201);
		contentPane.add(backgroundLabel);
	}
}
